package rb.com.care.purge.service;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SearchService {
    public String searchFies() throws FileNotFoundException, IOException, ParseException;
}
