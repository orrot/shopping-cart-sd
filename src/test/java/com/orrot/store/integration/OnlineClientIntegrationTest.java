package com.orrot.store.integration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import com.orrot.store.AbstractContainerBaseTest;
import com.orrot.store.integration.data.OnlineClientExamples;
import com.orrot.store.onlineuser.adapter.input.json.OnlineClientView;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DisplayName("During Online Client CRUD Flows")
public class OnlineClientIntegrationTest extends AbstractContainerBaseTest {

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("When creating an online client")
    @Order(1)
    class WhenCreatingOnlineClient {

        @Test
        @DisplayName("Should return created status and ID of the created online client")
        void shouldReturnCreatedStatusAndGeneratedIdShouldBeReturned() throws Exception {
            // When / Then
            var onlineClientToCreate = OnlineClientExamples.dummyToWrite();
            var result = mockMvc.perform(post("/v1/online-clients")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(onlineClientToCreate)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNumber())
                    .andReturn();

            // Assert
            Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

            mockMvc.perform(get("/v1/online-clients/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            gson.toJson(OnlineClientExamples.dummy(id, onlineClientToCreate))));

        }
    }

    @Nested
    @DisplayName("When updating an online client")
    @Order(2)
    class WhenUpdatingOnlineClient {

        @Test
        @DisplayName("Should return no content status and body should be empty")
        void shouldReturnNoContentStatusAndBodyEmpty() throws Exception {

            // When
            var createdOnlineClient = OnlineClientExamples.dummyToWrite();
            var result = mockMvc.perform(post("/v1/online-clients")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(createdOnlineClient)))
                    .andExpect(status().isCreated())
                    .andReturn();

            Integer onlineClientIdToUpdate = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

            // Then
            var onlineClientToUpdate = OnlineClientExamples.update(createdOnlineClient);
            mockMvc.perform(put("/v1/online-clients/{id}", onlineClientIdToUpdate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(onlineClientToUpdate)))
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$").doesNotExist());

            // Assert
            mockMvc.perform(get("/v1/online-clients/{id}", onlineClientIdToUpdate)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            gson.toJson(OnlineClientExamples.dummy(onlineClientIdToUpdate, onlineClientToUpdate))));
        }

    }

    @Nested
    @DisplayName("When listing online clients")
    class WhenListingOnlineClients {

        @Test
        @DisplayName("Should return OK status and a page of online clients")
        @Sql(value = "/sql/onlineclient/default_list.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(value = "/sql/onlineclient/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void shouldReturnOkAndThePageOfOnlineClients() throws Exception {

            // When / Then
            var result = mockMvc.perform(get("/v1/online-clients")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(2))
                    .andReturn();

            // Assert
            var contentAsString = JsonPath.read(result.getResponse().getContentAsString(), "$.content").toString();
            var listType = new TypeToken<ArrayList<OnlineClientView>>(){}.getType();
            List<OnlineClientView> onlineClients = new Gson().fromJson(contentAsString, listType);

            assertThat(onlineClients)
                    .as("All the fields for all the onlineProducts should be non-null")
                    .flatExtracting(OnlineClientView::id, OnlineClientView::name, OnlineClientView::address)
                    .doesNotContainNull();
        }

    }
}