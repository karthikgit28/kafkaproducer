package com.kafka.example.kafkacourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kafka.example.kafkacourse.producer.KakfaProducer;
import com.kafka.example.kafkacourse.twitter.TwitterClient;

@SpringBootApplication
public class KafkaCourseApplication implements CommandLineRunner {

//	@Autowired
//	private KakfaProducer kafca;
	
	@Autowired
	private TwitterClient twitter;

	public static void main(String[] args) {
		SpringApplication.run(KafkaCourseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// kafca.sendMessage();
		// kafca.callbackFunc();
		//kafca.callbackFuncWithKeys();
		twitter.sendMessage();
	}

}
