package rb.com.care.purge.controller;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rb.com.care.purge.model.IndexRequestDto;
import rb.com.care.purge.model.Response;
import rb.com.care.purge.model.Users;
import rb.com.care.purge.service.DeleteService;
import rb.com.care.purge.service.IndexService;
import rb.com.care.purge.service.SearchService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("rb")
@CrossOrigin
public class IndexController
{
    @Autowired
    IndexService indexService;

    @Autowired
    SearchService searchService;

    @Autowired
    DeleteService deleteService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/startIndex", method = RequestMethod.POST)
    public ResponseEntity<Response> startIndex(@Valid @RequestBody IndexRequestDto user)
    {
        Response response = new Response();
        try {
            String Response = indexService.generateParallelIndex(user.getUserId());
            response.setMessage(Response);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        response.setStatus(HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/startMerge", method = RequestMethod.POST)
    public ResponseEntity<Response> mergeIndexesInSingleDirectory(@Valid @RequestBody IndexRequestDto dto)
    {
        try {
            indexService.mergeIndexesInSingleDirectory(dto.getUserId());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Indexes generated in single folder");
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/startFileIndex", method = RequestMethod.POST)
    public ResponseEntity<String> startFileIndex(@Valid @RequestBody IndexRequestDto user)
    {
        try {
            indexService.generateParallelIndex(user.getUserId());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Index has been generated", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/searchIndex", method = RequestMethod.POST)
    public ResponseEntity<Response> searchIndex(@Valid @RequestBody IndexRequestDto user)
    {
        try {
            searchService.searchFies(user.getUserId());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Searched Successfully and File paths added.");
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteFiles", method = RequestMethod.POST)
    public ResponseEntity<Response> deleteFiles(@Valid @RequestBody IndexRequestDto user)
    {
        deleteService.deleteFiles(user.getUserId());
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setMessage("File Deleted Successfully");
        return ResponseEntity.ok(response);
    }
}
