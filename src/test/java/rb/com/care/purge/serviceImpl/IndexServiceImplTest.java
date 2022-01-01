package rb.com.care.purge.serviceImpl;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IndexServiceImpl.class)
//@TestPropertySource("/application.properties")
class IndexServiceImplTest {

//    @Before
//    public void setup(){
//
////        ReflectionTestUtils.setField(indexService, "dataSrcDirectory", "Test");
////        ReflectionTestUtils.setField(indexService, "indexDirectory", "IndexDir");
//    }

//    @Value( "${file.data.index}" )
//    String indexDirectory;
//
//    @Test
//    public void testValueSetup() {
//        assertEquals("IndexDir", indexDirectory);
//    }

    @Test
    void startIndex() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        IndexServiceImpl indexService = new IndexServiceImpl();
        indexService.createIndex();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }


    @Test
    void createIndexes() throws IOException, ParseException {
        System.out.println("Indexing has been started, please wait ...");
        Instant start = Instant.now();
        IndexServiceImpl indexService = new IndexServiceImpl();
        indexService.createIndexes();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for indexing: " + timeElapsed.getSeconds());
    }

    @Test
    void createCommonIndexes() throws IOException, ParseException {
        Instant start = Instant.now();
        IndexServiceImpl indexService = new IndexServiceImpl();
        indexService.createCommonIndexes();
        Instant stop = Instant.now();
        Duration timeElapsed = Duration.between(start, stop);
        System.out.println( "Time taken for merged Directory generation: " + timeElapsed.getSeconds());
    }
}