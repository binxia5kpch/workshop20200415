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

package com.jdd.global.shardingsphere.workshop.proxy.transport.packet.generic;

import com.jdd.global.shardingsphere.workshop.proxy.transport.MySQLPacketPayload;
import com.jdd.global.shardingsphere.workshop.proxy.transport.constant.MySQLStatusFlag;
import com.jdd.global.shardingsphere.workshop.proxy.transport.packet.MySQLPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * OK packet protocol for MySQL.
 * 
 * @see <a href="https://dev.mysql.com/doc/internals/en/packet-OK_Packet.html">OK Packet</a>
 */
@RequiredArgsConstructor
@Getter
public final class MySQLOKPacket implements MySQLPacket {
    
    /**
     * Header of OK packet.
     */
    public static final int HEADER = 0x00;
    
    private static final int DEFAULT_STATUS_FLAG = MySQLStatusFlag.SERVER_STATUS_AUTOCOMMIT.getValue();
    
    private final int sequenceId;
    
    private final long affectedRows;
    
    private final long lastInsertId;
    
    private final int statusFlag;
    
    private final int warnings;
    
    private final String info;
    
    public MySQLOKPacket(final int sequenceId) {
        this(sequenceId, 0L, 0L, DEFAULT_STATUS_FLAG, 0, "");
    }
    
    public MySQLOKPacket(final int sequenceId, final long affectedRows, final long lastInsertId) {
        this(sequenceId, affectedRows, lastInsertId, DEFAULT_STATUS_FLAG, 0, "");
    }
    
    @Override
    public void write(final MySQLPacketPayload payload) {
        payload.writeInt1(HEADER);
        payload.writeIntLenenc(affectedRows);
        payload.writeIntLenenc(lastInsertId);
        payload.writeInt2(statusFlag);
        payload.writeInt2(warnings);
        payload.writeStringEOF(info);
    }
}
