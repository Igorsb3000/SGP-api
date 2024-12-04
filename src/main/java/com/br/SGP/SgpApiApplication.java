package com.br.SGP;

import com.br.SGP.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SgpApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgpApiApplication.class, args);
	}

}
