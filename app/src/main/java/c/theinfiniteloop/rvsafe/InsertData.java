package c.theinfiniteloop.rvsafe;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.*;
import org.bson.Document;

import java.util.Random;


public class InsertData {

    public static void main(String[] args) {

        Random rand  = new Random();

        double min1 = -30,max1 = -37 ,min2 = 145 , max2 = 155;

        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://kitwradr:uSnJYwRZ3plpfCuAUwSYhg5FQSAIu7p2wH8FKreJ5FQfolbYH1TcMnvtWnXZB1PKZBmGkATM8wHPiGwRNp2UhA==@kitwradr.documents.azure.com:10255/?ssl=true&replicaSet=globaldb"));





        try {

            DB database = mongoClient.getDB("LocationData");



            DBCollection collection = database.getCollection("VolunteerGroups");


            for (int numLocations = 0; numLocations < 10; numLocations++) {

                LocationData data = new LocationData(rand.nextDouble()*max1+min1,rand.nextDouble()*max2+min2,"Rescuer");

                DBObject dbObject = toDBObject(data);

                collection.insert(dbObject);
            }

            Document document = new Document("Latitude","12.45")
                    .append("Longitude","13.67");

            //collection.insertOne(document);

        } finally {
            if (mongoClient != null) {
                mongoClient.close();

            }

        }
    }

    public static final DBObject toDBObject(LocationData data) {
        return new BasicDBObject("clientType",data.getClientType())
                .append("Latitude",data.getLatitude())
                .append("Longitude",data.getLongitude());
    }



}
