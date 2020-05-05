package shardingsphere.workshop.mysql.proxy.todo.executor.dml;

import io.netty.channel.ChannelHandlerContext;
import shardingsphere.workshop.mysql.proxy.fixture.packet.MySQLErrPacket;
import shardingsphere.workshop.mysql.proxy.fixture.packet.MySQLErrPacketFactory;
import shardingsphere.workshop.mysql.proxy.fixture.packet.MySQLOKPacket;
import shardingsphere.workshop.mysql.proxy.todo.executor.CommonSQLExecutor;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.ColumnEntity;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.DataBaseEntity;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.MetaDataEntity;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.TableEntity;
import shardingsphere.workshop.parser.statement.ASTNode;
import shardingsphere.workshop.parser.statement.segment.AssignmentSegment;
import shardingsphere.workshop.parser.statement.statement.UpdateStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateSqlExecutor extends CommonSQLExecutor {

    public UpdateSqlExecutor(String sql, ASTNode sqlStatement) {
        super(sql, sqlStatement);
    }

    @Override
    public void execute(final ChannelHandlerContext context) {
        try{
            UpdateStatement updateStatement = (UpdateStatement)this.getSqlStatement();
            //从数据库中获取表实体
            TableEntity tableEntity = DataBaseEntity.getTable(updateStatement.getTableName().getIdentifier().getValue());
            //数据异构
            Map<String,Object> tempMap = processData(updateStatement,tableEntity);
            //通过where条件找到行号
            List<Integer> rowNums = (List<Integer>)tempMap.get("rowNums");
            //set数据异构
            List<String> setAssiList = (List<String>)tempMap.get("setAssiList");
            Map<String,AssignmentSegment> assiMap = (Map<String,AssignmentSegment>)tempMap.get("assiMap");
            for(Map.Entry<String, ColumnEntity> entry : tableEntity.getTableInfo().entrySet()){
                //遍历该列去找数据
                if(setAssiList.contains(entry.getKey())){
                    for(MetaDataEntity metaDataEntity : entry.getValue().getMetaDataEntityList()){
                        if(rowNums.contains(metaDataEntity.getRowNum())){
                            //更新具体值
                            metaDataEntity.setValue(assiMap.get(metaDataEntity.getColumnName()).getAssignmentValueSegment().getIdentifier().getValue());
                        }
                    }
                }
            }
            //更新完毕组织返回值
            processRetData(context,tableEntity);
        }catch (Exception e){
            createErrorPacket(e);
        }
    }

    private void processRetData(final ChannelHandlerContext context, TableEntity tableEntity) {
        context.write(new MySQLOKPacket(1,1,tableEntity.getRowNum()));
    }

    /**
     * 将插入的数据异构到临时map中，这里也可以处理一些索引之类的，各种操作
     * @param updateStatement
     * @param tableEntity
     */
    private Map<String,Object> processData(UpdateStatement updateStatement, TableEntity tableEntity) {
        //最终需要返回的信息
        Map<String,Object> retMap = new HashMap<>();
        //通过where条件找到行号
        List<Integer> rowsNum = new ArrayList<>();
        ColumnEntity columnEntity = tableEntity.getTableInfo().get(updateStatement.getWhereConditions().getColumnName().getIdentifier().getValue());
        for(MetaDataEntity metaDataEntity: columnEntity.getMetaDataEntityList()){
            //如果取出来的值和where条件的值相当则记录行号
            if(metaDataEntity.getValue().equals(updateStatement.getWhereConditions().getAssignmentValue().getIdentifier().getValue())){
                rowsNum.add(metaDataEntity.getRowNum());
            }
        }
        //set条件转化
        List<String> setAssiList = new ArrayList<>();
        Map<String,AssignmentSegment> assiMap = new HashMap<>();
        for(AssignmentSegment assignmentSegment : updateStatement.getSetAssignmentsClauseSegment().getSetAssignments()){
            setAssiList.add(assignmentSegment.getColumnNameSegment().getIdentifier().getValue());
            assiMap.put(assignmentSegment.getColumnNameSegment().getIdentifier().getValue(),assignmentSegment);
        }
        retMap.put("rowNums",rowsNum);
        retMap.put("setAssiList",setAssiList);
        retMap.put("assiMap",assiMap);
        return retMap;
    }

    private MySQLErrPacket createErrorPacket(final Exception cause) {
        return MySQLErrPacketFactory.newInstance(1, cause);
    }
}
