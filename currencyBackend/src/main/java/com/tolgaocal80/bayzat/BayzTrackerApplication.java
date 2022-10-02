package com.tolgaocal80.bayzat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BayzTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BayzTrackerApplication.class, args);
	}

}
