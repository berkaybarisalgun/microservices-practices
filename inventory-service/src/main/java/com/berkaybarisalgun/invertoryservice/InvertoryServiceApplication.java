package com.berkaybarisalgun.invertoryservice;

import com.berkaybarisalgun.invertoryservice.model.Inventory;
import com.berkaybarisalgun.invertoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InvertoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvertoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args->{
			Inventory invertory=new Inventory();
			invertory.setSkuCode("iphone_13");
			invertory.setQuantity(100);

			Inventory invertory1=new Inventory();
			invertory1.setSkuCode("iphone_13_red");
			invertory1.setQuantity(2);

			inventoryRepository.save(invertory);
			inventoryRepository.save(invertory1);

		};
	}

}
