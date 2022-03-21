package com.example.TeamsApi.controller;

import com.example.TeamsApi.request.CreateTaskRequest;
import com.example.TeamsApi.request.CreateUserRequest;
import com.example.TeamsApi.request.UpdateTaskRequest;
import com.example.TeamsApi.response.TaskResponse;
import com.example.TeamsApi.response.UserResponse;
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


    private static final String NAME = "magda";
    private static final String LASTNAME = "szuszkiewicz";
    private static final String EMAIL = "szuszu@gmail.com";

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
    void shouldNotGetAllTasksWithBadRequest() throws Exception {
        mockMvc.perform(post("/api/task")
                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest.
                                builder()
                                .taskDescription(TASKDESCRIPTION)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
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
    void shouldFindTaskByTitle() throws Exception {
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

    @Test
    void shouldNotFindTaskByTitle() throws Exception {
        mockMvc.perform(post("/api/task")
                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest.builder()
                                .title(TITLE)
                                .taskDescription(TASKDESCRIPTION)
                                .date(DATE)
                                .status(STATUS)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/task/findByTitle/" + "NOT EXIST")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
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
    void shouldFindTaskByStatus() throws Exception {

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
    void shouldNotFindTaskByStatus() throws Exception {
        mockMvc.perform(post("/api/task")
                        .content(objectMapper.writeValueAsBytes(CreateTaskRequest.builder()
                                .title(TITLE)
                                .taskDescription(TASKDESCRIPTION)
                                .date(DATE)
                                .status(STATUS)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/task/findByStatus/" + "NOT EXIST")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
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
    void shouldNotDeleteTaskBecauseNotExist() throws Exception {
        mockMvc.perform(delete("/api/task/delete/1")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void assignTask() throws Exception {
        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsBytes(CreateUserRequest
                                .builder()
                                .name(NAME)
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .build())).contentType("application/json"))
                .andExpect(status().isCreated());

        var mvcResultUser = mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andReturn();


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

        var mvcResultTask = mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andReturn();



    }


    @Test
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsBytes(CreateUserRequest
                                .builder()
                                .name(NAME)
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .build())).contentType("application/json"))
                .andExpect(status().isCreated());

        var mvcResult = mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andReturn();

        var users = Arrays.asList(objectMapper
                .readValue(mvcResult
                        .getResponse()
                        .getContentAsString(), UserResponse[].class));

        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(NAME, users.get(0).getName());
        Assertions.assertEquals(LASTNAME, users.get(0).getLastName());
        Assertions.assertEquals(EMAIL, users.get(0).getEmail());

    }

    @Test
    void shouldNotGetAllUsersWithBadRequest() throws Exception {
        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsBytes(CreateUserRequest.
                                builder()
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFindByLastName() throws Exception{
        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsBytes(CreateUserRequest
                                .builder()
                                .name(NAME)
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .build())).contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/api/user/findByLastName/" + LASTNAME)
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        var users = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), UserResponse.class);


        Assertions.assertEquals(NAME, users.getName());
        Assertions.assertEquals(LASTNAME, users.getLastName());
        Assertions.assertEquals(EMAIL, users.getEmail());
    }

    @Test
    void shouldNotFindUserByLastName() throws Exception {
        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsBytes(CreateUserRequest.builder()
                                .name(NAME)
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/task/findByLastName/" + "NOT EXIST")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindUserByEmail() throws Exception {
        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsBytes(CreateUserRequest
                                .builder()
                                .name(NAME)
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .build())).contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/api/user/findByEmail/" + EMAIL)
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        var users = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), UserResponse.class);


        Assertions.assertEquals(NAME, users.getName());
        Assertions.assertEquals(LASTNAME, users.getLastName());
        Assertions.assertEquals(EMAIL, users.getEmail());
    }

    @Test
    void shouldNotFindUserByEmail() throws Exception {
        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsBytes(CreateUserRequest.builder()
                                .name(NAME)
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/task/findByEmail/" + "NOT EXIST")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsBytes(CreateUserRequest
                                .builder()
                                .name(NAME)
                                .lastName(LASTNAME)
                                .email(EMAIL)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());


        var mvcResult = mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andReturn();

        var users = Arrays.asList(objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), UserResponse[].class));


        mockMvc.perform(delete("/api/user/deleteUser/" + users.get(0).getUserId())
                        .contentType("application/json"))
                .andExpect(status().isOk());


        mvcResult = mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andReturn();

        var result = Arrays.asList(objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), UserResponse[].class));

        Assertions.assertEquals(0, result.size());
    }

    @Test
    void shouldNotDeleteUserBecauseNotExist() throws Exception {
        mockMvc.perform(delete("/api/user/delete/1")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}