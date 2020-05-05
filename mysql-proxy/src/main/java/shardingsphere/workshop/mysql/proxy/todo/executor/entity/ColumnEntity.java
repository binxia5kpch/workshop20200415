package shardingsphere.workshop.mysql.proxy.todo.executor.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ColumnEntity {

    /**
     * 列名
     */
    private final String columnName;
    /**
     * 列长度
     */
    private final int columnLength;
    /**
     * 数据类型 默认varchar
     */
    private final String dataType;
    /**
     * 列数据
     */
    private final List<MetaDataEntity> metaDataEntityList;

}
