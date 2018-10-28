package com.kanaiya.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kanaiya.kafka.model.User;

@Service
public class KafkaConsumer {

	
	@KafkaListener(topics="Kafka_Example",groupId="group_id")
	public void consume(String message) {
		System.out.println("Consume message : "+message);
	}
	
	@KafkaListener(topics="Kafka_Example_Json",groupId="group_json",containerFactory="userKafkaListenerContainerFactory")
	public void consumeJson(User user) {
		System.out.println("Consume Json message : "+user);
	}
}
