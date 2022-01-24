package rb.com.care.purge.serviceImpl;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import rb.com.care.purge.util.ClassUsingProperty;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
class IndexServiceImplTest {

    @Autowired
    ClassUsingProperty properties;

    @Autowired
    private IndexServiceImpl indexService;

    // Index generation Sequentially
    @Test
    void testGenerateSequentialIndex() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        String output = indexService.generateSequentialIndex();
       // Mockito.when(properties.getDirectory()).thenReturn("Test1");
        //Mockito.when(properties.getIndexDirectory()).thenReturn("Dir1");
        Assertions.assertNotNull(output);
        Assertions.assertEquals("Success", output);
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }


    // Concurrent Index generation with multi thread approach
    @Test
    void testParallelIndexGenerate() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        String output = indexService.generateParallelIndex();
        Assertions.assertNotNull(output);
        Assertions.assertEquals("Success", output);
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }

    // Adding individual indexes in to single Directory
    @Test
    void testMergeIndexesInSingleDirectory() throws IOException, ParseException {
        Instant start = Instant.now();
        String response = indexService.mergeIndexesInSingleDirectory();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Success", response);
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for merged Directory generation: " + timeElapsed.getSeconds());
    }
}