# Run the producer
`./mvnw spring-boot:run -Dstart-class=org.springframework.cloud.stream.performance.scst.PerformanceKafkaConsumerApplication`

# Run a consumer
## Spring Cloud Stream
`./mvnw spring-boot:run -Dstart-class=org.springframework.cloud.stream.performance.scst.PerformanceKafkaConsumerApplication`

## Spring Kafka
`./mvnw spring-boot:run -Dstart-class=org.springframework.cloud.stream.performance.sk.PerformanceSpringKafkaApplication`

## Native Kafka
`./mvnw spring-boot:run -Dstart-class=org.springframework.cloud.stream.performance.nk.PerformanceNativeKafkaConsumerApplication`
