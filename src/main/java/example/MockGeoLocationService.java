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
package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockGeoLocationService {

    List<String> ips = new ArrayList<>();
    Map<String, String> mappedIps = new HashMap<>();

    public MockGeoLocationService() {
        map();
    }

    public String findLocation(String ip) {
        String ipPrefix = Arrays.asList(ip.split("\\.")).get(0);
        return mappedIps.get(ipPrefix);
    }

    private void map() {
        for (int i = 1; i < 64; i++) {
            mappedIps.put(String.valueOf(i), "Italy");
        }
        for (int i = 64; i < 128; i++) {
            mappedIps.put(String.valueOf(i), "England");
        }
        for (int i = 128; i < 192; i++) {
            mappedIps.put(String.valueOf(i), "Usa");
        }
        for (int i = 192; i < 256; i++) {
            mappedIps.put(String.valueOf(i), "France");
        }
    }
}
