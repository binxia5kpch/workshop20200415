package shardingsphere.workshop.mysql.proxy.todo.executor.entity;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 行数据 ,可以异构用于快速crud  但是就想当于一份儿数据占两份空间了 太要命了 先不整了。。
 */
@RequiredArgsConstructor
public class RowEntity {

    /**
     * 数据对应的行号
     */
    private final int rowNum;

    private final List<MetaDataEntity> metaDataEntityList;
}
