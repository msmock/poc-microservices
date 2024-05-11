package org.nahsi.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Martin Smock
 */
@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
public class PocServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocServiceApplication.class, args);
	}

}
