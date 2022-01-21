package rb.com.care.purge.serviceImpl;

import com.google.common.collect.Lists;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.context.annotation.Configuration;
import rb.com.care.purge.service.ParallelIndexService;
import rb.com.care.purge.util.IndexingExecutor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ParallelIndexServiceImpl extends ParallelIndexService {

    //Thread approach
    @Override
    public void startParallelIndexing(IndexWriter iw, File dataDirectory) throws IOException, ParseException {
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
