package org.nahsi.service.poc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin Smock
 */
@RestController
@Service
public class DiscoveryService {

	@Autowired
	private DiscoveryClient discoveryClient;

	public List<ServiceInstance> servicesByName(String name) {
		return this.discoveryClient.getInstances(name);
	}

}
