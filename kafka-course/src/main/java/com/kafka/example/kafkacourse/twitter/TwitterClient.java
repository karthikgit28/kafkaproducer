package com.kafka.example.kafkacourse.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Service
public class TwitterClient {
	
	String consumerKey = "FcNkCHOnfkj9WsSrhqQ11iC9C";
	String consumerSecret = "pDG3tlSkqVcb7umRsrmhGxz0q073heIQ5n1wvETQwY7mPJ7SAY";
	String token = "1131644221-sy8GW3opWNgjC55lypGBhOMORkBbhaVvYzZsPfr";
	String secret = "eLz8GGKdz5uxJ6y7lwcqcX0NWL9PuRoeENhrlWxCN0Lcf";
	String bootStrapValue = "localhost:9092";
	
	public void sendMessage() {
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(10000);
		
		//Create twitter client
		Client client = createTwitterClient(msgQueue);
		client.connect();
		
		//Create Kakfa producer
		KafkaProducer<String, String> producer = kafkaProducer();
		
		System.out.println("New*************");
		
		//Add shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shuting down process");
			client.stop();
			producer.close();
		}));
		
		//Push msg to queue
		while(!client.isDone()) {
			String msg = null;
			try {
				msg = msgQueue.poll(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				client.stop();
			}
			if(msg != null) {
				System.out.println("Hi Receiver message= " + msg);
				producer.send(new ProducerRecord<String, String>("twitter_tweets", msg), new Callback() {
					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						if(exception != null) {
							System.out.println("Exceptionssss");
						}
					}
				});
			}
		}
	}

	public Client createTwitterClient(BlockingQueue<String> msgQueue) {
		
		Hosts hoseBirdHost = new HttpHosts(Constants.STREAM_HOST);
		
		StatusesFilterEndpoint hoseBirdEndPoint = new StatusesFilterEndpoint();
		
		//To track which tweets
		ArrayList<String> terms = Lists.newArrayList("kafka","usa","bitcoin");
		hoseBirdEndPoint.trackTerms(terms);
		
		//Authentication purpose
		Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		
		ClientBuilder builder = new ClientBuilder()
				.name("kafka-tweet")
				.hosts(hoseBirdHost)
				.authentication(hosebirdAuth)
				.endpoint(hoseBirdEndPoint)
				.processor(new StringDelimitedProcessor(msgQueue));
		
		Client client = builder.build();
		return client;
	}
	
	public KafkaProducer<String, String> kafkaProducer(){
		Properties proeprty = new Properties();
		proeprty.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapValue);
		proeprty.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		proeprty.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		//idempotence producer(Safe Producer)
		proeprty.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		proeprty.setProperty(ProducerConfig.ACKS_CONFIG, "all");
		proeprty.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
		proeprty.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
		
		//Higher Throughput
		proeprty.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		proeprty.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
		proeprty.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); //32kb
		
		KafkaProducer<String, String> kafka = new KafkaProducer<String, String>(proeprty);
		return kafka;
	}
	
}
