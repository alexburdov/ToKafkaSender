package ru.alex.burdovitsin.tokafkasender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ToKafkaSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToKafkaSenderApplication.class, args);
    }

}
