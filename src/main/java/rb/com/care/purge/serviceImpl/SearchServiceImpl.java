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
import org.springframework.stereotype.Service;
import rb.com.care.purge.service.SearchService;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public String searchFies() throws IOException, ParseException{
        String st;
        BufferedReader br1 = getFIleBufferedReader();
        BufferedWriter bw = getFileBufferedWriter();
        while ((st = br1.readLine()) != null) {
            searchIndex(st, bw);
        }
        br1.close();
        bw.close();
        return null;
    }

    private static BufferedWriter getFileBufferedWriter() throws IOException {
        File filePath = new File("target\\classes\\Search\\SearchFiles.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        return bw;
    }

    public static BufferedReader getFIleBufferedReader() throws IOException {
        File file = new File("target\\classes\\Input\\List.txt");
        return new BufferedReader(new FileReader(file));
    }

    private static Directory getIndexDirectory(String dirPath) throws IOException {
        File idxDirectory = new File(dirPath);
        Directory index = FSDirectory.open(idxDirectory.toPath());
        return index;
    }

    private static void searchIndex(String searchString, BufferedWriter bw) throws IOException, ParseException {
        Directory directory = getIndexDirectory("IndexDir");
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher searcher = getSearcher(ireader);
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser queryParser = new QueryParser("filename", analyzer);
        queryParser.setAllowLeadingWildcard(true);
        queryParser.setSplitOnWhitespace(true);
        Query query = queryParser.parse("*" + searchString + "*");
        ScoreDoc[] hits = searcher.search(query, 1000).scoreDocs;
        printPath(searcher, hits, bw);
        ireader.close();
    }

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
