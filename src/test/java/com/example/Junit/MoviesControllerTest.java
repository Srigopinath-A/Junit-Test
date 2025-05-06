package com.example.Junit;

import java.util.ResourceBundle.Control;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Junit.Controller.Controller;
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
	
	private Controller controller;
	
	
	
}
