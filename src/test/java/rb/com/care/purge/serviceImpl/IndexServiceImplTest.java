package rb.com.care.purge.serviceImpl;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import rb.com.care.purge.util.ClassUsingProperty;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class IndexServiceImplTest {

    @Mock
    ClassUsingProperty properties;

    @Autowired
    private IndexServiceImpl indexService;

    @BeforeEach
    public void beforeEach()
    {
        MockitoAnnotations.initMocks(this);
        properties = Mockito.mock(ClassUsingProperty.class);
    }

    // Index generation Sequentially
    @Test
    void testGenerateSequentialIndex() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        Assertions.assertEquals("Test", properties.getDirectory());
        Assertions.assertEquals("IndexDir", properties.getIndexDirectory());
        String output = indexService.generateSequentialIndex();
        Assertions.assertNotNull(output);
        Assertions.assertEquals("Success", output);
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }

    @Test
    void testGetSerializedIndexWriterConfig() throws IOException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        IndexWriter iw = indexService.getSerializedIndexWriterConfig(properties.getIndexDirectory());
        Assertions.assertNotNull(iw);
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }

    // Property changes
    @Test
    void generateSequentialIndexTest2() throws IOException, ParseException {
        Instant start = Instant.now();
        when(properties.getDirectory()).thenReturn("Test1");
        when(properties.getIndexDirectory()).thenReturn("IndexDir");
        String output = indexService.generateSequentialIndex();
        Assertions.assertNotNull(output);
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }


    // Concurrent Index generation with multi thread approach
    @Test
    void testParallelIndexGenerate() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        when(properties.getDirectory()).thenReturn("Test1");
        when(properties.getIndexDirectory()).thenReturn("IndexDir");
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
        when(properties.getMergedDir()).thenReturn("mergedDir");
        String response = indexService.mergeIndexesInSingleDirectory();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Success", response);
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for merged Directory generation: " + timeElapsed.getSeconds());
    }
}