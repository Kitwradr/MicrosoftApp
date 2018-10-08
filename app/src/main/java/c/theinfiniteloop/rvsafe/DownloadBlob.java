package c.theinfiniteloop.rvsafe;

import com.microsoft.azure.storage.blob.BlobRange;
import com.microsoft.azure.storage.blob.BlockBlobURL;
import com.microsoft.azure.storage.blob.ContainerURL;
import com.microsoft.azure.storage.blob.PipelineOptions;
import com.microsoft.azure.storage.blob.ServiceURL;
import com.microsoft.azure.storage.blob.SharedKeyCredentials;
import com.microsoft.azure.storage.blob.StorageURL;
import com.microsoft.azure.storage.blob.models.ContainerCreateResponse;
import com.microsoft.rest.v2.RestException;
import com.microsoft.rest.v2.util.FlowableUtil;

import java.io.File;
import java.net.URL;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DownloadBlob {

    public static void main(String[] args) throws java.lang.Exception{

        File downloadedFile = File.createTempFile("downloadedFile", ".jpg");

        downloadBlob("tamilnadutsunami.jpg",downloadedFile);

    }

    static void downloadBlob(String name,File sourceFile) throws java.lang.Exception
    {

        String accountName = "rvsafeimages";
        String accountKey = "391TMmlvDdRWu+AsNX+ZMl1i233YQfP5dxo/xhMrPm22KtwWwwMmM9vFAJpJHrGXyBrTW4OoAInjHnby9Couug==";

        SharedKeyCredentials creds = new SharedKeyCredentials(accountName, accountKey);
        // We are using a default pipeline here, you can learn more about it at https://github.com/Azure/azure-storage-java/wiki/Azure-Storage-Java-V10-Overview
        final ServiceURL serviceURL = new ServiceURL(new URL("http://" + accountName + ".blob.core.windows.net"), StorageURL.createPipeline(creds, new PipelineOptions()));

        // Let's create a container using a blocking call to Azure Storage
        // If container exists, we'll catch and continue
        ContainerURL containerURL = serviceURL.createContainerURL("imagescontainer");

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



        // Listening for commands from the console
        //System.out.println("Enter a command");
        //System.out.println("(P)utBlob | (L)istBlobs | (G)etBlob | (D)eleteBlobs | (E)xitSample");
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //System.out.println("Uploading the sample file into the container: " + containerURL );
        final BlockBlobURL blobURL = containerURL.createBlockBlobURL(name);

        System.out.println(blobURL);
        getBlob(blobURL,sourceFile);
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
}
