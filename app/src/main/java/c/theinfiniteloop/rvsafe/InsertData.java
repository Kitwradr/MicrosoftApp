package c.theinfiniteloop.rvsafe;

import com.microsoft.azure.storage.blob.ContainerURL;
import com.microsoft.azure.storage.blob.ServiceURL;
import com.microsoft.azure.storage.blob.SharedKeyCredentials;
import com.microsoft.azure.storage.blob.models.ContainerCreateResponse;
import com.microsoft.rest.v2.RestException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.*;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;



public class InsertData
{

    public static Datastore datastore;


    public static void main(String[] args) {

        final Morphia morphia = new Morphia();

        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://kitwradr:uSnJYwRZ3plpfCuAUwSYhg5FQSAIu7p2wH8FKreJ5FQfolbYH1TcMnvtWnXZB1PKZBmGkATM8wHPiGwRNp2UhA==@kitwradr.documents.azure.com:10255/?ssl=true&replicaSet=globaldb"));

        morphia.mapPackage("c.theinfiniteloop.rvsafe");

        datastore = morphia.createDatastore(mongoClient, "LocationData");


        datastore.ensureIndexes();

        Random rand  = new Random();

        double min1 = -30,max1 = 6 ,min2 = 145 , max2 = 6;

        try {

            MongoDatabase database = mongoClient.getDatabase("LocationData");

            MongoCollection collection = database.getCollection("VolunteerGroups");


            for (int numLocations = 0; numLocations < 10; numLocations++) {

                LocationData data = new LocationData(rand.nextDouble()*max1+min1,rand.nextDouble()*max2+min2,"Rescuer");

                DBObject dbObject = toDBObject(data);

                //collection.insertOne(dbObject);
            }



            //collection.insertOne(document);

        } finally {
            if (mongoClient != null) {

                insertVolunteerGroupsData();
                mongoClient.close();

            }
        }

    }

    static final DBObject toDBObject(LocationData data) {
        return new BasicDBObject("clientType",data.getClientType())
                .append("Latitude",data.getLatitude())
                .append("Longitude",data.getLongitude());
    }

    public static void insertVolunteerGroupsData()
    {
        int group_id;

        String group_name;

        String group_image_url;

        String group_description;

        String started_by;

        double location_lat;

        double location_long;

        LocalDate date;

        float expected_duration;

        int male_members;

        int female_members;

        String email_id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        String dat = "16/08/2016";
        LocalDate localDate = LocalDate.parse(dat, formatter);

        
        VolunteerGroupData group = new VolunteerGroupData();

        group.setGroup_id(1);
        group.setDate(localDate);
        group.setEmail_id("Suhas");
        group.setExpected_duration(5);
        group.setGroup_description("Courageous group!!!");
        group.setFemale_members(9);
        group.setMale_members(10);
        group.setLocation_lat(12.57);
        group.setLocation_long(34.56);
        group.setGroup_image_url("yes");

        datastore.save(group);
    }

    public static void insertImagesintoBlob()
    {
        SharedKeyCredentials creds = new SharedKeyCredentials(accountName, accountKey);
// We are using a default pipeline here, you can learn more about it at https://github.com/Azure/azure-storage-java/wiki/Azure-Storage-Java-V10-Overview
        final ServiceURL serviceURL = new ServiceURL(new URL("http://" + accountName + ".blob.core.windows.net"), StorageURL.createPipeline(creds, new PipelineOptions()));

// Let's create a container using a blocking call to Azure Storage
// If container exists, we'll catch and continue
        ContainerURL   containerURL = serviceURL.createContainerURL("quickstart");

        try {
            ContainerCreateResponse response = containerURL.create(null, null).blockingGet();
            System.out.println("Container Create Response was " + response.statusCode());
        } catch (RestException e){
            if (e instanceof RestException && ((RestException)e).response().statusCode() != 409) {
                throw e;
            } else {
                System.out.println("quickstart container already exists, resuming...");
            }
        }

    }



}
