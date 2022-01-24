package rb.com.care.purge.serviceImpl;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.context.annotation.Configuration;
import rb.com.care.purge.service.IndexService;
import rb.com.care.purge.service.SequentialIndexService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class SequentialIndexServiceImpl extends SequentialIndexService {

    public static AtomicInteger threadsInWritingBlock = new AtomicInteger();
    public static AtomicInteger pendingCommits = new AtomicInteger();
    public static int PENDING_COMMIT_THRESHOLD = 1000;

    // Sequential Approach
    @Override
    public String startSequentialIndexing(IndexWriter iw, File dataDirectory) throws IOException, ParseException {
        File[] files = dataDirectory.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                startSequentialIndexing(iw, f);
            } else {
                indexFileWithIndexWriter(iw, f);
            }
        }
        return "Success";
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
