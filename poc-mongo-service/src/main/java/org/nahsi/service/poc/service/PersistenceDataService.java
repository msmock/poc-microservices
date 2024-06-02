package org.nahsi.service.poc.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
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
     * Search a patient from DB by fhir id
     *
     * @param id the fhir id of the patient object
     * @return HAPI Patient object
     */
    public Patient getById(String id) {

        MongoCollection<Document> collection = getCollection();
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
     * Save hapi patient in DB
     *
     * @param patient HAPI FHIR patient
     * @return the serialized object stored
     */
    public String create(Patient patient) {

        MongoCollection<Document> collection = getCollection();

        // check if the patient object with fhir id already exists
        String id = getUnqualifiedId(patient);
        if (collection.find(eq("id", id)).first() != null)
            return "Patient with id = " + id + " already exists.";

        // else
        FhirContext ctx = FhirContext.forR4();
        IParser parser = ctx.newJsonParser();
        String serialized = parser.encodeResourceToString(patient);

        Document doc = Document.parse(serialized);
        InsertOneResult result = collection.insertOne(doc);

        logger.info("Accepted patient object: {}", result.getInsertedId());

        return serialized;
    }

    /**
     * Update a hapi patient by using the fhir id
     *
     * @param patient the updated hapi patient
     * @return a report
     */
    public String update(Patient patient) {

        MongoCollection<Document> collection = getCollection();

        // convert to bson object
        FhirContext ctx = FhirContext.forR4();
        IParser parser = ctx.newJsonParser();
        String serialized = parser.encodeResourceToString(patient);
        Document doc = Document.parse(serialized);

        // filter for object to be replaced
        Bson query = eq("id", getUnqualifiedId(patient));

        // Instructs the driver to insert a new document if none match the query
        ReplaceOptions opts = new ReplaceOptions().upsert(true);

        // Replaces the first document that matches the filter with a new document
        UpdateResult result = collection.replaceOne(query, doc, opts);

        // log the number of modified documents and the upserted document ID, if an upsert was performed
        logger.info("Modified document count: {}", result.getModifiedCount());
        logger.info("Upserted id: {}", result.getUpsertedId());

        return "Updated " + result.getModifiedCount() + " objects, and inserted object with id " + result.getUpsertedId();
    }

    /**
     * Delete a object in DB by fhir id
     *
     * @param id the fhir id of the object to be deleted
     * @return a report
     */
    public String delete(String id) {

        MongoCollection<Document> collection = getCollection();

        // filter for object to be deleted
        Bson query = eq("id", id);

        DeleteResult result = collection.deleteOne(query);
        logger.info("Deleted document count: {} ", result.getDeletedCount());

        return "Deleted "+result.getDeletedCount()+" objects.";
    }

    /**
     * TODO move configuration to config server
     *
     * <p>Get a mongo client, the database and the patient collection.</p>
     *
     * @return MongoCollection
     */
    private MongoCollection<Document> getCollection() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("poc-test");
        return database.getCollection("patients");
    }

    /**
     * extract the fhir id from the qualified id (e.g., Patient/12345)
     *
     * @param patient the hapi patient
     * @return the unqualified id
     */
    private String getUnqualifiedId(Patient patient) {
        String[] idParts = patient.getId().split("/");
        if (idParts.length == 2 && idParts[0].equals("Patient"))
            return patient.getId().split("/")[1];
        return null;
    }

}
