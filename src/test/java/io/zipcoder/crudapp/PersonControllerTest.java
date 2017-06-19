package io.zipcoder.crudapp;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;



import java.util.Arrays;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class PersonControllerTest{
    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Before
    //before each test, initialize the mock builders using the personController and filters listed in CORS class.
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders
                .standaloneSetup(personController)
                .addFilters(new CORSFilter())
                .build();
    }


    //test get all people
    @Test
    public void testGetAllPeopleSuccess() throws Exception{
        List<Person> people = Arrays.asList(
                new Person(1, "Paul", 35),
                new Person(2, "Mike", 62));

        when(personService.getAll()).thenReturn(people);


        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is (1)))
                .andExpect(jsonPath("$[0].name", is ("Paul")))
                .andExpect(jsonPath("$[1].id", is (2)))
                .andExpect(jsonPath("$[1].name", is ("Mike")));

        verify(personService, times(1)).getAll();
        verifyNoMoreInteractions(personService);


    }



}