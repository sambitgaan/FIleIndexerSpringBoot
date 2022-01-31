package rb.com.care.purge.serviceImpl;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rb.com.care.purge.util.ClassUsingProperty;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class SearchServiceImplTest {

    @Mock
    ClassUsingProperty properties;

    @Autowired
    private SearchServiceImpl searchService;

    @BeforeEach
    public void beforeEach()
    {
        MockitoAnnotations.initMocks(this);
        properties = Mockito.mock(ClassUsingProperty.class);
    }

    @Test
    void searchFies() throws IOException, ParseException {
        System.out.println("Searching files, please wait ...");
        Instant searchStart = Instant.now();
        String response = searchService.searchFies();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Success", response);
        Instant searchStop = Instant.now();
        Duration timeElapsedSearch = Duration.between(searchStart, searchStop);
        System.out.println("Time taken for searching: " + timeElapsedSearch.getSeconds());
    }

    // Property changes
    @Test
    void testSearchFies() throws IOException, ParseException {
        System.out.println("Searching files, please wait ...");
        Instant searchStart = Instant.now();
        when(properties.getMergedSearch()).thenReturn("Input\\mergedDirSearch.txt");
        when(properties.getFileList()).thenReturn("Input\\List.txt");
        String response = searchService.searchFies();
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Success", response);
        Instant searchStop = Instant.now();
        Duration timeElapsedSearch = Duration.between(searchStart, searchStop);
        System.out.println("Time taken for searching: " + timeElapsedSearch.getSeconds());
    }
}