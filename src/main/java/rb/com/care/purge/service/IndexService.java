package rb.com.care.purge.service;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.LockObtainFailedException;

import java.io.IOException;

public interface IndexService {

    //Sequential
    public String generateSequentialIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException;

    //Thread Approach
    public void generateParallelIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException;

}
