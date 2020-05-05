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

package shardingsphere.workshop.mysql.proxy.todo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import shardingsphere.workshop.mysql.proxy.todo.executor.CommonSQLExecutor;
import shardingsphere.workshop.mysql.proxy.todo.executor.SQLExecutor;
import shardingsphere.workshop.mysql.proxy.todo.executor.dml.DeleteSqlExecutor;
import shardingsphere.workshop.mysql.proxy.todo.executor.dml.InsertSqlExecutor;
import shardingsphere.workshop.mysql.proxy.todo.executor.dml.SelectSqlExecutor;
import shardingsphere.workshop.mysql.proxy.todo.executor.dml.UpdateSqlExecutor;
import shardingsphere.workshop.parser.statement.ASTNode;
import shardingsphere.workshop.parser.statement.statement.DeleteStatement;
import shardingsphere.workshop.parser.statement.statement.InsertStatement;
import shardingsphere.workshop.parser.statement.statement.SelectStatement;
import shardingsphere.workshop.parser.statement.statement.UpdateStatement;

/**
 * SQL executor factory.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SQLExecutorFactory {

    /**
     * 获取sqlExecutor
     * @param astNode
     * @param sql
     * @return
     */
    @SuppressWarnings("unchecked")
    public static SQLExecutor newInstance(final ASTNode astNode, final String sql) {
        if (astNode instanceof SelectStatement) {
            return new SelectSqlExecutor(sql,astNode);
        }else if(astNode instanceof InsertStatement){
            return new InsertSqlExecutor(sql,astNode);
        }else if(astNode instanceof UpdateStatement){
            return new UpdateSqlExecutor(sql,astNode);
        }else if(astNode instanceof DeleteStatement){
            return new DeleteSqlExecutor(sql,astNode);
        }
        return new CommonSQLExecutor(sql,astNode);
    }
}
