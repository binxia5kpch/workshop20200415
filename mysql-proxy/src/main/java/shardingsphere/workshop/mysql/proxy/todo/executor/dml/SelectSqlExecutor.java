package shardingsphere.workshop.mysql.proxy.todo.executor.dml;

import io.netty.channel.ChannelHandlerContext;
import shardingsphere.workshop.mysql.proxy.fixture.packet.MySQLErrPacket;
import shardingsphere.workshop.mysql.proxy.fixture.packet.MySQLErrPacketFactory;
import shardingsphere.workshop.mysql.proxy.fixture.packet.constant.MySQLColumnType;
import shardingsphere.workshop.mysql.proxy.todo.executor.CommonSQLExecutor;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.ColumnEntity;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.DataBaseEntity;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.MetaDataEntity;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.TableEntity;
import shardingsphere.workshop.mysql.proxy.todo.packet.MySQLColumnDefinition41Packet;
import shardingsphere.workshop.mysql.proxy.todo.packet.MySQLEofPacket;
import shardingsphere.workshop.mysql.proxy.todo.packet.MySQLFieldCountPacket;
import shardingsphere.workshop.mysql.proxy.todo.packet.MySQLTextResultSetRowPacket;
import shardingsphere.workshop.parser.statement.ASTNode;
import shardingsphere.workshop.parser.statement.segment.ColumnNameSegment;
import shardingsphere.workshop.parser.statement.segment.WhereConditionsSegment;
import shardingsphere.workshop.parser.statement.statement.SelectStatement;

import java.util.*;

public class SelectSqlExecutor extends CommonSQLExecutor {


    public SelectSqlExecutor(String sql, ASTNode sqlStatement) {
        super(sql, sqlStatement);
    }

    @Override
    public void execute(final ChannelHandlerContext context){
        try{
            SelectStatement selectStatement = (SelectStatement)this.getSqlStatement();
            //获取表结构
            TableEntity tableEntity = DataBaseEntity.getTable(selectStatement.getTableName().getIdentifier().getValue());
            if(null == tableEntity){
                //处理没有表的异常
            }
            //异构数据，可以做很多事情，这里只是简单的保证下面不用出现讨厌的多层for循环，where条件目前设计有缺陷，先不异构了
            Map<String,Object> retMap = processData(selectStatement,tableEntity);
            List<String> selectColumns = (List<String>)retMap.get("selectColumns");
            List<ColumnEntity> selectColumnsEntities = (List<ColumnEntity>)retMap.get("selectColumnsEntities");
            List<Integer> whereRowNums = (List<Integer>)retMap.get("rowsNum");
            //构建临时返回数据容器
            Map<Integer,List<Object>> tempDataMap = getTempDataMap(whereRowNums);
            //没有异构索引，也没有异构row数据 就只能用这种笨方法取数据了。。。
            //遍历表的所有列,取select对应的列 然后根据where行号取对应行
            for(Map.Entry<String, ColumnEntity> entry : tableEntity.getTableInfo().entrySet()){
                if(selectColumns.contains(entry.getKey()) && entry.getValue().getMetaDataEntityList().size()>0){
                    //遍历该列去找数据
                    for(MetaDataEntity metaDataEntity : entry.getValue().getMetaDataEntityList()){
                        if(whereRowNums.contains(metaDataEntity.getRowNum())){
                            //组织返回数据
                            tempDataMap.get(metaDataEntity.getRowNum()).add(metaDataEntity.getValue());
                        }
                    }
                }
            }
            //返回数据
            processRetData(tableEntity,selectColumnsEntities,tempDataMap,context);
        }catch (Exception e){
            e.printStackTrace();
            createErrorPacket(e);
        }
    }

    private void processRetData(TableEntity tableEntity, List<ColumnEntity> selectColumns, Map<Integer, List<Object>> tempDataMap, ChannelHandlerContext context) {
        int sequenceId = 1;
        //设置有多少列
        context.write(new MySQLFieldCountPacket(sequenceId, selectColumns.size()));
        /**
         * 构造返回的列的信息
         */
        for(ColumnEntity column : selectColumns){
            context.write(new MySQLColumnDefinition41Packet(++sequenceId, 0, tableEntity.getSchemaName(), tableEntity.getTableName(), tableEntity.getTableName(), column.getColumnName(), column.getColumnName(), column.getColumnLength(), getMysqlType(column.getColumnName()),0));
        }
        //表示上面的列信息构造完毕
        context.write(new MySQLEofPacket(++sequenceId));
        /**
         * ImmutableList.of(100,9)
         * 表示两列的值对应上面的id,id2
         * 上面有几列 这里有几个值
         */
        for(Map.Entry<Integer,List<Object>> tempData : tempDataMap.entrySet()){
            context.write(new MySQLTextResultSetRowPacket(++sequenceId, tempData.getValue()));
        }
        //表示整个mysql 返回值信息构造完毕
        context.write(new MySQLEofPacket(++sequenceId));
    }

    /**
     * 类型转化
     * @param columnName
     * @return
     */
    private MySQLColumnType getMysqlType(String columnName) {
        //通过自定义的类型返回mysql要求的类型
        if("varchar".equals(columnName)){
            return MySQLColumnType.MYSQL_TYPE_STRING;
        }
        return null;
    }

    private Map<Integer, List<Object>> getTempDataMap(List<Integer> whereRowNums) {
        Map<Integer, List<Object>> map = new LinkedHashMap<>();
        for(int rowNum : whereRowNums){
            List<Object> list = new ArrayList<>();
            map.put(rowNum,list);
        }
        return map;
    }

    private Map<String,Object> processData(SelectStatement selectStatement, TableEntity tableEntity) {
        Map<String,Object> retMap = new HashMap<>();
        List<String> selectColumns = new ArrayList<>();
        List<ColumnEntity> columnEntities = new ArrayList<>();
        if(null != selectStatement.getSelectColumnName().getASTERISK_()){
            //* 号逻辑单独处理（这部分感觉应该放在sql解析的地方是不是更好一些）
            for(Map.Entry<String, ColumnEntity> entry : tableEntity.getTableInfo().entrySet()){
                selectColumns.add(entry.getKey());
                columnEntities.add(entry.getValue());
            }
        }else {
            for(ColumnNameSegment columnNameSegment : selectStatement.getSelectColumnName().getColumnNames()){
                selectColumns.add(columnNameSegment.getIdentifier().getValue());
                columnEntities.add(tableEntity.getTableInfo().get(columnNameSegment.getIdentifier().getValue()));
            }
        }
        //where 条件
        List<Integer> rowsNum = new ArrayList<>();
        ColumnEntity columnEntity = tableEntity.getTableInfo().get(selectStatement.getWhereConditions().getColumnName().getIdentifier().getValue());
        for(MetaDataEntity metaDataEntity: columnEntity.getMetaDataEntityList()){
            //如果取出来的值和where条件的值相当则记录行号
            if(metaDataEntity.getValue().equals(selectStatement.getWhereConditions().getAssignmentValue().getIdentifier().getValue())){
                rowsNum.add(metaDataEntity.getRowNum());
            }
        }
        retMap.put("rowsNum",rowsNum);
        retMap.put("selectColumns",selectColumns);
        retMap.put("selectColumnsEntities",columnEntities);
        return retMap;
    }

    private MySQLErrPacket createErrorPacket(final Exception cause) {
        return MySQLErrPacketFactory.newInstance(1, cause);
    }
}
