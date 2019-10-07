package com.pharmsaler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HurtowniaApplication {
	private static final Logger log = LoggerFactory.getLogger(HurtowniaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HurtowniaApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner loadData(AccountRepository repository) {
	// return (args) -> {
	// // repository.save(new Account("test", "test"));
	//
	// // fetch all customers
	// log.info("Customers found with findAll():");
	// log.info("-------------------------------");
	// for (Account customer : repository.findAll()) {
	// log.info(customer.toString());
	// }
	// log.info("");
	//
	// // fetch an individual customer by ID
	// Account customer = repository.findOne(1L);
	// log.info("Customer found with findOne(1L):");
	// log.info("--------------------------------");
	// log.info(customer.toString());
	// log.info("");
	//
	// // fetch customers by last name
	// log.info("Customer found with
	// findByLastNameStartsWithIgnoreCase('Bauer'):");
	// log.info("--------------------------------------------");
	// for (Account bauer : repository.findByLoginStartsWithIgnoreCase("Bauer"))
	// {
	// log.info(bauer.toString());
	// }
	// log.info("");
	// };
	// }
}
