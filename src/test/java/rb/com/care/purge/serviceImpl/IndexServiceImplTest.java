package rb.com.care.purge.serviceImpl;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import rb.com.care.purge.service.IndexService;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
class IndexServiceImplTest {

    @Autowired
    private IndexServiceImpl indexService;

    // Index generation Sequentially
    @Test
    void testGenerateSequentialIndex() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        indexService.generateSequentialIndex();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }


    // Index generation concurrently with multi thread approch
    @Test
    void testParallelIndexGenerate() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        indexService.generateParallelIndex();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }

    // Adding individual indexes in to single Directory
    @Test
    void testMergeIndexesInSingleDirectory() throws IOException, ParseException {
        Instant start = Instant.now();
        indexService.mergeIndexesInSingleDirectory();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for merged Directory generation: " + timeElapsed.getSeconds());
    }
}