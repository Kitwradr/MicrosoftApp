package c.theinfiniteloop.rvsafe;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.*;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;


public class QueryData {

    static MongoClient mongoClient ;
    static Datastore datastore;
    static final Morphia morphia = new Morphia();

    static MongoClientURI uri = new MongoClientURI("mongodb://kitwradr:uSnJYwRZ3plpfCuAUwSYhg5FQSAIu7p2wH8FKreJ5FQfolbYH1TcMnvtWnXZB1PKZBmGkATM8wHPiGwRNp2UhA==@kitwradr.documents.azure.com:10255/?ssl=true&replicaSet=globaldb");

    public static void main(String[] args) {

        try{
//            mongoClient = new MongoClient(uri);
//
//
//            MongoDatabase database = mongoClient.getDatabase("LocationData");
//
//            MongoCollection groupsData = database.getCollection("VolunteerGroups");
//
//            FindIterable<Document> docs = groupsData.find(new Document());
//
//            for (Document doc: docs) {
//
//                System.out.println(doc);
//
//            }

        }

        finally {
//            if (mongoClient != null) {
//                mongoClient.close();
//            }
        }

        queryDisasterids();



    }

    public  static DisasterData queryDisasterData(int disaster_id)
    {

        morphia.mapPackage("c.theinfiniteloop.rvsafe");
        DisasterData data;


        try{
            mongoClient = new MongoClient(uri);
            datastore = morphia.createDatastore(mongoClient, "LocationData");
            data = datastore.createQuery(DisasterData.class)
                                .field("_id").equal(50).get();

            System.out.println(data);



        }

        finally {

            if (mongoClient != null) {
                mongoClient.close();
            }

        }

        return data;

    }

    public static List<Integer> queryDisasterids(){

        ArrayList<Integer> ids = new ArrayList<>();

        try{
            mongoClient = new MongoClient(uri);
            DB database = mongoClient.getDB("LocationData");
            DBCollection coll = database.getCollection("DisasterData");

            DBCursor cursor = coll.find();

            while(cursor.hasNext()){
                DBObject obj= cursor.next();
                ids.add(Integer.parseInt(obj.get("_id").toString()));
                System.out.println(ids);
            }
        }

        finally {

            if (mongoClient != null) {
                mongoClient.close();
            }

        }

        return  ids;

    }

}
