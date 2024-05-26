package org.nahsi.service.poc.service;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

/**
 * @author Martin Smock
 */
@Service
public class PatientDataService {

	public Patient get(String id) {

		Patient patient = new Patient();
		patient.setId(id);
		patient.addName().addGiven("James").setFamily("Simpson");

		return patient;

	}

	public String create(Patient data) {

		FhirContext ctx = FhirContext.forR4();
		IParser parser = ctx.newJsonParser();
		parser.setPrettyPrint(true);
		String serialized = parser.encodeResourceToString(data);

		System.out.println("Accepted patient object:");
		System.out.println(serialized);

		return serialized;
	}

	public String update(Patient data) {

		FhirContext ctx = FhirContext.forR4();
		IParser parser = ctx.newJsonParser();
		parser.setPrettyPrint(true);
		String serialized = parser.encodeResourceToString(data);

		System.out.println("Updated patient object:");
		System.out.println(serialized);

		return serialized;
	}

	public String delete(String id) {
		return "Deleted patient object with id: " + id;
	}

}
