
package com.example.incident_management_api.service;

import com.example.incident_management_api.dto.IncidentAssignmentRequest;
import com.example.incident_management_api.dto.IncidentRequest;
import com.example.incident_management_api.dto.IncidentResponse;
import com.example.incident_management_api.exception.ResourceNotFoundException;
import com.example.incident_management_api.model.Incident;
import com.example.incident_management_api.model.IncidentEnvironment;
import com.example.incident_management_api.model.IncidentPriority;
import com.example.incident_management_api.model.IncidentStatus;
import com.example.incident_management_api.repository.IncidentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentService incidentService;

    private Incident incident;

    @BeforeEach
    void setUp() {
        incident = new Incident();
        incident.setTitle("Kafka Consumer Lag");
        incident.setDescription("Consumer lag exceeded threshold");
        incident.setPriority(IncidentPriority.HIGH);
        incident.setStatus(IncidentStatus.OPEN);
        incident.setServiceName("Kafka");
        incident.setEnvironment(IncidentEnvironment.PRODUCTION);
    }

    private IncidentRequest createRequest() {
        IncidentRequest request = new IncidentRequest();
        request.setTitle("Database Connection Failure");
        request.setDescription("Cannot connect to the database");
        request.setPriority(IncidentPriority.CRITICAL);
        request.setServiceName("MySQL");
        request.setEnvironment(IncidentEnvironment.PRODUCTION);
        return request;
    }

    // Test 1: Create incident
    @Test
    void shouldCreateIncident() {
        IncidentRequest request = createRequest();

        when(incidentRepository.save(any(Incident.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        IncidentResponse response =
                incidentService.createIncident(request);

        assertEquals("Database Connection Failure", response.getTitle());
        assertEquals(IncidentPriority.CRITICAL, response.getPriority());
        assertEquals(IncidentStatus.OPEN, response.getStatus());

        verify(incidentRepository).save(any(Incident.class));
    }

    // Test 2: Get incidents with pagination and filters
    @Test
    void shouldReturnPagedIncidents() {
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Incident> page =
                new PageImpl<>(List.of(incident), pageable, 1);

        when(incidentRepository.findByOptionalFilters(
                IncidentStatus.OPEN, IncidentPriority.HIGH, pageable))
                .thenReturn(page);

        Page<IncidentResponse> response =
                incidentService.getAllIncidents(
                        IncidentStatus.OPEN,
                        IncidentPriority.HIGH,
                        pageable);

        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals("Kafka Consumer Lag",
                response.getContent().get(0).getTitle());

        verify(incidentRepository).findByOptionalFilters(
                IncidentStatus.OPEN, IncidentPriority.HIGH, pageable);
    }

    // Test 3: Update incident details
    @Test
    void shouldUpdateIncidentDetails() {
        IncidentRequest request = createRequest();

        when(incidentRepository.findById(1L))
                .thenReturn(Optional.of(incident));
        when(incidentRepository.save(incident))
                .thenReturn(incident);

        IncidentResponse response =
                incidentService.updateIncident(1L, request);

        assertEquals("Database Connection Failure", response.getTitle());
        assertEquals(IncidentPriority.CRITICAL, response.getPriority());

        // Updating details should not change the existing status.
        assertEquals(IncidentStatus.OPEN, response.getStatus());

        verify(incidentRepository).save(incident);
    }

    // Test 4: Delete an existing incident
    @Test
    void shouldDeleteIncident() {
        when(incidentRepository.findById(1L))
                .thenReturn(Optional.of(incident));

        incidentService.deleteIncident(1L);

        verify(incidentRepository).delete(incident);
    }

    // Test 5: Assign incident to a team member
    @Test
    void shouldAssignIncidentToTeamMember() {
        IncidentAssignmentRequest request =
                new IncidentAssignmentRequest();
        request.setAssignedTo("Lalitha");

        when(incidentRepository.findById(1L))
                .thenReturn(Optional.of(incident));
        when(incidentRepository.save(incident))
                .thenReturn(incident);

        IncidentResponse response =
                incidentService.assignIncident(1L, request);

        assertEquals("Lalitha", response.getAssignedTo());
        verify(incidentRepository).save(incident);
    }

    // Test 6: Allow a valid status transition
    @Test
    void shouldAllowOpenToInProgressTransition() {
        when(incidentRepository.findById(1L))
                .thenReturn(Optional.of(incident));
        when(incidentRepository.save(incident))
                .thenReturn(incident);

        IncidentResponse response =
                incidentService.updateIncidentStatus(
                        1L, IncidentStatus.IN_PROGRESS);

        assertEquals(IncidentStatus.IN_PROGRESS, response.getStatus());
        verify(incidentRepository).save(incident);
    }

    // Test 7: Reject an invalid status transition
    @Test
    void shouldRejectOpenToClosedTransition() {
        when(incidentRepository.findById(1L))
                .thenReturn(Optional.of(incident));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> incidentService.updateIncidentStatus(
                        1L, IncidentStatus.CLOSED)
        );

        assertTrue(exception.getMessage()
                .contains("Invalid status transition"));

        verify(incidentRepository, never())
                .save(any(Incident.class));
    }

    // Test 8: Handle an incident that does not exist
    @Test
    void shouldThrowWhenIncidentDoesNotExist() {
        when(incidentRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> incidentService.getIncidentById(999L)
        );

        verify(incidentRepository, never())
                .save(any(Incident.class));
    }
}
