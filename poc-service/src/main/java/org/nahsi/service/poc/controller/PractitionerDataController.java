package org.nahsi.service.poc.controller;

import org.hl7.fhir.r4.model.Practitioner;
import org.nahsi.service.poc.service.PractitionerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.uhn.fhir.context.FhirContext;

/**
	* Serves practitioner data in FHIR format.
	* 
 * @author Martin Smock
 */
@RestController
@RequestMapping(value = "/practitioner")
public class PractitionerDataController {

	@Autowired
	private PractitionerDataService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getHpData(@PathVariable("id") String id) {
		Practitioner practitioner = service.get(id);
		String serialized = FhirContext.forR4().newJsonParser().encodeResourceToString(practitioner);
		return ResponseEntity.ok(serialized);
	}

	@PostMapping
	public ResponseEntity<String> createHpData(@RequestBody String request) {
		Practitioner parsed = FhirContext.forR4().newJsonParser().parseResource(Practitioner.class, request);
		return ResponseEntity.ok(service.create(parsed));
	}

	@PutMapping
	public ResponseEntity<String> updateHpData(@RequestBody String request) {
		Practitioner parsed = FhirContext.forR4().newJsonParser().parseResource(Practitioner.class, request);
		return ResponseEntity.ok(service.update(parsed));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteHpData(@PathVariable("id") String id) {
		return ResponseEntity.ok(service.delete(id));
	}

}
