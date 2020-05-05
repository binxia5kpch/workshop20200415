package shardingsphere.workshop.mysql.proxy.todo.executor.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 表信息，基于内存构建一个简单的表，只是为了实现简单模拟，也可以用各种文件系统
 */
@Getter
public class DataBaseEntity {

    /**
     * user表
     */
    private final static TableEntity userTable = new TableEntity("user","sharding_db");

    /**
     * 初始化表结构
     */
    static {
        List<MetaDataEntity> idList = new ArrayList<>();
        ColumnEntity idColumnEntity = new ColumnEntity("id",100,"int",idList);
        userTable.getTableInfo().put("id",idColumnEntity);
        List<MetaDataEntity> userNameList = new ArrayList<>();
        ColumnEntity userColumnEntity = new ColumnEntity("user_name",100,"varchar",userNameList);
        userTable.getTableInfo().put("user_name",userColumnEntity);
        List<MetaDataEntity> mobileList = new ArrayList<>();
        ColumnEntity moblieColumnEntity = new ColumnEntity("mobile",100,"varchar",mobileList);
        userTable.getTableInfo().put("mobile",moblieColumnEntity);
        List<MetaDataEntity> pwdList = new ArrayList<>();
        ColumnEntity pwdColumnEntity = new ColumnEntity("pwd",1000,"varchar",pwdList);
        userTable.getTableInfo().put("pwd",pwdColumnEntity);
    }

    /**
     * 通过名称获取表实体
     * @param tableName
     * @return
     */
    public static TableEntity getTable(String tableName){
        if("user".equals(tableName)){
            return userTable;
        }
        return null;
    }
}
