package shardingsphere.workshop.mysql.proxy.todo.executor.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 行数据基本单位
 */
@Getter
public class MetaDataEntity {
    /**
     * 数据值
     */
    @Setter
    private String value;


    /**
     * 数据对应的行号
     */
    private final int rowNum;

    /**
     * 数据对应的列名
     */
    private final String columnName;

    public MetaDataEntity(String value, int incrementRowNum, String key) {
        this.value = value;
        this.rowNum = incrementRowNum;
        this.columnName = key;
    }
}
