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
import shardingsphere.workshop.parser.statement.statement.DeleteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteSqlExecutor extends CommonSQLExecutor {

    public DeleteSqlExecutor(String sql, ASTNode sqlStatement) {
        super(sql, sqlStatement);
    }

    @Override
    public void execute(final ChannelHandlerContext context) {
        try{
            DeleteStatement deleteStatement = (DeleteStatement)this.getSqlStatement();
            //从数据库中获取表实体
            TableEntity tableEntity = DataBaseEntity.getTable(deleteStatement.getTableName().getIdentifier().getValue());
            //数据异构
            Map<String,Object> tempMap = processData(deleteStatement,tableEntity);
            //通过where条件找到行号
            List<Integer> rowNums = (List<Integer>)tempMap.get("rowNums");
            //遍历表结构 删除对应行的数据,有个异构的数据这里会容易很多。。。
            for(Map.Entry<String, ColumnEntity> entry : tableEntity.getTableInfo().entrySet()){
                //遍历该列去找数据
                //临时list 用于记录要删的数据，最后一次性删掉
                List<MetaDataEntity> tempList = new ArrayList<>();
                for(MetaDataEntity metaDataEntity : entry.getValue().getMetaDataEntityList()){
                    if(rowNums.contains(metaDataEntity.getRowNum())){
                        //组织返回数据
                        tempList.add(metaDataEntity);
                    }
                }
                //一次性删掉
                entry.getValue().getMetaDataEntityList().removeAll(tempList);
            }
            //删除完毕组织返回值
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
     * @param deleteStatement
     * @param tableEntity
     */
    private Map<String,Object> processData(DeleteStatement deleteStatement, TableEntity tableEntity) {
        //最终需要返回的信息
        Map<String,Object> retMap = new HashMap<>();
        //通过where条件找到行号
        List<Integer> rowsNum = new ArrayList<>();
        ColumnEntity columnEntity = tableEntity.getTableInfo().get(deleteStatement.getWhereConditions().getColumnName().getIdentifier().getValue());
        for(MetaDataEntity metaDataEntity: columnEntity.getMetaDataEntityList()){
            //如果取出来的值和where条件的值相当则记录行号
            if(metaDataEntity.getValue().equals(deleteStatement.getWhereConditions().getAssignmentValue().getIdentifier().getValue())){
                rowsNum.add(metaDataEntity.getRowNum());
            }
        }
        /***
         * 处理各种逻辑。。。。。
         */
        retMap.put("rowsNum",rowsNum);
        return retMap;
    }

    private MySQLErrPacket createErrorPacket(final Exception cause) {
        return MySQLErrPacketFactory.newInstance(1, cause);
    }
}
