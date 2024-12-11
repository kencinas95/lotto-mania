package ar.com.kaijusoftware.lotto_mania;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class LottoManiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LottoManiaApplication.class, args);
	}

}
