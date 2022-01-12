package rb.com.care.purge.util;

import com.google.common.collect.Lists;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class IndexTypes {

    public static AtomicInteger threadsInWritingBlock = new AtomicInteger();
    public static AtomicInteger pendingCommits = new AtomicInteger();
    public static int PENDING_COMMIT_THRESHOLD = 1000;

    // Sequential Approach
    public void generateIndexesSequential(IndexWriter iw, File dataDirectory) throws IOException, ParseException {
        File[] files = dataDirectory.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                generateIndexesSequential(iw, f);
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
    public void generateIndexesConcurrent(IndexWriter iw, File dataDirectory) throws IOException, ParseException {
        File[] files = dataDirectory.listFiles();
        assert files != null;
        List<File> filesList = Arrays.asList(files);
        int chunkSize = (int) Math.ceil((double) files.length / 10);
        List<List<File>> checkedData = Lists.partition(filesList, chunkSize);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        for (int i = 0; i < checkedData.size(); i++) {
            executor.submit(new IndexingExecutor(checkedData.get(i), i));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}

    }
}
