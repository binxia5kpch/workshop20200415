package shardingsphere.workshop.mysql.proxy.todo.executor.dml;

import io.netty.channel.ChannelHandlerContext;
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
        //先用where 条件找到对应行号,这里设计有缺陷，where只支持一个条件 (以后的整体改一下)
        WhereConditionsSegment whereConditionsSegment = selectStatement.getWhereConditions();
        //因为where条件只支持一个所以异常简单。。。直接获取。。
        ColumnEntity whereColumnEntity = tableEntity.getTableInfo().get(whereConditionsSegment.getColumnName().getIdentifier().getValue());
        if(null != whereColumnEntity){
            //没获取到列信息直接报错
        }
        //获取where条件对应的列数据list
        List<MetaDataEntity> whereMetaDataEntityList = whereColumnEntity.getMetaDataEntityList();
        //获取满足where条件的行号
        List<Integer> whereRowNums = new ArrayList<>();
        //遍历取where对应的值
        for(MetaDataEntity metaDataEntity : whereMetaDataEntityList){
            if(whereConditionsSegment.getAssignmentValue().getIdentifier().getValue() == metaDataEntity.getValue()){
                //获取到对应的值，
                whereRowNums.add(metaDataEntity.getRowNum());
            }
        }
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
        for(ColumnNameSegment columnNameSegment : selectStatement.getSelectColumnName().getColumnNames()){
            selectColumns.add(columnNameSegment.getIdentifier().getValue());
            columnEntities.add(tableEntity.getTableInfo().get(columnNameSegment.getIdentifier().getValue()));
        }
        retMap.put("selectColumns",selectColumns);
        retMap.put("selectColumnsEntities",columnEntities);
        return retMap;
    }
}
