package rb.com.care.purge.service;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.LockObtainFailedException;

import java.io.IOException;

public interface IndexService {

    //Sequential
    public String generateSequentialIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException;

    //Thread Approach
    public String generateParallelIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException;

    public String mergeIndexesInSingleDirectory() throws CorruptIndexException, IOException, ParseException;

    //public String generateFileParallelIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException;

}
