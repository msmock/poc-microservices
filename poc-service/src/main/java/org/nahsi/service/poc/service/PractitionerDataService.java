package org.nahsi.service.poc.service;

import java.util.UUID;

import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

/**
 * @author Martin Smock
 */
@Service
public class PractitionerDataService {

	public Practitioner get(String id) {
		
		Practitioner practitioner = new Practitioner();

		practitioner.setId(id);
		practitioner.addName().addGiven("James").setFamily("Wagner");

		Identifier identifier = new Identifier();
		identifier.setValue(UUID.randomUUID().toString());

		practitioner.addIdentifier(identifier); 

		return practitioner;
	}

	public String create(Practitioner data) {

		FhirContext ctx = FhirContext.forR4();
		IParser parser = ctx.newJsonParser();
		parser.setPrettyPrint(true);
		String serialized = parser.encodeResourceToString(data);

		System.out.println("Accepted patient object:");
		System.out.println(serialized);
		
		return serialized;
	}

	public String update(Practitioner data) {

		FhirContext ctx = FhirContext.forR4();
		IParser parser = ctx.newJsonParser();
		parser.setPrettyPrint(true);
		String serialized = parser.encodeResourceToString(data);

		System.out.println("Updated patient object:");
		System.out.println(serialized);
		
		return serialized;
	}

	public String delete(String id) {
		return "Deleted hp data object with id: "+ id;
	}
}
