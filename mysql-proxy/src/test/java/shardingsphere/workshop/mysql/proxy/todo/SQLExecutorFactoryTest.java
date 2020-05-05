package shardingsphere.workshop.mysql.proxy.todo;

import org.junit.Before;
import org.junit.Test;
import shardingsphere.workshop.mysql.proxy.todo.executor.SQLExecutor;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.DataBaseEntity;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.MetaDataEntity;
import shardingsphere.workshop.mysql.proxy.todo.executor.entity.TableEntity;
import shardingsphere.workshop.parser.engine.ParseEngine;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * 这里的测试用例 只是用来调试方法用的，代码耦合的比较严重测试用例确实不太好写。
 */
public class SQLExecutorFactoryTest {

    private TableEntity tableEntity;
    private String tableName = "user";
    int initDataRow = 10; //初始化数据行数
    @Before
    public void initTableData(){
        tableEntity = DataBaseEntity.getTable(tableName);
        for(int i=0; i<initDataRow; i++){
            String sql = "insert "+tableName+" value("+i+",k"+i+",13201110000,123456);";
            SQLExecutor sqlExecutor = SQLExecutorFactory.newInstance(ParseEngine.parse(sql),sql);
            //执行
            sqlExecutor.execute(null);
        }
        //校验表中数据是否满足行数
        assertThat(tableEntity.getTableInfo().get("id").getMetaDataEntityList().size(),is(initDataRow));
    }
    @Test
    public void testSelectExcutor(){
        String sql = "select * from "+tableName+" where id = 1";
        SQLExecutor sqlExecutor = SQLExecutorFactory.newInstance(ParseEngine.parse(sql),sql);
        //执行
        sqlExecutor.execute(null);
    }

    @Test
    public void testInsert(){
        String sql = "insert user(id,user_name,pwd) value(1,kk,123456);";
        //获取执行器
        SQLExecutor sqlExecutor = SQLExecutorFactory.newInstance(ParseEngine.parse(sql),sql);
        //执行
        sqlExecutor.execute(null);
    }

    @Test
    public void testUpdate(){
        String sql = "UPDATE user SET user_name = zhangsan,mobile = 132 WHERE id = 1";
        //获取执行器
        SQLExecutor sqlExecutor = SQLExecutorFactory.newInstance(ParseEngine.parse(sql),sql);
        //执行
        sqlExecutor.execute(null);
        List<Integer> rowNums = new ArrayList<>();
        for(MetaDataEntity idMeta : tableEntity.getTableInfo().get("id").getMetaDataEntityList()){
            if("1".equals(idMeta.getValue())){
                rowNums.add(idMeta.getRowNum());
            }
        }
        //测试修改列user_name
        for(MetaDataEntity userNameMeta : tableEntity.getTableInfo().get("user_name").getMetaDataEntityList()){
            if(rowNums.contains(userNameMeta.getRowNum())){
                assertThat(userNameMeta.getValue(),is("zhangsan"));
            }
        }
        //测试修改列mobile
        for(MetaDataEntity userNameMeta : tableEntity.getTableInfo().get("mobile").getMetaDataEntityList()){
            if(rowNums.contains(userNameMeta.getRowNum())){
                assertThat(userNameMeta.getValue(),is("132"));
            }
        }
    }

    @Test
    public void testDelete(){
        String sql = "DELETE from user where id=1";
        //获取执行器
        SQLExecutor sqlExecutor = SQLExecutorFactory.newInstance(ParseEngine.parse(sql),sql);
        //执行
        sqlExecutor.execute(null);
        //校验表中数据是否满足行数(这里是可以支持多行删除的，只是现在条件是只有一行)
        assertThat(tableEntity.getTableInfo().get("id").getMetaDataEntityList().size(),is(initDataRow-1));
    }
}
