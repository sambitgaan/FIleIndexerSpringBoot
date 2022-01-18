package rb.com.care.purge.serviceImpl;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import rb.com.care.purge.util.ClassUsingProperty;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class SearchServiceImplTest {

    @Autowired
    private SearchServiceImpl searchService;

    @Test
    void searchFies() throws IOException, ParseException {
        System.out.println("Searching files, please wait ...");
        Instant searchStart = Instant.now();
        searchService.searchFies();
        Instant searchStop = Instant.now();
        Duration timeElapsedSearch = Duration.between(searchStart, searchStop);
        System.out.println("Time taken for searching: " + timeElapsedSearch.getSeconds());
    }
}