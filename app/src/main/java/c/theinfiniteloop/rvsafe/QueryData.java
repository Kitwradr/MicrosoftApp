package c.theinfiniteloop.rvsafe;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.*;
import org.bson.Document;

import java.util.Random;


public class QueryData {


    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://kitwradr:uSnJYwRZ3plpfCuAUwSYhg5FQSAIu7p2wH8FKreJ5FQfolbYH1TcMnvtWnXZB1PKZBmGkATM8wHPiGwRNp2UhA==@kitwradr.documents.azure.com:10255/?ssl=true&replicaSet=globaldb"));

        MongoDatabase database = mongoClient.getDatabase("LocationData");

        MongoCollection groupsData = database.getCollection("VolunteerGroups");

        FindIterable<Document> docs = groupsData.find(new Document());

        for (Document doc: docs) {

            System.out.println(doc);

        }

    }





}
