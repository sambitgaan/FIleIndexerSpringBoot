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
    void startIndex() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        indexService.createIndex();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }


    // Index generation concurrently with multiple folders
    @Test
    void createIndexes() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        indexService.createIndexes();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }

    // Merging generated directories into common directory
    @Test
    void createCommonIndexes() throws IOException, ParseException {
        Instant start = Instant.now();
        indexService.createCommonIndexes();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for merged Directory generation: " + timeElapsed.getSeconds());
    }
}