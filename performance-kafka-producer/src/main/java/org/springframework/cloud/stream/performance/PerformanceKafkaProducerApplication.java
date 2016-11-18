package org.springframework.cloud.stream.performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@EnableBinding(Source.class)
public class PerformanceKafkaProducerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PerformanceKafkaProducerApplication.class, args);

		Source source = context.getBean(Source.class);

		for (int i=0; i < 1000000; i++) {
			source.output().send(MessageBuilder.withPayload(new byte[1000]).build());
		}

		context.close();
	}
}
