/*
 Licensed to Diennea S.r.l. under one
 or more contributor license agreements. See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership. Diennea S.r.l. licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.

 */
package demo;

import example.Executor;
import java.util.Properties;
import org.junit.Test;

public class DemoDistributedCache {

    @Test
    public void demo() throws Exception {
       /*
        * In order to run this example you have to start a BlazingCache server
        * Download standard distribution binaries from http://github.com/diennea/blazingcache
        * unzip and change in conf/server.properties put
        * clustering.mode=clustered
        * zk.address=localhost:2181
        * then start the cache server service with
        * bin/service start 
        */
        Properties cacheConfiguration = new Properties();
        cacheConfiguration.put("blazingcache.mode", "clustered");
        cacheConfiguration.put("blazingcache.zookeeper.connectstring", "localhost:2181");
        Executor executor = new Executor("input-topic11", "output-topic12", cacheConfiguration);
        executor.run();
    }

}
