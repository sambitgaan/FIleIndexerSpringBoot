package rb.com.care.purge.service;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.LockObtainFailedException;

import java.io.IOException;

public interface IndexService {

    public String createIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException;

    //Thread Approach
    public String createIndexes() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException;

}
