package c.theinfiniteloop.rvsafe;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.*;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                    .field("_id").equal(disaster_id).get();

            System.out.println(data);
        }

        finally {

            if (mongoClient != null) {
                mongoClient.close();
            }

        }

        try {
            sendDownlaodimagerequest(data.image_url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        return data;

    }

    static void sendDownlaodimagerequest(String url ) throws Exception
    {
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type","image/jpeg");
        con.setDoInput(true);
        con.setDoOutput(true);

        try {


            //Path path = Paths.get("jpeg.jpg");
            byte[] fileContents = new byte[500];
            //System.out.println(fileContents.length);
            //System.out.println("Sending image to server. ");

            OutputStream out = con.getOutputStream();
            DataOutputStream image = new DataOutputStream(out);

            //image.writeInt(bytes.length);
            image.write(fileContents, 0, fileContents.length);

            FileOutputStream stream = new FileOutputStream("C:\\Users\\suhas\\Desktop\\MicrosoftApp\\app\\src\\main\\res\\drawable");
            try {
                stream.write(fileContents);
            } finally {
                stream.close();
            }




            image.close();
            // close the output stream
            out.close();

        }catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // define object for the reply from the server
        BufferedInputStream in = new BufferedInputStream(con.getInputStream());
        //Get response from server
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
//        // read in the response from the server
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//            //System.out.println(inputLine);
//        }
//        System.out.println(response.length());
//        // close the input stream
//        in.close();
        byte[] b = org.apache.commons.io.IOUtils.toByteArray(in);
        System.out.println(b.length);


    }



}


    public static WantToHelpGroupData queryWanttohelpdata(int id){
        morphia.mapPackage("c.theinfiniteloop.rvsafe");


        WantToHelpGroupData data ;

        try{
            mongoClient = new MongoClient(uri);
            datastore = morphia.createDatastore(mongoClient, "LocationData");
            data = datastore.createQuery(WantToHelpGroupData.class)
                    .field("_id").equal(id).get();

            System.out.println(data);
        }

        finally {

            if (mongoClient != null) {
                mongoClient.close();
            }

        }

        return data;
    }

    public static VolunteerGroupData queryvolunteerData(int id)
    {
        morphia.mapPackage("c.theinfiniteloop.rvsafe");


        VolunteerGroupData data ;

        try{
            mongoClient = new MongoClient(uri);
            datastore = morphia.createDatastore(mongoClient, "LocationData");
            data = datastore.createQuery(VolunteerGroupData.class)
                    .field("_id").equal(id).get();

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
            MongoDatabase database = mongoClient.getDatabase("LocationData");
            FindIterable<Document> coll = database.getCollection("DisasterData").find();

            MongoCursor<Document> iterator = coll.iterator();

            while(iterator.hasNext()){
                Document obj= iterator.next();
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
