package shardingsphere.workshop.mysql.proxy.todo.executor.entity;



import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class TableEntity {
    /**
     * 表结构，这里的考虑线程安全的问题，暂时先不考虑
     * 数据类型先统一支持String吧。。 实在心有余而力不足。。
     * 设计貌似有缺陷。。。 列名应该在columnEntity里吧。。。。算冗余数据吧。。。
     */
    @Getter
    private final Map<String, ColumnEntity> tableInfo = new LinkedHashMap<>();

    /**
     * 行数据 key行号，value行数据（打算异构成行表，有点浪费空间没做。）
     */
    private final Map<Integer,RowEntity> rows = new ConcurrentHashMap<>();
    /**
     * 表名
     */
    @Getter
    private final String tableName;

    /**
     * 数据库名
     */
    @Getter
    private final String schemaName;

    /**
     * 表数据行数
     */
    private final AtomicInteger rowNum = new AtomicInteger();

    public int incrementRowNum() {
        return rowNum.incrementAndGet();
    }

    public int getRowNum(){
        return rowNum.intValue();
    }

    public TableEntity(String tableName,String schemaName){
        this.tableName = tableName;
        this.schemaName = schemaName;
    }

}
