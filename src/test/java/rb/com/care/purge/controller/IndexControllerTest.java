package rb.com.care.purge.controller;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rb.com.care.purge.service.DeleteService;
import rb.com.care.purge.service.IndexService;
import rb.com.care.purge.service.SearchService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IndexService indexService;

    @MockBean
    private SearchService searchService;

    @MockBean
    private DeleteService deleteService;

    @Test
    void startIndex() throws Exception {
        Mockito.when(indexService.generateSequentialIndex()).thenReturn("Success");
        mockMvc.perform(MockMvcRequestBuilders.get("/rb/startIndex"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void startIndexExceptionCase() throws Exception {
        Mockito.when(indexService.generateSequentialIndex()).thenReturn("Success");
        mockMvc.perform(MockMvcRequestBuilders.post("/rb/startIndex1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void searchIndex() throws Exception {
        Mockito.when(searchService.searchFies()).thenReturn("Success");
        mockMvc.perform(MockMvcRequestBuilders.get("/rb/searchIndex"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void searchIndexExceptionCase() throws Exception {
        Mockito.when(searchService.searchFies()).thenReturn("Success");
        mockMvc.perform(MockMvcRequestBuilders.post("/rb/searchIndex1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void deleteFiles() throws Exception {
        Mockito.when(deleteService.deleteFiles()).thenReturn("Success");
        mockMvc.perform(MockMvcRequestBuilders.get("/rb/deleteFiles"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}