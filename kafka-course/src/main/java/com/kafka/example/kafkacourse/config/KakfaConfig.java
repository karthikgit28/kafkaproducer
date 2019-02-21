package com.kafka.example.kafkacourse.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakfaConfig {
	
	String bootStrapValue = "localhost:9092";
	
//	@Bean
//	Properties getProperty() {
//		Properties property = new Properties();
//		property.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapValue);
//		property.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		property.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		return property;
//	}
//	
//	
//	@Bean
//	KafkaProducer<String , String> createProducer(){
//		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(getProperty());
//		return producer;
//	}
//	
//	@Bean
//	ProducerRecord<String, String> record(){
//		ProducerRecord<String, String> recorder = new ProducerRecord<String, String>("first_topic", "Hello Code");
//		return recorder;
//	}
	

}
