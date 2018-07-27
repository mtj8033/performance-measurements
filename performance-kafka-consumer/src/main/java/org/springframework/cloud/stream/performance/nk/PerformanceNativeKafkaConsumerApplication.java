package org.springframework.cloud.stream.performance.nk;

import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Marius Bogoevici
 */
@SpringBootApplication
@EnableScheduling
public class PerformanceNativeKafkaConsumerApplication {

    private volatile int i = 0;

    private volatile int seconds = 0;

    @Scheduled(fixedRate=1000)
    public void scheduled(){
        System.out.println("Messages consumed after " + ++seconds + ":" + i);
    }

    @Bean
    public CommandLineRunner consume() {
        return args -> {
            Properties configs = new Properties();
            configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
            configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
            configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
            configs.put(ConsumerConfig.GROUP_ID_CONFIG, "test" + UUID.randomUUID().toString().replace("-",""));

            KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(configs);

            consumer.subscribe(Collections.singletonList("performance-test"));
            while (i < 1000000) {
                ConsumerRecords<byte[], byte[]> consumerRecords = consumer.poll(1000);
                for (ConsumerRecord<byte[], byte[]> consumerRecord : consumerRecords) {
                    i++;
                }
                consumer.commitSync();
            }
            consumer.close();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(PerformanceNativeKafkaConsumerApplication.class, args);
    }
}
