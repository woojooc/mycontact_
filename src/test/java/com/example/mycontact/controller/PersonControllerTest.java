package com.example.mycontact.controller;

import com.example.mycontact.controller.dto.PersonDto;
import com.example.mycontact.domain.Person;
import com.example.mycontact.domain.dto.Birthday;
import com.example.mycontact.exception.handler.GlobalExceptionHandler;
import com.example.mycontact.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@SpringBootTest
class PersonControllerTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;
    /*
    @Autowired
    private PersonController personController;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    */

    private MockMvc mockMvc;

    // ?????????????????? ????????? ?????? ??????
    @BeforeEach
    void beforeEach() {
        /*
        mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .setMessageConverters(messageConverter)
                .setControllerAdvice(globalExceptionHandler)
                .alwaysDo(print())
                .build();
*/
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .build();
    }
    @Test
    void getAll() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person")
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(3))
                .andExpect(jsonPath("$.totalElements").value(6))
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.content.[0].name").value("dennis"))
                .andExpect(jsonPath("$.content.[1].name").value("sophia"));
    }
    /*
    @Test
    void getAll() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(6)))
                .andExpect(jsonPath("$.[0].name").value("martin"))
                .andExpect(jsonPath("$.[1].name").value("david"))
                .andExpect(jsonPath("$.[2].name").value("dennis"))
                .andExpect(jsonPath("$.[3].name").value("sophia"))
                .andExpect(jsonPath("$.[4].name").value("benny"))
                .andExpect(jsonPath("$.[5].name").value("tony"));
                //.andExpect(jsonPath("$.[6].name").value("andrew"));
    }

     */

    @Test
    void getPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"))
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
                .andExpect(jsonPath("$.birthday").value("1991-08-15"))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber())
                .andExpect(jsonPath("$.birthdayToday").isBoolean());


        //jsonPath("$.name").value("mertin");
        //assertThar(result.getName()).isEqualTo("martin");
    }

    @Test
    void postPerson() throws Exception {
        PersonDto dto = PersonDto
                .of("martin", "programming", "??????", LocalDate.now(), "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")//RequestParam :  ?name=martin2&age=20&bloodType=A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                //.andDo(print())
                .andExpect(status().isCreated());

        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("??????"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getJob()).isEqualTo("programmer"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222"));
    }

    @Test
    void postPersonIfNameIsNull() throws Exception {
        PersonDto dto = new PersonDto();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("????????? ??????????????????"));
    }

    @Test
    void postPersonIfNameIsEmptyString() throws Exception {
        PersonDto dto = new PersonDto();
        dto.setName("");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("????????? ??????????????????"));
    }

    @Test
    void postPersonIfNameIsBlankString() throws Exception {
        PersonDto dto = new PersonDto();
        dto.setName(" ");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("????????? ??????????????????"));
    }


    @Test
    void modifyPerson() throws Exception {
        PersonDto dto = PersonDto
                .of("martin", "programming", "??????", LocalDate.now(), "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
//                .content("{\n"
//                        + " \"name\": \"martin\""
//                        + "}"))
                //.andDo(print())
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("??????"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getJob()).isEqualTo("programmer"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
        );
    }

    @Test
    void modifyPersonIfNameDifferent() throws Exception {
        PersonDto dto = PersonDto
                .of("james", "programming", "??????", LocalDate.now(), "programmer", "010-1111-2222");

        //?????? ???api????????? exce?????????????????? ?????? ???????????? ?????????????????????.
        //assertThat(NestedServletException.class,() ->

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("?????? ????????? ???????????? ????????????."));
    }

    @Test
    void modifyPersonIfPersonNotFound() throws Exception {
        PersonDto dto = PersonDto
                .of("james", "programming", "??????", LocalDate.now(), "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Person Entity??? ???????????? ????????????."));
    }

    @Test
    void modifyName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                        .param("name", "martinModified"))
                //.andDo(print())
                .andExpect(status().isOk());

        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");
    }

    @Test
        //@Disabled
    void deletePerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                //.andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string("true"));
        // ????????? ??????????????? ?????????????????? ????????? ??????
        // void -> booleadn

        //log.info("people deleted : {}", personRepository.findPeopleDeleted());

        //?????? 3 ??????????????? ?????? ??????
        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    /*
    @Test
    void checkJsonString() throws JsonProcessingException {
        PersonDto dto = new PersonDto();
        dto.setName("martinc");
        dto.setBirthday(LocalDate.now());

        System.out.println(">>> " + toJsonString(dto));
    }
*/
    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}