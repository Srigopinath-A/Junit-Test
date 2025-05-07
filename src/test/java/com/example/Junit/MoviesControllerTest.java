package com.example.Junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

import static org.hamcrest.Matchers.notNullValue;
import static java.util.Optional.of;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.offset;
import static org.mockito.ArgumentMatchers.isA;
import static java.util.Optional.ofNullable;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.Junit.Controller.Controller;
import com.example.Junit.Model.Movies;
import com.example.Junit.Repo.ModelRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@RunWith(MockitoJUnitRunner.class)
public class MoviesControllerTest {

    private MockMvc mockmvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ModelRepo modelrepo;

    @InjectMocks
    private Controller controller;

    Movies Mov1 = new Movies(1L, "Petta", "Oru time pakkala", 4);
    Movies Mov2 = new Movies(2L, "Lubber panthu", "Nalla padam", 5);
    Movies Mov3 = new Movies(3L, "Leo", "Paravala", 4);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockmvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllRecords() throws Exception {
        List<Movies> movie = new ArrayList<>(Arrays.asList(Mov1, Mov2, Mov3));
        Mockito.when(modelrepo.findAll()).thenReturn(movie);

        mockmvc.perform(MockMvcRequestBuilders
                .get("/movies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Leo"));
    }
    
    @Test
    public void getById() throws Exception {
        
        Mockito.when(modelrepo.findById(Mov1.getMovieid())).thenReturn(of(Mov1));
       
        mockmvc.perform(MockMvcRequestBuilders
                .get("/movies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Petta"));
    }
    
    @Test
    public void createmovie() throws Exception {
    	Movies movie  =Movies.builder()
    			.movieid(4L)
    			.name("Pathu thala")
    			.about("Simbu come back")
    			.rating(5)
    			.build();
    	
    	Mockito.when(modelrepo.save(movie)).thenReturn(movie);
    	
    	String content = objectWriter.writeValueAsString(movie);
    	
    	MockHttpServletRequestBuilder mockreq = MockMvcRequestBuilders.post("/movies")
    			.contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.content(content);
    	
    	mockmvc.perform(mockreq)
    	.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pathu thala"));
    }
    
    @Test
    public void updatemovie() throws Exception {
    	Movies Updaterecord = Movies.builder()
    			.movieid(1L)
    			.name("Vikram")
    			.about("Good to watch")
    			.rating(5)
    			.build();
    	
    	Mockito.when(modelrepo.findById(Mov1.getMovieid())).thenReturn(ofNullable(Mov1));
    	Mockito.when(modelrepo.save(Updaterecord)).thenReturn(Updaterecord);
    	
    	String updatecontent = objectWriter.writeValueAsString(Updaterecord);
    	
    	MockHttpServletRequestBuilder mockreq = MockMvcRequestBuilders.post("/movies")
    			.contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.content(updatecontent);
    	
    	mockmvc.perform(mockreq)
    	.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vikram"));
    }
    
    @Test
    public void deletebyid() throws Exception{
    	Mockito.when(modelrepo.findById(Mov2.getMovieid())).thenReturn(of(Mov2));
    	
    	mockmvc.perform(MockMvcRequestBuilders
    			.delete("/movies/2")
    			.contentType(MediaType.APPLICATION_JSON))
    	        .andExpect(status().isOk());
    }
    
}

