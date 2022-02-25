package rb.com.care.purge.serviceImpl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rb.com.care.purge.model.Config;
import rb.com.care.purge.service.ConfigService;
import rb.com.care.purge.service.SearchService;
import rb.com.care.purge.service.UsersService;
import rb.com.care.purge.util.ClassUsingProperty;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

@Service
@Configurable
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ConfigService configService;

    //Get reading and writing files
    @Override
    public String searchFies(String userId) throws IOException, ParseException{
        String st;
        Config config = configService.findConfigByUserId(Long.parseLong(userId));
        BufferedReader br1 = getFIleBufferedReader(config.getFilesLogPath());
        BufferedWriter bw = getFileBufferedWriter(config.getSearchedFilesPathlog());
        while ((st = br1.readLine()) != null) {
            searchIndex(st, bw, config.getIndexDirPath());
        }
        br1.close();
        bw.close();
        return "Success";
    }

    private BufferedWriter getFileBufferedWriter(String searchPath) throws IOException {
        File filePath = new File(searchPath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        return bw;
    }

    public BufferedReader getFIleBufferedReader(String path) throws IOException {
        File file = new File(path);
        return new BufferedReader(new FileReader(file));
    }

    private Directory getIndexDirectory(String dirPath) throws IOException {
        File idxDirectory = new File(dirPath);
        Directory index = FSDirectory.open(idxDirectory.toPath());
        return index;
    }

    // Search as per provided file data
    private void searchIndex(String searchString, BufferedWriter bw, String indexPath) throws IOException, ParseException {
        Directory directory = getIndexDirectory(indexPath);
        DirectoryReader iReader = DirectoryReader.open(directory);
        IndexSearcher searcher = getSearcher(iReader);
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser queryParser = new QueryParser("filename", analyzer);
        queryParser.setAllowLeadingWildcard(true);
        queryParser.setSplitOnWhitespace(true);
        Query query = queryParser.parse("*" + searchString +"*");
        ScoreDoc[] hits = searcher.search(query, 1000).scoreDocs;
        printPath(searcher, hits, bw);
        iReader.close();
    }

    // Written Identified files
    private static void printPath(IndexSearcher searcher, ScoreDoc[] hits, BufferedWriter bw) throws IOException {
        for(int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            String filePath = d.get("filepath");
            bw.write(filePath + "\n");
        }
    }

    private static IndexSearcher getSearcher(DirectoryReader ireader) throws IOException {
        IndexSearcher searcher = new IndexSearcher(ireader);
        return searcher;
    }
}
