package rb.com.care.purge.service;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;
import rb.com.care.purge.model.IndexRequestDto;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SearchService {
    public String searchFies(String userId) throws FileNotFoundException, IOException, ParseException;
}
