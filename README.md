This is just an example to show how a Kafka Streams Application can use a JCache (JSR107) cache, like Blazing Cache, and limit the usage of external services

In order to test this demo, you have to setup a simple kafka broker
see http://docs.confluent.io/3.0.0/streams/quickstart.html


Then create an input text file similar to: (It is a possible example of common server web file log.)

25-10-2016 195.4.4.1 GET /foo.html
25-10-2016 127.10.2.2 GET /bar.html
25-10-2016 195.4.4.1 GET /foo.html
25-10-2016 127.0.0.1 GET /foo.html
25-10-2016 127.0.0.1 GET /foo.html

and put it in the input topic already created. 

The demo will add another element for each line that represents the ip location, in order to calculate it, can be used the cache instead of e.g. a web service call.

As BlazingCache uses ZooKeeper service to coordinate the service you can start a BlazingCache server in clustered mode over the same ZooKeeper ensemble.

Look at DemoDistributedCache file and see BlazingCache documentation https://blazingcache.readme.io/docs/getting-started