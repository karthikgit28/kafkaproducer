package com.kafka.example.kafkacourse.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KakfaProducer {

//	@Autowired
//	private KafkaProducer<String, String> producer;
//	
//	@Autowired
//	private ProducerRecord<String, String> record;
//	
//	public void sendMessage() {
//		producer.send(record);
//		producer.flush();
//		producer.close();
//	}
//	
//	public void callbackFunc() {
//		producer.send(record, new Callback() {
//			
//			@Override
//			public void onCompletion(RecordMetadata metadata, Exception exception) {
//				if(exception == null) {
//					//record is sent successfully
//					System.out.println("Message sent successfully " + "topic = " + metadata.topic()
//					+ "partition = " + metadata.partition() + "offset = " + metadata.offset()
//					+ "timestamp = " + metadata.timestamp());
//				}else {
//					exception.printStackTrace();
//				}
//			}
//		});
//		
//	}
//	
//	
//	public void callbackFuncWithKeys() {
//		
//		for(int i=0;i<10;i++) {
//			String topic = "first_topic";
//			String value = "helloWorld " + Integer.toString(i);
//			String key = "Key_" + Integer.toString(i);//Key always goes to particular partition
//			ProducerRecord<String, String> recorderNew = new ProducerRecord<String, String>(topic,key,value);
//			producer.send(recorderNew, new Callback() {
//				@Override
//				public void onCompletion(RecordMetadata metadata, Exception exception) {
//					if(exception == null) {
//						//record is sent successfully
//						System.out.println("Message sent successfully " + "topic = " + metadata.topic()
//						+ "partition = " + metadata.partition() + "offset = " + metadata.offset()
//						+ "timestamp = " + metadata.timestamp());
//					}else {
//						exception.printStackTrace();
//					}
//				}
//			});
//		}
//	}
	
	
	
}
