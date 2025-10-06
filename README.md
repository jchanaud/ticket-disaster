# Welcome to Kafka Capture The Flag

## 🎯 Objective
Produce a message to the Kafka topic tickets to request the flag.  
Then wait one hour for the system to process and deliver it.

Format of the flag: `flag{xxxxx}`

## ⚙️ Get Started
**Web App**  
http://195.154.119.34:8080/  

**Kafka**
````properties
bootstrap.servers=195.154.119.34:9092
security.protocol=SASL_PLAINTEXT
sasl.mechanism=PLAIN
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username="user1" password="user1-secret";
````

## ⏳ The Process
After submitting your ticket, it enters Kafka’s internal approval chain. A process that, for reasons no one fully understands, takes **exactly one hour**.  
During that time, your message is examined, relayed, and re-validated by a sequence of services whose purpose is never clearly documented.

You can simply wait, and once the system decides your request is complete, the flag will appear.

## 🐒 A Note from Operations
Unfortunately, the DevOOPS (yes, you read that right) has mistakenly left the ChaosMonkeyService 🙊 enabled in Production.

As a result, all ticket requests disappear every 15 minutes!

If your message disappears, you’ll need to start again. 😭