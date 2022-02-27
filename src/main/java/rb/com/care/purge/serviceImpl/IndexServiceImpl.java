package rb.com.care.purge.serviceImpl;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import rb.com.care.purge.model.Config;
import rb.com.care.purge.service.ConfigService;
import rb.com.care.purge.service.IndexService;
import rb.com.care.purge.service.ParallelIndexService;
import rb.com.care.purge.service.SequentialIndexService;
import rb.com.care.purge.util.ClassUsingProperty;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class IndexServiceImpl implements IndexService {

    @Autowired
    ClassUsingProperty properties;

    @Autowired
    SequentialIndexService sequenceIndexService;

    @Autowired
    ParallelIndexService parallelIndexService;

    @Autowired
    private ConfigService configService;

    // Sequential Approach
    public String generateSequentialIndex() throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
        File dataDirectory = new File(properties.getDirectory());
        IndexWriter iw = getSerializedIndexWriterConfig(properties.getIndexDirectory());
        String response = sequenceIndexService.startSequentialIndexing(iw, dataDirectory);
        iw.commit();
        iw.close();
        return response;
    }

    // Concurrent Approach
    public String generateParallelIndex(String userId) throws IOException, ParseException {
        Config config = configService.findConfigByUserId(Long.parseLong(userId));
        File dataDirectory = new File(config.getDirPath());
        String response = parallelIndexService.startParallelIndexing(dataDirectory);
        return response;
    }

    // Concurrent indexing and searching
//    public String generateFileParallelIndex() throws IOException, ParseException {
//        File dataDirectory = new File(properties.getDirectory());
//        IndexWriter iw = getConcurrentIndexWriterConfig(properties.getIndexDirectory());
//        String response = parallelIndexService.startParallelIndexing(iw, dataDirectory);
//        iw.commit();
//        iw.close();
//        return response;
//    }

    public IndexWriter getSerializedIndexWriterConfig(String indexDirectory) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        Directory index = getIndexDirectory(indexDirectory);
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setCommitOnClose(true);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        iwc.setMergeScheduler(new SerialMergeScheduler());
        iwc.setRAMBufferSizeMB(1024);
        return new IndexWriter(index, iwc);
    }

    private IndexWriter getConcurrentIndexWriterConfig(String indexDirectory) throws IOException {
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
    public String mergeIndexesInSingleDirectory(String userId) throws CorruptIndexException, IOException, ParseException {

        Directory dir0 = getIndexDirectory("IndexDir0");
        Directory dir1 = getIndexDirectory("IndexDir1");
        Directory dir2 = getIndexDirectory("IndexDir2");
        Directory dir3 = getIndexDirectory("IndexDir3");
        Directory dir4 = getIndexDirectory("IndexDir4");
        Directory dir5 = getIndexDirectory("IndexDir5");
        Directory dir6 = getIndexDirectory("IndexDir6");
        Directory dir7 = getIndexDirectory("IndexDir7");
        Directory dir8 = getIndexDirectory("IndexDir8");
        Directory dir9 = getIndexDirectory("IndexDir9");

        Config config = configService.findConfigByUserId(Long.parseLong(userId));

        IndexWriter writer = getConcurrentIndexWriterConfig(config.getIndexDirPath());
        writer.addIndexes(dir0, dir1, dir2, dir3, dir4, dir5, dir6, dir7, dir8, dir9);
        writer.close();

        for(int i = 0; i < 10; i++){
            String filepath = "IndexDir"+i;
            File file = new File(filepath);
            deleteDirectory(file);
            Path path = Paths.get(filepath);
            Files.delete(path);
        }
        return "Success";
    }

    public static void deleteDirectory(File file)
    {
        // store all the paths of files and folders present
        for (File subfile : file.listFiles()) {
            // recursiley call function to empty subfolder
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            // delete files and empty subfolders
            subfile.delete();
        }
    }

}
