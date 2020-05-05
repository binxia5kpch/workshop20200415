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
import shardingsphere.workshop.parser.statement.segment.AssignmentValueSegment;
import shardingsphere.workshop.parser.statement.segment.ColumnNameSegment;
import shardingsphere.workshop.parser.statement.statement.InsertStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class InsertSqlExecutor extends CommonSQLExecutor {

    public InsertSqlExecutor(String sql, ASTNode sqlStatement) {
        super(sql, sqlStatement);
    }

    @Override
    public void execute(final ChannelHandlerContext context) {
        try{
            InsertStatement insertStatement = (InsertStatement)this.getSqlStatement();
            //从数据库中获取表实体
            TableEntity tableEntity = DataBaseEntity.getTable(insertStatement.getTableName().getIdentifier().getValue());
            //数据异构
            Map<String,Object> tempMap = processData(insertStatement,tableEntity);
            //整合一个列名和值的对应关系
            Map<String,String> kvMap = (Map<String,String>)tempMap.get("kvMap");
            //整合一个列名的集合方便查找
            List<String> columnsList = (List<String>)tempMap.get("columnsList");
            //遍历表结构的所有表字段，有插入值的设置相应的值没有的设置null
            for(String key : tableEntity.getTableInfo().keySet()){
                ColumnEntity valueList = tableEntity.getTableInfo().get(key);
                List<MetaDataEntity> metaDataEntityList = valueList.getMetaDataEntityList();
                //如果插入有这列
                if(columnsList.contains(key)){
                    //表数据填充，且表数据行号递增
                    MetaDataEntity metaDataEntity = new MetaDataEntity(kvMap.get(key),tableEntity.incrementRowNum(),key);
                    metaDataEntityList.add(metaDataEntity);
                }else {//
                    //如果没有则表数据插入null，且表数据行号递增
                    MetaDataEntity metaDataEntity = new MetaDataEntity(null,tableEntity.incrementRowNum(),key);
                    metaDataEntityList.add(metaDataEntity);
                }
            }
            //插入完毕组织返回值
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
     * @param insertStatement
     * @param tableEntity
     */
    private Map<String,Object> processData(InsertStatement insertStatement, TableEntity tableEntity) {
        //最终需要返回的信息
        Map<String,Object> retMap = new HashMap<>();
        //整合一个列名和值的对应关系
        Map<String,String> kvMap = new HashMap<>();
        //整合一个列名的集合方便查找
        List<String> columnsList = new ArrayList<>();
        //列信息为空，则插入所有字段
        if(null == insertStatement.getColumnNames()){
            List<AssignmentValueSegment> assignmentValueSegments = insertStatement.getAssignmentValues().getAssignmentValues();
            int i = 0;
            for (String tableKey : tableEntity.getTableInfo().keySet()) {
                columnsList.add(tableKey);
                AssignmentValueSegment assignmentValueSegment = assignmentValueSegments.get(i);
                kvMap.put(tableKey,assignmentValueSegment.getIdentifier().getValue());
                ++i;
            }
        }else{ //不为空则插入部分字段
            List<ColumnNameSegment> columnNameSegments = insertStatement.getColumnNames().getColumnNames();
            List<AssignmentValueSegment> assignmentValueSegments = insertStatement.getAssignmentValues().getAssignmentValues();
            Stream.iterate(0, i -> i + 1).limit(columnNameSegments.size()).forEach(i -> {
                //获取插入参数数据
                ColumnNameSegment columnNameSegment = columnNameSegments.get(i);
                //获取插入数据<顺序插入所以可以直接获取，不一致报错即可，暂时先不做处理吧。。>
                AssignmentValueSegment assignmentValueSegment = assignmentValueSegments.get(i);
                columnsList.add(columnNameSegment.getIdentifier().getValue());
                kvMap.put(columnNameSegment.getIdentifier().getValue(),assignmentValueSegment.getIdentifier().getValue());
            });
        }
        retMap.put("columnsList",columnsList);
        retMap.put("kvMap",kvMap);
        return retMap;
    }

    private MySQLErrPacket createErrorPacket(final Exception cause) {
        return MySQLErrPacketFactory.newInstance(1, cause);
    }
}
