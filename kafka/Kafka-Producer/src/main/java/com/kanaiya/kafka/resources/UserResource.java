package com.kanaiya.kafka.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanaiya.kafka.model.User;

@RestController
@RequestMapping("kafka")
public class UserResource {

	@Autowired
	KafkaTemplate<String, User> kafkaTemplate;
	private static final String TOPIC ="Kafka_Example";
	@GetMapping("/publish/{name}")
	public String post(@PathVariable("name")final String name) {
		
		kafkaTemplate.send(TOPIC, new User(name,"Amrutiya",27));
		return "Published successfully.";
	}
}
