package rb.com.care.purge.service;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.File;
import java.io.IOException;

public abstract class ParallelIndexService {

    public abstract String startParallelIndexing(IndexWriter iw, File dataDirectory) throws IOException, ParseException;

}
