package com.chryl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.chryl"})
@MapperScan("com.chryl.mapper")
public class ChrSsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChrSsoApplication.class, args);
	}

}
