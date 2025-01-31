package com.programmers.pcquotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class PcquotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcquotationApplication.class, args);
    }

}
