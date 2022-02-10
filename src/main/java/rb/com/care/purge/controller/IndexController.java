package rb.com.care.purge.controller;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rb.com.care.purge.model.Response;
import rb.com.care.purge.service.DeleteService;
import rb.com.care.purge.service.IndexService;
import rb.com.care.purge.service.SearchService;

import java.io.IOException;

@RestController
@RequestMapping("rb")
public class IndexController
{
    @Autowired
    IndexService indexService;

    @Autowired
    SearchService searchService;

    @Autowired
    DeleteService deleteService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/startIndex", method = RequestMethod.GET)
    public ResponseEntity<Object> startIndex()
    {
        try {
            indexService.generateParallelIndex();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Index has been generated");
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/startMerge", method = RequestMethod.GET)
    public ResponseEntity<Object> mergeIndexesInSingleDirectory()
    {
        try {
            indexService.mergeIndexesInSingleDirectory();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Indexes generated in single folder");
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/startFileIndex", method = RequestMethod.GET)
    public ResponseEntity<String> startFileIndex()
    {
        try {
            indexService.generateParallelIndex();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Index has been generated", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/searchIndex", method = RequestMethod.GET)
    public ResponseEntity<Object> searchIndex()
    {
        try {
            searchService.searchFies();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Searched Successfully and File paths added.");
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteFiles", method = RequestMethod.GET)
    public ResponseEntity<Object> deleteFiles()
    {
        deleteService.deleteFiles();
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setMessage("File Deleted Successfully");
        return ResponseEntity.ok(response);
    }
}
