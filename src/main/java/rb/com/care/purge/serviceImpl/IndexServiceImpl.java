package rb.com.care.purge.serviceImpl;


import com.google.common.collect.Lists;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.ConcurrentMergeScheduler;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rb.com.care.purge.service.IndexService;
import rb.com.care.purge.util.Indexing;
import rb.com.care.purge.util.IndexingExecutor;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class IndexServiceImpl implements IndexService {

    public static AtomicInteger threadsInWritingBlock = new AtomicInteger();
    public static AtomicInteger pendingCommits = new AtomicInteger();
    public static int PENDING_COMMIT_THRESHOLD = 1000;

    @Value( "${file.data.index}" )
    private String indexDirectory;

    @Value( "${file.data.directory}" )
    private String dataSrcDirectory;

    @Value( "${file.data.list}" )
    private String fileList;

    @Value( "${file.data.search}" )
    private String searchList;


    public String createIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
        File dataDirectory = new File("Test");
        IndexWriter iw = getIndexWriter("IndexDir");
        indexDirectory(iw, dataDirectory);
        iw.commit();
        iw.close();
        return "Indexes Generated";
    }

    //For Thread Approch
    public String createIndexes() throws IOException, ParseException {
        File dataDirectory = new File("Test");
        IndexWriter iw = getIndexWriter("IndexDir");
        threadIndexDirectory(iw, dataDirectory);
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
        IndexWriter iw = new IndexWriter(index, iwc);
        return iw;
    }

    private static Directory getIndexDirectory(String dirPath) throws IOException {
        File idxDirectory = new File(dirPath);
        Directory index = FSDirectory.open(idxDirectory.toPath());
        return index;
    }

    private void indexDirectory(IndexWriter iw, File dataDirectory) throws IOException, ParseException {
        File[] files = dataDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(iw, f);
            } else {
                indexFileWithIndexWriter(iw, f);
            }
        }
    }

    private static void indexFileWithIndexWriter(IndexWriter iw, File f) throws IOException, ParseException {
        if (f.isHidden() || f.isDirectory() || !f.canRead() || !f.exists()) {
            return;
        }
        Document doc = new Document();
        addToIndex(iw, f, doc);
    }

    private static void addToIndex(IndexWriter iw, File f, Document doc) throws IOException, ParseException {
        doc.add(new LongPoint("id", f.hashCode()));
        doc.add(new Field("filename", f.getName(), TextField.TYPE_STORED));
        doc.add(new Field("filepath", f.getCanonicalPath(), TextField.TYPE_STORED));
        iw.addDocument(doc);
        threadsInWritingBlock.incrementAndGet();
        if (threadsInWritingBlock.decrementAndGet() == 0 || pendingCommits.incrementAndGet() > PENDING_COMMIT_THRESHOLD) {
            pendingCommits.set(0);
            iw.commit();
        }
    }

    //Thread approach
    private static void threadIndexDirectory(IndexWriter iw, File dataDirectory) throws IOException, ParseException {
        File[] files = dataDirectory.listFiles();
        List<File> listFIles = Arrays.asList(files);
        int chunkSize = (int) Math.ceil((double) files.length / 10);
        List<List<File>> checkedData = Lists.partition(listFIles, chunkSize); //List of files 1425/10 --> 142

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        // executor
//		for (int i = 0; i < checkedData.size(); i++) {
//			executor.submit(new Indexing(iw, checkedData.get(i), i));
//		}

        // thread approch
        for (int i = 0; i < checkedData.size(); i++) {
            executor.submit(new IndexingExecutor(checkedData.get(i), i));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}

    }

    public String createCommonIndexes() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {

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

        IndexWriter writer = getIndexWriter("mergedDir");
        writer.addIndexes(new Directory[]{indexA, indexB, indexC, indexD, indexE, indexF, indexG, indexH, indexI, indexJ});
        writer.close();
        return "Common Indexes Data Generated";
    }

}
