package com.varsha.customer.kafka_config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.varsha.customer.constant.AppConstant;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic topic() {
		return TopicBuilder
				.name(AppConstant.CREATED_CUSTOMER)
				.build();
	}
}
