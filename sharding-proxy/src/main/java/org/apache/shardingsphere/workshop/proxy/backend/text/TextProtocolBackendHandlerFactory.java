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

package org.apache.shardingsphere.workshop.proxy.backend.text;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.sql.parser.SQLParserEngine;
import org.apache.shardingsphere.sql.parser.sql.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dml.DMLStatement;

/**
 * Text protocol backend handler factory.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextProtocolBackendHandlerFactory {
    
    /**
     * Create new instance of text protocol backend handler.
     *
     * @param sql SQL to be executed
     * @return instance of text protocol backend handler
     */
    public static TextProtocolBackendHandler newInstance(final String sql) {
        if (Strings.isNullOrEmpty(sql)) {
            return new SkipBackendHandler();
        }
        SQLStatement sqlStatement = new SQLParserEngine("MySQL").parse(sql, false);
        if (sqlStatement instanceof DMLStatement) {
        
        }
//        if (sqlStatement instanceof TCLStatement) {
//            return createTCLBackendHandler(sql, (TCLStatement) sqlStatement, backendConnection);
//        }
//        if (sqlStatement instanceof DALStatement) {
//            return createDALBackendHandler((DALStatement) sqlStatement, sql, backendConnection);
//        }
        return new SkipBackendHandler();
    }
    
}
