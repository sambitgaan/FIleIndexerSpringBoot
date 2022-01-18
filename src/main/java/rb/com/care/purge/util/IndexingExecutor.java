package rb.com.care.purge.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IndexingExecutor implements Runnable{

    public static AtomicInteger threadsInWritingBlock = new AtomicInteger();
    public static AtomicInteger pendingCommits = new AtomicInteger();
    public static int PENDING_COMMIT_THRESHOLD = 1000;

    private List<File> files;
    private Integer count;

    public IndexingExecutor(List<File> files, Integer count) {
        this.files = files;
        this.count = count;
    }

    @Override
    public void run()
    {
        IndexWriter iw = null;
        try {
            iw = getIndexWriter("IndexDir"+count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (File f : files) {
                generateIndexes(iw, f);
            }
            System.out.println(count);
        }
        catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }

    public void generateIndexes(IndexWriter iw, File f) throws IOException, ParseException {
        if (f.isDirectory()) {
            generateIndexesBasedOnDirectories(iw, f);
        } else {
            indexFileWithIndexWriter(iw, f);
        }
    }

    public void generateIndexesBasedOnDirectories(IndexWriter iw, File dataDirectory) throws IOException, ParseException {
        File[] files = dataDirectory.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                generateIndexes(iw, f);
            } else {
                indexFileWithIndexWriter(iw, f);
            }
        }
    }

    private static IndexWriter getIndexWriter(String indexDirectory) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        Directory index = getIndexDirectory(indexDirectory);
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setCommitOnClose(true);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        iwc.setMergeScheduler(new org.apache.lucene.index.SerialMergeScheduler());
        iwc.setRAMBufferSizeMB(1024);
        return new IndexWriter(index, iwc);
    }

    private static Directory getIndexDirectory(String dirPath) throws IOException {
        File idxDirectory = new File(dirPath);
        return FSDirectory.open(idxDirectory.toPath());
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
}
