package org.springframework.samples.aerolineasAAAFC;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
public class AerolineaApplication {

	private static final int ArrayList = 0;

	public static void main(String[] args) {
		SpringApplication.run(AerolineaApplication.class, args);
		
//		BCryptPasswordEncoder pass = new BCryptPasswordEncoder(); 
//		String pss = "F1234%adsa";
//		System.out.println(pass.encode(pss));
	}

}