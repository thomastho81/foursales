package br.com.thomas.foursales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.thomas.foursales"})
public class FoursalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoursalesApplication.class, args);
	}

}
