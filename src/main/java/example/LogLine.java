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
import java.util.List;

public class LogLine {

    public static LogLine parse(String s) {
        LogLine logLine = new LogLine();
        if (s == null) {
            return null;
        }
        List<String> elements = new ArrayList<>(Arrays.asList(s.split(" ")));
        for (String element : elements) {
            if (element.contains("-")) {
                logLine.setTimestamp(element);
            } else if (element.contains(".")) {
                logLine.setIp(element);
            } else {
                logLine.setRequest(element);
            }
        }
        return logLine;
    }

    private String ip;
    private String timestamp;
    private String location;
    private String request;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "LogLine{" + "ip=" + ip + ", timestamp=" + timestamp + ", location=" + location + ", request=" + request + '}';
    }

}
