package awaitility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class AwaitilityTest {

    @Test
    public void canWaitOnOtherStuff() throws IOException {

        // Create the file in another thread

        File outputFolder = new File(System.getProperty("user.dir"),"temp");
        File outputFile = new File(outputFolder,System.currentTimeMillis()+".txt");

        ThreadedFileCreator w = new ThreadedFileCreator(
                outputFolder, outputFile, 3000 );
        new Thread(w).start();

        // Wait for file to be created

        await().
                atMost(Duration.ofMillis(5000)).
                until(() -> outputFile.exists());

        // Check File Contents

        final byte[] contents = Files.readAllBytes(outputFile.toPath());
        Assertions.assertEquals("File Contents", new String(contents));
    }
}
