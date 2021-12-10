package rb.com.care.purge.controller;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "/startIndex", method = RequestMethod.GET)
    ResponseEntity<String> startIndex()
    {
        try {
            indexService.createIndex();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Index has been generated", HttpStatus.OK);
    }

    @RequestMapping(value = "/searchIndex", method = RequestMethod.GET)
    ResponseEntity<String> searchIndex()
    {
        try {
            searchService.searchFies();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteFiles", method = RequestMethod.GET)
    ResponseEntity<String> deleteFiles()
    {
        deleteService.deleteFiles();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
