package com.pszymczyk.kafkatripreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KafkaTripReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaTripReservationApplication.class, args);
	}

}
