package com.bbc.anotherhospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnotherhospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnotherhospitalApplication.class, args);
	}


	// 26.10.2023
	// Doctor - Krystian
	// Patient - Mateusz
	// Appointment - Michał
	//  zapoznajcie sie z roznicami miedzy JPA a JDBC. Sprobujcie uzyc NamedJdbcTemplate w repo.

	// 02.11
	// factory i zabezpieczenie encji przed tworzeniem za pomoca prywatnych konstruktorow
	// dokonczyc globalexceptionhandler
	// wyniesc czesc logiki z repo appointment do handlera
	// napisac testy jednostkowe dla handlerow w groovy/spock
}
