/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shardingsphere.workshop.parser.engine;

import org.junit.Test;
import shardingsphere.workshop.parser.statement.segment.AssignmentSegment;
import shardingsphere.workshop.parser.statement.segment.AssignmentValueSegment;
import shardingsphere.workshop.parser.statement.segment.ColumnNameSegment;
import shardingsphere.workshop.parser.statement.statement.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public final class ParseEngineTest {
    
    @Test
    public void testParse() {
        String sql = "use sharding_db";
        UseStatement useStatement = (UseStatement) ParseEngine.parse(sql);
        assertThat(useStatement.getSchemeName().getIdentifier().getValue(), is("sharding_db"));
    }

    @Test
    public void testParseSelect() {
        List<String> expectedColumns = Arrays.asList("tname","amt");
        String sql = "select tname,amt from t_order where tname = 1";
        SelectStatement selectStatement = (SelectStatement) ParseEngine.parse(sql);

        List<String> listcolumnsValue = new ArrayList();
        for(ColumnNameSegment columnNameSegment :  selectStatement.getSelectColumnName().getColumnNames()){
            listcolumnsValue.add(columnNameSegment.getIdentifier().getValue());
        }
        //测试select列
        assertThat(listcolumnsValue,is(expectedColumns));
        //测试表名
        assertThat(selectStatement.getTableName().getIdentifier().getValue(),is("t_order"));
        //测试where条件
        assertThat(selectStatement.getWhereConditions().getColumnName().getIdentifier().getValue(),is("tname"));
        assertThat(selectStatement.getWhereConditions().getAssignmentValue().getIdentifier().getValue(),is("1"));
    }

    @Test
    public void testParseSelectAll() {
        String sql = "select * from t_order where tname = 1";
        SelectStatement selectStatement = (SelectStatement) ParseEngine.parse(sql);
        //测试select列
        assertThat(selectStatement.getSelectColumnName().getASTERISK_().getValue(),is("*"));
        //测试表名
        assertThat(selectStatement.getTableName().getIdentifier().getValue(),is("t_order"));
        //测试where条件
        assertThat(selectStatement.getWhereConditions().getColumnName().getIdentifier().getValue(),is("tname"));
        assertThat(selectStatement.getWhereConditions().getAssignmentValue().getIdentifier().getValue(),is("1"));
    }

    @Test
    public void testParseInsert() {
        List<String> expectedColumns = Arrays.asList("id","user_name","pwd");
        List<String> expectedArgs = Arrays.asList("1","kk","123456");

        String sql = "insert into t_user(id,user_name,pwd) value(1,kk,123456);";
        InsertStatement insertStatement = (InsertStatement) ParseEngine.parse(sql);
        List<String> listcolumnsValue = new ArrayList();
        for(ColumnNameSegment columnNameSegment :  insertStatement.getColumnNames().getColumnNames()){
            listcolumnsValue.add(columnNameSegment.getIdentifier().getValue());
        }
        //测试列名
        assertThat(listcolumnsValue,is(expectedColumns));
        //测试表名
        assertThat(insertStatement.getTableName().getIdentifier().getValue(),is("t_user"));
        List<String> argsValues = new ArrayList();
        for(AssignmentValueSegment assignmentValueSegment :  insertStatement.getAssignmentValues().getAssignmentValues()){
            argsValues.add(assignmentValueSegment.getIdentifier().getValue());
        }
        //测试参数名
        assertThat(argsValues,is(expectedArgs));
    }

    @Test
    public void testParseInsertNoInto() {
        List<String> expectedColumns = Arrays.asList("id","user_name","pwd");
        List<String> expectedArgs = Arrays.asList("1","kk","123456");

        String sql = "insert t_user(id,user_name,pwd) value(1,kk,123456);";
        InsertStatement insertStatement = (InsertStatement) ParseEngine.parse(sql);
        List<String> listcolumnsValue = new ArrayList();
        for(ColumnNameSegment columnNameSegment :  insertStatement.getColumnNames().getColumnNames()){
            listcolumnsValue.add(columnNameSegment.getIdentifier().getValue());
        }
        //测试列名
        assertThat(listcolumnsValue,is(expectedColumns));
        //测试表名
        assertThat(insertStatement.getTableName().getIdentifier().getValue(),is("t_user"));
        List<String> argsValues = new ArrayList();
        for(AssignmentValueSegment assignmentValueSegment :  insertStatement.getAssignmentValues().getAssignmentValues()){
            argsValues.add(assignmentValueSegment.getIdentifier().getValue());
        }
        //测试参数名
        assertThat(argsValues,is(expectedArgs));
    }

    @Test
    public void testParseInsertNoColumns() {
        List<String> expectedArgs = Arrays.asList("1","kk","123456");

        String sql = "insert t_user value(1,kk,123456);";
        InsertStatement insertStatement = (InsertStatement) ParseEngine.parse(sql);
        //测试表名
        assertThat(insertStatement.getTableName().getIdentifier().getValue(),is("t_user"));
        List<String> argsValues = new ArrayList();
        for(AssignmentValueSegment assignmentValueSegment :  insertStatement.getAssignmentValues().getAssignmentValues()){
            argsValues.add(assignmentValueSegment.getIdentifier().getValue());
        }
        //测试参数名
        assertThat(argsValues,is(expectedArgs));
    }

    @Test
    public void testParseDelete() {
        String sql = "DELETE from t_user where id=1";
        DeleteStatement deleteStatement = (DeleteStatement) ParseEngine.parse(sql);
        //测试表名
        assertThat(deleteStatement.getTableName().getIdentifier().getValue(),is("t_user"));
        //测试where条件
        assertThat(deleteStatement.getWhereConditions().getColumnName().getIdentifier().getValue(),is("id"));
        assertThat(deleteStatement.getWhereConditions().getAssignmentValue().getIdentifier().getValue(),is("1"));
    }

    @Test
    public void testParseUpdate() {
        List<String> expectedColumns = Arrays.asList("name","sex");
        List<String> expectedArgs = Arrays.asList("zhangsan","nan");

        String sql = "UPDATE t_user SET name = zhangsan,sex = nan WHERE id = 1";
        UpdateStatement updateStatement = (UpdateStatement) ParseEngine.parse(sql);
        //测试表名
        assertThat(updateStatement.getTableName().getIdentifier().getValue(),is("t_user"));
        //测试where条件
        assertThat(updateStatement.getWhereConditions().getColumnName().getIdentifier().getValue(),is("id"));
        assertThat(updateStatement.getWhereConditions().getAssignmentValue().getIdentifier().getValue(),is("1"));
        //测试set列表
        List<String> columns = new ArrayList<>();
        List<String> args = new ArrayList<>();
        for(AssignmentSegment assignmentSegment :updateStatement.getSetAssignmentsClauseSegment().getSetAssignments()){
            columns.add(assignmentSegment.getColumnNameSegment().getIdentifier().getValue());
            args.add(assignmentSegment.getAssignmentValueSegment().getIdentifier().getValue());
        }
        assertThat(columns,is(expectedColumns));
        assertThat(args,is(expectedArgs));
    }

}
