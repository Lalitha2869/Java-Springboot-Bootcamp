
package com.example.incident_management_api.controller;

import com.example.incident_management_api.dto.IncidentRequest;
import com.example.incident_management_api.dto.IncidentResponse;
import com.example.incident_management_api.exception.GlobalExceptionHandler;
import com.example.incident_management_api.exception.ResourceNotFoundException;
import com.example.incident_management_api.model.IncidentEnvironment;
import com.example.incident_management_api.model.IncidentPriority;
import com.example.incident_management_api.model.IncidentStatus;
import com.example.incident_management_api.service.IncidentService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IncidentController.class)
@Import(GlobalExceptionHandler.class)
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IncidentService incidentService;

    private IncidentResponse sampleResponse() {
        LocalDateTime now = LocalDateTime.now();

        return new IncidentResponse(
                1L,
                "Kafka Consumer Lag",
                "Consumer lag exceeded the threshold",
                IncidentPriority.HIGH,
                IncidentStatus.OPEN,
                "Kafka",
                IncidentEnvironment.PRODUCTION,
                null,
                now,
                now
        );
    }

    private IncidentRequest validRequest() {
        IncidentRequest request = new IncidentRequest();
        request.setTitle("Kafka Consumer Lag");
        request.setDescription("Consumer lag exceeded the threshold");
        request.setPriority(IncidentPriority.HIGH);
        request.setServiceName("Kafka");
        request.setEnvironment(IncidentEnvironment.PRODUCTION);
        return request;
    }

    // Test 1: Create incident returns 201 Created
    @Test
    void shouldCreateIncident() throws Exception {
        when(incidentService.createIncident(any(IncidentRequest.class)))
                .thenReturn(sampleResponse());

        mockMvc.perform(post("/api/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                validRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title")
                        .value("Kafka Consumer Lag"))
                .andExpect(jsonPath("$.status").value("OPEN"));

        verify(incidentService).createIncident(
                any(IncidentRequest.class));
    }

    // Test 2: Get incident returns 200 OK
    @Test
    void shouldGetIncidentById() throws Exception {
        when(incidentService.getIncidentById(1L))
                .thenReturn(sampleResponse());

        mockMvc.perform(get("/api/incidents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.priority").value("HIGH"))
                .andExpect(jsonPath("$.serviceName").value("Kafka"));
    }

    // Test 3: Missing incident returns 404
    @Test
    void shouldReturn404WhenIncidentDoesNotExist()
            throws Exception {

        when(incidentService.getIncidentById(999L))
                .thenThrow(new ResourceNotFoundException(
                        "Incident with id 999 not found"));

        mockMvc.perform(get("/api/incidents/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Incident with id 999 not found"));
    }

    // Test 4: Invalid request returns 400
    @Test
    void shouldRejectRequestWithMissingTitle() throws Exception {
        IncidentRequest request = validRequest();
        request.setTitle("");

        mockMvc.perform(post("/api/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Request validation failed"))
                .andExpect(jsonPath("$.errors.title").exists());

        verify(incidentService, never())
                .createIncident(any(IncidentRequest.class));
    }

    // Test 5: Delete incident returns 204
    @Test
    void shouldDeleteIncident() throws Exception {
        doNothing().when(incidentService).deleteIncident(1L);

        mockMvc.perform(delete("/api/incidents/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(incidentService).deleteIncident(1L);
    }

    // Test 6: Update incident status returns 200
    @Test
    void shouldUpdateIncidentStatus() throws Exception {
        when(incidentService.updateIncidentStatus(
                eq(1L), eq(IncidentStatus.IN_PROGRESS)))
                .thenReturn(sampleResponseWithStatus(
                        IncidentStatus.IN_PROGRESS));

        mockMvc.perform(patch("/api/incidents/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"status":"IN_PROGRESS"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status")
                        .value("IN_PROGRESS"));
    }

    private IncidentResponse sampleResponseWithStatus(
            IncidentStatus status) {

        LocalDateTime now = LocalDateTime.now();

        return new IncidentResponse(
                1L,
                "Kafka Consumer Lag",
                "Consumer lag exceeded the threshold",
                IncidentPriority.HIGH,
                status,
                "Kafka",
                IncidentEnvironment.PRODUCTION,
                null,
                now,
                now
        );
    }
}
