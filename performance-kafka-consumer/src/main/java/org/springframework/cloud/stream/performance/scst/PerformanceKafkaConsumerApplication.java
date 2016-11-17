package org.springframework.cloud.stream.performance.scst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableBinding(Sink.class)
public class PerformanceKafkaConsumerApplication {

	private int i = 0;

	@StreamListener(Sink.INPUT)
	public void processMessage(Message<?> message) {
		i++;
	}

	@Scheduled(fixedDelay=5000)
	private void scheduled(){
		System.out.println("Messages consumed: " + i);
	}


	public static void main(String[] args) {
		SpringApplication.run(PerformanceKafkaConsumerApplication.class, args);
	}
}
