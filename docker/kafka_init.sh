# Retention 30 minutes
./opt/kafka/bin/kafka-topics.sh --create --topic tickets --bootstrap-server localhost:9093 --command-config /opt/kafka/config/command.config --partitions 1 --replication-factor 1 --config retention.ms=1860000 --config segment.bytes=10485760 --config retention.bytes=1073741824

# Create ACLs so that user1 can write to the topic
./opt/kafka/bin/kafka-acls.sh --bootstrap-server localhost:9093 --command-config /opt/kafka/config/command.config --add --allow-principal User:user1 --operation Write --topic tickets
./opt/kafka/bin/kafka-acls.sh --bootstrap-server localhost:9093 --command-config /opt/kafka/config/command.config --add --allow-principal User:user1 --operation DescribeConfigs --topic tickets

# Allow cluster metadata access to user1
./opt/kafka/bin/kafka-acls.sh --bootstrap-server localhost:9093 --command-config /opt/kafka/config/command.config --add --allow-principal User:user1 --operation Describe --cluster '*'
./opt/kafka/bin/kafka-acls.sh --bootstrap-server localhost:9093 --command-config /opt/kafka/config/command.config --add --allow-principal User:user1 --operation Describe --group '*'

# set quotas for user1
./opt/kafka/bin/kafka-configs.sh --bootstrap-server localhost:9093 --command-config /opt/kafka/config/command.config --alter --add-config 'producer_byte_rate=1024' --entity-type users --entity-name user1
./opt/kafka/bin/kafka-configs.sh --bootstrap-server localhost:9093 --command-config /opt/kafka/config/command.config --alter --add-config 'producer_byte_rate=50' --entity-type clients --entity-default
