package rb.com.care.purge.serviceImpl;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.ConcurrentMergeScheduler;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import rb.com.care.purge.service.IndexService;
import rb.com.care.purge.util.ClassUsingProperty;
import rb.com.care.purge.util.IndexTypes;

import java.io.*;

@Configuration
public class IndexServiceImpl extends IndexTypes implements IndexService {

    @Autowired
    ClassUsingProperty properties;

    // Sequential Approach
    public String createIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
        File dataDirectory = new File(properties.getDirectory());
        IndexWriter iw = getIndexWriter(properties.getIndexDirectory());
        IndexTypes indexTypes = new IndexServiceImpl();
        indexTypes.generateIndexesSequential(iw, dataDirectory);
        iw.commit();
        iw.close();
        return "Indexes Generated Sequentially";
    }

    // Concurrent Approach
    public String createIndexes() throws IOException, ParseException {
        File dataDirectory = new File(properties.getDirectory());
        IndexWriter iw = getIndexWriter(properties.getIndexDirectory());
        IndexTypes indexTypes = new IndexServiceImpl();
        indexTypes.generateIndexesConcurrent(iw, dataDirectory);
        iw.commit();
        iw.close();
        return "Indexes Generated Concurrently";
    }

    private IndexWriter getIndexWriter(String indexDirectory) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        Directory index = getIndexDirectory(indexDirectory);
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setCommitOnClose(true);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        iwc.setMergeScheduler(new ConcurrentMergeScheduler());
        iwc.setRAMBufferSizeMB(1024);
        return new IndexWriter(index, iwc);
    }

    private static Directory getIndexDirectory(String dirPath) throws IOException {
        File idxDirectory = new File(dirPath);
        return FSDirectory.open(idxDirectory.toPath());
    }


    // Merging generated directories into common directory
    public void createCommonIndexes() throws CorruptIndexException, IOException, ParseException {

        Directory indexE = getIndexDirectory("IndexDir0");
        Directory indexA = getIndexDirectory("IndexDir1");
        Directory indexB = getIndexDirectory("IndexDir2");
        Directory indexC = getIndexDirectory("IndexDir3");
        Directory indexD = getIndexDirectory("IndexDir4");
        Directory indexF = getIndexDirectory("IndexDir5");
        Directory indexG = getIndexDirectory("IndexDir6");
        Directory indexH = getIndexDirectory("IndexDir7");
        Directory indexI = getIndexDirectory("IndexDir8");
        Directory indexJ = getIndexDirectory("IndexDir9");

        IndexWriter writer = getIndexWriter(properties.getMergedDir());
        writer.addIndexes(indexA, indexB, indexC, indexD, indexE, indexF, indexG, indexH, indexI, indexJ);
        writer.close();
    }

}
