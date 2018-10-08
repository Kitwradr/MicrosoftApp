package c.theinfiniteloop.rvsafe;

import android.annotation.TargetApi;

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

                //insertVolunteerGroupsData();
                //insertDisasterData();
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

    @TargetApi(24)
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

    public static void insertImagesintoBlob (File image)throws java.lang.Exception
    {
        String accountName = "rvsafeimages";
        String accountKey = "391TMmlvDdRWu+AsNX+ZMl1i233YQfP5dxo/xhMrPm22KtwWwwMmM9vFAJpJHrGXyBrTW4OoAInjHnby9Couug==";

        SharedKeyCredentials creds = new SharedKeyCredentials(accountName, accountKey);
        // We are using a default pipeline here, you can learn more about it at https://github.com/Azure/azure-storage-java/wiki/Azure-Storage-Java-V10-Overview
        final ServiceURL serviceURL = new ServiceURL(new URL("http://" + accountName + ".blob.core.windows.net"), StorageURL.createPipeline(creds, new PipelineOptions()));

        // Let's create a container using a blocking call to Azure Storage
        // If container exists, we'll catch and continue
        ContainerURL  containerURL = serviceURL.createContainerURL("imagescontainer");

        try {
            ContainerCreateResponse response = containerURL.create(null, null,null).blockingGet();
            System.out.println("Container Create Response was " + response.statusCode());
        } catch (RestException e){
            if (e instanceof RestException && ((RestException)e).response().statusCode() != 409) {
                throw e;
            } else {
                System.out.println("imagescontainer container already exists, resuming...");
            }
        }

        final BlockBlobURL blobURL = containerURL.createBlockBlobURL("SampleBlob.png");

        // Listening for commands from the console
        //System.out.println("Enter a command");
        //System.out.println("(P)utBlob | (L)istBlobs | (G)etBlob | (D)eleteBlobs | (E)xitSample");
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Uploading the sample file into the container: " + containerURL );
        try {
            uploadFile(blobURL, image);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    static void uploadFile(BlockBlobURL blob, File sourceFile) throws IOException {

        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(sourceFile.toPath());

        // Uploading a file to the blobURL using the high-level methods available in TransferManager class
        // Alternatively call the PutBlob/PutBlock low-level methods from BlockBlobURL type
        TransferManager.uploadFileToBlockBlob(fileChannel, blob, 8*1024*1024, null)
                .subscribe(response-> {
                    System.out.println("Completed upload request.");
                    System.out.println(response.response().statusCode());
                });
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

    static void getBlob(BlockBlobURL blobURL, File sourceFile) {
        try {
            // Get the blob using the low-level download method in BlockBlobURL type
            // com.microsoft.rest.v2.util.FlowableUtil is a static class that contains helpers to work with Flowable
            // BlobRange is defined from 0 to 4MB
            blobURL.download(new BlobRange().withOffset(0).withCount(4*1024*1024L), null, false, null)
                    .flatMapCompletable(response -> {
                        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(sourceFile.getPath()), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                        return FlowableUtil.writeFile(response.body(null), channel);
                    }).doOnComplete(()-> System.out.println("The blob was downloaded to " + sourceFile.getAbsolutePath()))
                    // To call it synchronously add .blockingAwait()
                    .subscribe();
        } catch (Exception ex){

            System.out.println(ex.toString());
        }
    }
    static void listBlobs(ContainerURL containerURL) {
        // Each ContainerURL.listBlobsFlatSegment call return up to maxResults (maxResults=10 passed into ListBlobOptions below).
        // To list all Blobs, we are creating a helper static method called listAllBlobs,
        // and calling it after the initial listBlobsFlatSegment call
        ListBlobsOptions options = new ListBlobsOptions();
        options.withMaxResults(10);

        containerURL.listBlobsFlatSegment(null, options, null).flatMap(containerListBlobFlatSegmentResponse ->
                listAllBlobs(containerURL, containerListBlobFlatSegmentResponse))
                .subscribe(response-> {
                    System.out.println("Completed list blobs request.");
                    System.out.println(response.statusCode());
                });
    }

    private static Single <ContainerListBlobFlatSegmentResponse> listAllBlobs(ContainerURL url, ContainerListBlobFlatSegmentResponse response) {
        // Process the blobs returned in this result segment (if the segment is empty, blobs() will be null.
        if (response.body().segment() != null) {
            for (BlobItem b : response.body().segment().blobItems()) {
                String output = "Blob name: " + b.name();
                if (b.snapshot() != null) {
                    output += ", Snapshot: " + b.snapshot();
                }
                System.out.println(output);
            }
        }
        else {
            System.out.println("There are no more blobs to list off.");
        }

        // If there is not another segment, return this response as the final response.
        if (response.body().nextMarker() == null) {
            return Single.just(response);
        } else {
            /*
            IMPORTANT: ListBlobsFlatSegment returns the start of the next segment; you MUST use this to get the next
            segment (after processing the current result segment
            */

            String nextMarker = response.body().nextMarker();

            /*
            The presence of the marker indicates that there are more blobs to list, so we make another call to
            listBlobsFlatSegment and pass the result through this helper function.
            */

            return url.listBlobsFlatSegment(nextMarker, new ListBlobsOptions().withMaxResults(10), null)
                    .flatMap(containersListBlobFlatSegmentResponse ->
                            listAllBlobs(url, containersListBlobFlatSegmentResponse));
        }
    }






}
