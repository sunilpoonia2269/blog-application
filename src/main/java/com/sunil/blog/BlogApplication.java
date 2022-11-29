package com.sunil.blog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		// System.out.println(this.passwordEncoder.encode("Sunil@123"));

	}

}
