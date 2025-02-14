package ru.alex.burdovitsin.tokafkasender.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.util.ResourceUtils.getFile;

@RestController
@RequestMapping("")
public class KafkaSendController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSendController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping(value = "/send")
    public void sendJsonMessage(@RequestParam String topicName, @RequestParam String path) {
        String request = getFileContent(path);
        kafkaTemplate.send(topicName, request);
    }

    @PutMapping
    public void sendMessage(@RequestParam String topicName,
                            @RequestBody String message
    ) {
        kafkaTemplate.send(topicName, message);
    }

    @KafkaListener(topics = "out_topic_1.0", containerFactory = "stringKafkaListenerContainerFactory")
    public void listenAnswerOne(String answer) {
        System.out.println(answer);
    }

    private static String getFileContent(String path) {
        try {
            return FileUtils.readFileToString(getFile(path), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
