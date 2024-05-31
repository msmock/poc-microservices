package org.nahsi.service.poc.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.mongodb.client.result.InsertOneResult;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Martin Smock
 */
@Service
public class PersistenceDataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Search a patient from DB by id
     *
     * @param id
     * @return HAPI Patient object
     */
    public Patient get(String id) {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("poc-test");
        MongoCollection<Document> collection = database.getCollection("test-documents");

        Document doc = collection.find(eq("id", id)).first();

        if (doc != null) {
            FhirContext ctx = FhirContext.forR4();
            IParser parser = ctx.newJsonParser();
            return parser.parseResource(Patient.class, doc.toJson());
        }

        // else
        return new Patient();
    }

    /**
     * Save HAPI Patient in DB
     *
     * @param patient HAPI FHIR patient
     * @return the serialized object stored
     */
    public String create(Patient patient) {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("poc-test");
        MongoCollection<Document> collection = database.getCollection("test-documents");

        // check if the patient object with fhir id already exists
        String id = getUnqualifiedId(patient);
        Document doc = collection.find(eq("id", id)).first();
        if (doc != null)
            return "Patient with id = " + id + " already exists.";

        // else
        FhirContext ctx = FhirContext.forR4();
        IParser parser = ctx.newJsonParser();
        String serialized = parser.encodeResourceToString(patient);

        doc = Document.parse(serialized);
        InsertOneResult result = collection.insertOne(doc);

        logger.info("Accepted patient object: {}", result.getInsertedId());

        return serialized;
    }

    public String update(Patient data) {
        return "Not implemented yet.";
    }

    public String delete(String id) {
        return "Not implemented yet.";
    }

    private String getUnqualifiedId (Patient patient){
        String[] idParts = patient.getId().split("/");
        if (idParts.length == 2 && idParts[0].equals("Patient"))
            return patient.getId().split("/")[1];
        return null;
    }

}
