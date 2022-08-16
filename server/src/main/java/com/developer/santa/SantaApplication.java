package com.developer.santa;

import com.developer.santa.member.config.properties.AppProperties;
import com.developer.santa.member.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, CorsProperties.class})
public class SantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SantaApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
