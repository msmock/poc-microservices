package org.nahsi.poc.controller;

import java.util.List;

import org.nahsi.poc.service.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin Smock
 */
@RestController
public class DiscoveryController {

		@Autowired
	private DiscoveryService discoveryService;

	@GetMapping("/discovery")
	public ResponseEntity<List<ServiceInstance>> service(@RequestParam(value = "name", defaultValue = "poc-service") String name) {
		return ResponseEntity.ok(discoveryService.servicesByName(name)); 
	}

}
