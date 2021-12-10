package rb.com.care.purge.serviceImpl;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class SearchServiceImplTest {

    @Test
    void searchFies() throws IOException, ParseException {
        System.out.println("Searching files, please wait ...");
        Instant searchStart = Instant.now();
        SearchServiceImpl searchService = new SearchServiceImpl();
        searchService.searchFies();
        Instant searchStop = Instant.now();
        Duration timeElapsedSearch = Duration.between(searchStart, searchStop);
        System.out.println("Time taken for searching: " + timeElapsedSearch.getSeconds());
    }
}