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

import java.util.Properties;
import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

public class Executor {

    private final String input_topic;
    private final String output_topic;

    private final Serde<String> stringSerde = Serdes.String();
    private final Serde<LogLine> logLineSerde = Serdes.serdeFrom(new LogLineSerde(), new LogLineDeserializier());

    private final Properties props;
    private final Properties cacheConfiguration;

    public Executor(String inputTopic, String outputTopic, Properties cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
        this.input_topic = inputTopic;
        this.output_topic = outputTopic;
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount-" + System.currentTimeMillis());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        this.props = props;

    }

    public void run() throws Exception {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        try (javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager(
            cachingProvider.getDefaultURI(),
            cachingProvider.getDefaultClassLoader(),
            cacheConfiguration);) {
            try (Cache<String, String> cache
                = cacheManager.createCache("ip-location-cache", new MutableConfiguration<>());) {

                KStreamBuilder builder = new KStreamBuilder();
                KStream<String, String> startLines = builder.stream(stringSerde, stringSerde, input_topic);

                KStream<String, LogLine> finalLines = startLines
                    .mapValues((value) -> {
                        return LogLine.parse(value);
                    })
                    .mapValues((LogLine logLine) -> {
                        String ip = logLine.getIp();
                        if (ip != null) {
                            String location = cache.get(ip);
                            if (location == null) {
                                location = MockGeoLocationService.findLocation(ip);
                                cache.put(ip, location);
                            }
                            logLine.setLocation(location);
                        }
                        return logLine;
                    });

                finalLines.to(stringSerde, logLineSerde, output_topic);

                KafkaStreams streams = new KafkaStreams(builder, props);
                streams.start();
                Thread.sleep(5000L);
                streams.close();
            }
        }
    }

}
