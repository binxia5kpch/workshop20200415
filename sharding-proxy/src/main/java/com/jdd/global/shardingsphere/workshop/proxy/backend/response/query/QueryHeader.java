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

package com.jdd.global.shardingsphere.workshop.proxy.backend.response.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.shardingsphere.sql.parser.binder.metadata.column.ColumnMetaData;

/**
 * Query header.
 */
@AllArgsConstructor
@Getter
public final class QueryHeader {
    
    private final String schema;
    
    private final String table;
    
    private final String columnLabel;
    
    private final String columnName;
    
    private final int columnLength;
    
    private final Integer columnType;
    
    private final int decimals;

    private final boolean signed;

    private final boolean primaryKey;

    private final boolean notNull;

    private final boolean autoIncrement;
    
    public QueryHeader(final String schemaName, final String tableName, final ColumnMetaData columnMetaData) {
        this.columnName = columnMetaData.getName();
        schema = schemaName;
        columnLabel = columnMetaData.getName();
        columnLength = 100;
        columnType = columnMetaData.getDataType();
        decimals = 0;
        signed = false;
        notNull = true;
        autoIncrement = columnMetaData.isGenerated();
        table = tableName;
        primaryKey = columnMetaData.isPrimaryKey();
    }
}
