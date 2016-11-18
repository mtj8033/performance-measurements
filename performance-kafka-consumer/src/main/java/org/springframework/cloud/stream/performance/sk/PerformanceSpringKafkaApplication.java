package org.springframework.cloud.stream.performance.sk;

import java.util.HashMap;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class PerformanceSpringKafkaApplication {

	private volatile int i = 0;

	private volatile int seconds = 0;

	@Scheduled(fixedRate=1000)
	public void scheduled(){
		System.out.println("Messages consumed after " + ++seconds + ":" + i);
	}

	@Bean
	public KafkaMessageListenerContainer<byte[],byte[]> consumer() {
		ContainerProperties containerProperties = new ContainerProperties("performance-test");
		HashMap<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "test" + UUID.randomUUID().toString().replace("-",""));
		DefaultKafkaConsumerFactory<byte[], byte[]> defaultKafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(configs);
		KafkaMessageListenerContainer<byte[], byte[]> kafkaMessageListenerContainer = new KafkaMessageListenerContainer<>(defaultKafkaConsumerFactory, containerProperties);
		kafkaMessageListenerContainer.setupMessageListener((MessageListener<byte[], byte[]>) record -> i++);
		return kafkaMessageListenerContainer;
	}

	public static void main(String[] args) {
		SpringApplication.run(PerformanceSpringKafkaApplication.class, args);
	}
}
