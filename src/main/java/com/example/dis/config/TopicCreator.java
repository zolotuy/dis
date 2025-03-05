package com.example.dis.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

@Component
public class TopicCreator {

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @PostConstruct
    public void createTopic() {
        NewTopic newTopic = new NewTopic("detections-topic", 10, (short) 3); // 10 partitions, replication factor 3
        kafkaAdmin.createOrModifyTopics(newTopic);
    }
}
