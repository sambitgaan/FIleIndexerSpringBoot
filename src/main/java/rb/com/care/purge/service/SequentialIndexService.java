package rb.com.care.purge.service;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.File;
import java.io.IOException;

public abstract class SequentialIndexService {

    public abstract String startSequentialIndexing(IndexWriter iw, File dataDirectory) throws IOException, ParseException;

}
