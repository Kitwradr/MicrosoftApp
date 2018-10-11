package c.theinfiniteloop.rvsafe;

import android.annotation.TargetApi;
import android.widget.ArrayAdapter;

import com.microsoft.azure.storage.blob.BlobRange;
import com.microsoft.azure.storage.blob.BlockBlobURL;
import com.microsoft.azure.storage.blob.ContainerURL;
import com.microsoft.azure.storage.blob.ListBlobsOptions;
import com.microsoft.azure.storage.blob.PipelineOptions;
import com.microsoft.azure.storage.blob.ServiceURL;
import com.microsoft.azure.storage.blob.SharedKeyCredentials;
import com.microsoft.azure.storage.blob.StorageURL;
import com.microsoft.azure.storage.blob.TransferManager;
import com.microsoft.azure.storage.blob.models.BlobItem;
import com.microsoft.azure.storage.blob.models.ContainerCreateResponse;
import com.microsoft.azure.storage.blob.models.ContainerListBlobFlatSegmentResponse;
import com.microsoft.rest.v2.RestException;
import com.microsoft.rest.v2.util.FlowableUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.*;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Target;
import java.net.URL;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import io.reactivex.Single;


public class InsertData
{
    static Datastore datastore;


    public static void main(String[] args) {


        final  Morphia morphia = new Morphia();

        MongoClient mongoClient ;

        MongoClientURI uri = new MongoClientURI("mongodb://kitwradr:uSnJYwRZ3plpfCuAUwSYhg5FQSAIu7p2wH8FKreJ5FQfolbYH1TcMnvtWnXZB1PKZBmGkATM8wHPiGwRNp2UhA==@kitwradr.documents.azure.com:10255/?ssl=true&replicaSet=globaldb");


        mongoClient = new MongoClient(uri);

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

                //DBObject dbObject = toDBObject(data);

                //collection.insertOne(dbObject);
            }



            //collection.insertOne(document);

        } finally {
            if (mongoClient != null) {

                insertVolunteerGroupsData();
                //insertDisasterData();
                //insertwanttohelpdata();
                mongoClient.close();

            }
        }
//        File image = new File("drawable/tamilnadutsunami.jpg");
//        try{
//            insertImagesintoBlob(image);
//        }
//        catch (Exception e)
//        {
//            System.out.println("Error occured!!");
//            e.printStackTrace();
//        }


    }

//    static final DBObject toDBObject(LocationData data) {
//        return new BasicDBObject("clientType",data.getClientType())
//                .append("Latitude",data.getLatitude())
//                .append("Longitude",data.getLongitude());
//    }


    public static void insertVolunteerGroupsData()
    {


        VolunteerGroupData group = new VolunteerGroupData();

        group.set_id(63);
        group.setGroup_name("TEAM RELIEF CORPS");
        group.setNumber_of_members(27);
        group.setStart_location("BTM LAYOUT");
        group.setStarted_by("JON SMITH");
        group.setDate("25/9/18");
        group.setDestination("THIRUVANATHAPURAM");
        group.setEmail_id("yatish04@gmail.com");
        group.setExpected_duration("2-3 weeks");
        group.setGroup_description("Help victims stuck in Kerala Floods. We wish to help deliver food packets and other accessories" +
                ".Come join us in our noble cause");
        group.setFemale_members(3);
        group.setMale_members(5);
        group.setGroup_image_url("url to be updated");

        datastore.save(group);
    }






    static void insertDisasterData()
    {
        DisasterData disasterData = new DisasterData();
        disasterData.setDisaster_id(6);
        disasterData.setDisaster_name("TAMILNADU TSUNAMI");
        disasterData.setWantToHelp_id(42);
        disasterData.setImage_url("https://rvsafeimages.blob.core.windows.net/imagescontainer/tamilnadutsunami.jpg?st=2018-10-08T15%3A12%3A39Z&se=2018-10-09T15%3A12%3A39Z&sp=rl&sv=2018-03-28&sr=b&sig=FeXhEAdOvTZICyoNRJc2aDLcokwsOMxg16%2FNYa567Po%3D");
        disasterData.setDisaster_type("TSUNAMI");


        datastore.save(disasterData);

    }

    static void insertwanttohelpdata()
    {
        WantToHelpGroupData data = new WantToHelpGroupData();
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();

        list1.add(64);
        list1.add(65);
        list1.add(66);
        list2.add(73);
        list2.add(74);


        data.setWantToHelp_id(42);
        data.setVolunteerGroup_ids(list1);
        data.setHelpAndDonate_ids(list2);

        datastore.save(data);
    }



}
