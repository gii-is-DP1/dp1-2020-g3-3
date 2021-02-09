package org.springframework.samples.aerolineasAAAFC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AerolineaApplication {


	public static void main(String[] args) {
		SpringApplication.run(AerolineaApplication.class, args);
		
	}

}