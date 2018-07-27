package org.springframework.cloud.stream.performance.scst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableBinding(Sink.class)
public class PerformanceKafkaConsumerApplication {

	private volatile int i = 0;

	private volatile int seconds = 0;

	@StreamListener(Sink.INPUT)
	public void processMessage(Message<?> message) {
		i++;
	}

	@Scheduled(fixedRate=1000)
	public void scheduled(){
		System.out.println("Messages consumed after " + ++seconds + ":" + i);
	}



	public static void main(String[] args) {
		SpringApplication.run(PerformanceKafkaConsumerApplication.class, args);
	}
}
