## Welcome to Kafka CTF!

## Objective

Request for a ticket in Kafka by writing a value in topic `tickets`.  
Then wait an hour and get the flag. Easy.

Format of the flag: `flag{xxxxx}`

## Start

**Web App**  
http://195.154.119.34:8080/  

**Kafka**
````properties
bootstrap.servers=195.154.119.34:9092
security.protocol=SASL_PLAINTEXT
sasl.mechanism=PLAIN
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username="user1" password="user1-secret";
````

## Oh wait...

Unfortunately, the DevOps (or should we say DevOOPS) has mistakenly left the ChaosMonkeyService 🙊 enabled in Production.

As a result, all ticket requests disappear every 15 minutes!