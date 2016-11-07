This is just an example to show how a Kafka Streams Application can use a JCache (JSR107) cache, like Blazing Cache, and limit the usage of external services

In order to test this demo, you have to setup a simple kafka broker
see http://docs.confluent.io/3.0.0/streams/quickstart.html


Create an input Kafka topic named 'input' and write this content (this is like a web server access log): 

25-10-2016 195.4.4.1 GET/foo.html
25-10-2016 195.1.4.1 GET/foo.html
25-10-2016 10.4.4.1 GET/foo.html
25-10-2016 89.4.3.1 GET/foo.html
25-10-2016 127.10.2.2 GET/bar.html
25-10-2016 195.4.4.1 GET/foo.html
25-10-2016 127.84.0.1 GET/foo.html
25-10-2016 127.77.0.1 GET/foo.html

you can use the kafka-console-producer script inside standard Kafka distribution.

You will see the output on the 'output' topic, you can use the kafka-console-consumer application to see the content of it

This simple Kafka Stream Application adds to each line another element which represents the geo location of the IP address.

As calls to GeoLocation services can be very slow and time-consuming the use o a cache will speed up the execution of the application and reduce the overall usage of resources.


BlazingCache and ZooKeeper

As BlazingCache in 'clustered' mode uses ZooKeeper service to discover cache server services you can start a BlazingCache server in "clustered" mode over the same ZooKeeper ensemble which supports Kafka and Kafka Streams.

Look at DemoDistributedCache file and see BlazingCache documentation https://blazingcache.readme.io/docs/getting-started