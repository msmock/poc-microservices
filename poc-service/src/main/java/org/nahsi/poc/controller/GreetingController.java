package org.nahsi.poc.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.nahsi.poc.model.Greeting;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin Smock
 */
@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public ResponseEntity<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return ResponseEntity.ok(new Greeting(counter.incrementAndGet(), String.format(template, name)));
	}

}
