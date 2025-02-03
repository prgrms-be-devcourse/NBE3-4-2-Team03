package com.programmers.pcquotation;

import jakarta.validation.Valid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PcquotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcquotationApplication.class, args);
	}

}
