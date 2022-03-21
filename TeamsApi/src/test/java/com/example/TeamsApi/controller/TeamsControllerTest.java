package com.example.TeamsApi.controller;

import com.example.TeamsApi.request.CreateTaskRequest;
import com.example.TeamsApi.request.UpdateTaskRequest;
import com.example.TeamsApi.response.TaskResponse;
import com.example.TeamsApi.respository.TaskRepository;
import com.example.TeamsApi.respository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeamsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    TeamsControllerTest() throws ParseException {
    }

    @BeforeEach
    void cleanUp() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    ObjectMapper objectMapper = new ObjectMapper();

    private final static String TITLE = "coffee";
    private final static String TASKDESCRIPTION = "make coffee";
    private final static String sDate = "31/12/1998";
    private final Date DATE = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
    private final static String STATUS = "waiting";

    @Test
    void shouldGetAllTasks() throws Exception {
        mockMvc.perform(post("/api/task")
                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest
                                .builder()
                                .title(TITLE)
                                .taskDescription(TASKDESCRIPTION)
                                .date(DATE)
                                .status(STATUS)
                                .build())).contentType("application/json"))
                .andExpect(status().isCreated());

        var mvcResult = mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andReturn();

        var tasks = Arrays.asList(objectMapper
                .readValue(mvcResult
                        .getResponse()
                        .getContentAsString(), TaskResponse[].class));

        Assertions.assertEquals(1, tasks.size());
        Assertions.assertEquals(TITLE, tasks.get(0).getTitle());
        Assertions.assertEquals(TASKDESCRIPTION, tasks.get(0).getTaskDescription());
        Assertions.assertEquals(DATE, tasks.get(0).getDate());
        Assertions.assertEquals(STATUS, tasks.get(0).getStatus());
    }


    @Test
    void shouldAddTask() throws Exception {

    }

    @Test
    void shouldUpdateTask() throws Exception {
        mockMvc.perform(post("/api/task")
                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest.builder()
                                .title(TITLE)
                                .taskDescription(TASKDESCRIPTION)
                                .date(DATE)
                                .status(STATUS)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        final String changeTitle = "CHANGE";
        final String changeTaskDescription = "CHANGE TASK DESCRIPTION";
        final String sDate1 = "01/01/2001";
        final Date changeDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        final String changeStatus = "CHANGE STATUS";

        MvcResult mvcResult = mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andReturn();

        var tasks = Arrays.asList(objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), TaskResponse[].class));

        mockMvc.perform(put("/api/task/coffee")
                        .content(objectMapper.writeValueAsBytes(UpdateTaskRequest.builder()
                                .title(changeTitle)
                                .taskDescription(changeTaskDescription)
                                .date(changeDate)
                                .status(changeStatus)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        mvcResult = mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andReturn();

        var result = Arrays.asList(objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), TaskResponse[].class));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(changeTitle, result.get(0).getTitle());
        Assertions.assertEquals(changeTaskDescription, result.get(0).getTaskDescription());
        Assertions.assertEquals(changeDate, result.get(0).getDate());
        Assertions.assertEquals(changeStatus, result.get(0).getStatus());

    }

    @Test
    void shouldGetNotFoundExceptionWhenUpdateTask() throws Exception {
        String wrongTitle = "cof";
        mockMvc.perform(put("/api/task/" + wrongTitle)
                        .content(objectMapper.writeValueAsBytes(UpdateTaskRequest.builder()
                                .title(TITLE)
                                .taskDescription(TASKDESCRIPTION)
                                .date(DATE)
                                .status(STATUS)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void taskShouldFindByTitle() throws Exception {
        mockMvc.perform(post("/api/task")
                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest.builder()
                                .title(TITLE)
                                .taskDescription(TASKDESCRIPTION)
                                .date(DATE)
                                .status(STATUS)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/api/task/findByTitle/" + TITLE)
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        var task = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), TaskResponse.class);

        Assertions.assertEquals(TITLE, task.getTitle());
        Assertions.assertEquals(TASKDESCRIPTION, task.getTaskDescription());
        Assertions.assertEquals(DATE, task.getDate());
        Assertions.assertEquals(STATUS, task.getStatus());

    }

//    @Test
//    void taskShouldFindByDate() throws Exception {
//        mockMvc.perform(post("/api/task")
//                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest.builder()
//                                .title(TITLE)
//                                .taskDescription(TASKDESCRIPTION)
//                                .date(DATE)
//                                .status(STATUS)
//                                .build()))
//                        .contentType("application/json"))
//                .andExpect(status().isCreated());
//
//        MvcResult mvcResult = mockMvc.perform(get("/api/task/findByDate/" + DATE)
//                        .contentType("application/json"))
//                .andExpect(status().isOk()).andReturn();
//
//        var task = objectMapper.readValue(mvcResult
//                .getResponse()
//                .getContentAsString(), TaskResponse.class);
//
//        Assertions.assertEquals(TITLE, task.getTitle());
//        Assertions.assertEquals(TASKDESCRIPTION, task.getTaskDescription());
//        Assertions.assertEquals(DATE, task.getDate());
//        Assertions.assertEquals(STATUS, task.getStatus());
//    }

    @Test
    void taskShouldFindByStatus() throws Exception {

        mockMvc.perform(post("/api/task")
                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest.builder()
                                .title(TITLE)
                                .taskDescription(TASKDESCRIPTION)
                                .date(DATE)
                                .status(STATUS)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/api/task/findByStatus/" + STATUS)
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        var task = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), TaskResponse.class);

        Assertions.assertEquals(TITLE, task.getTitle());
        Assertions.assertEquals(TASKDESCRIPTION, task.getTaskDescription());
        Assertions.assertEquals(DATE, task.getDate());
        Assertions.assertEquals(STATUS, task.getStatus());
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(post("/api/task")
                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest
                                .builder()
                                .title(TITLE)
                                .taskDescription(TASKDESCRIPTION)
                                .date(DATE)
                                .status(STATUS)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());


        var mvcResult = mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andReturn();

        var task = Arrays.asList(objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), TaskResponse[].class));


        mockMvc.perform(delete("/api/task/deleteTask/" + task.get(0).getTaskId())
                        .contentType("application/json"))
                .andExpect(status().isOk());


        mvcResult = mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andReturn();

        var result = Arrays.asList(objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), TaskResponse[].class));

        Assertions.assertEquals(0, result.size());
    }

    @Test
    void assignTask() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void addUser() {
    }

    @Test
    void getByLastName() {
    }

    @Test
    void getByEmail() {
    }

    @Test
    void deleteUser() {
    }
}