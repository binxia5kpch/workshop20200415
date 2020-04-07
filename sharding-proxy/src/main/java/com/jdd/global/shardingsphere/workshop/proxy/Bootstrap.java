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

package com.jdd.global.shardingsphere.workshop.proxy;

import com.google.common.primitives.Ints;
import com.jdd.global.shardingsphere.workshop.proxy.backend.schema.CSVLogicSchema;
import com.jdd.global.shardingsphere.workshop.proxy.frontend.ShardingProxy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Sharding-Proxy Bootstrap.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Bootstrap {
    
    private static final int DEFAULT_PORT = 3307;

    /**
     * Main entrance.
     *
     * @param args startup arguments
     */
    public static void main(final String[] args) throws IOException {
        CSVLogicSchema.getInstance().init();
        ShardingProxy.getInstance().start(getPort(args));
    }
    
    private static int getPort(final String[] args) {
        if (0 == args.length) {
            return DEFAULT_PORT;
        }
        Integer paredPort = Ints.tryParse(args[0]);
        return paredPort == null ? DEFAULT_PORT : paredPort;
    }
}
