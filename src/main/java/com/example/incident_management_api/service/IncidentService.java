
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IncidentService {

    private static final Logger log =
            LoggerFactory.getLogger(IncidentService.class);

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    // 1. Create an incident
    public IncidentResponse createIncident(IncidentRequest request) {
        log.info("Creating incident for service: {}",
                request.getServiceName());

        Incident incident = new Incident();
        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setPriority(request.getPriority());
        incident.setServiceName(request.getServiceName());
        incident.setEnvironment(request.getEnvironment());
        incident.setStatus(IncidentStatus.OPEN);

        Incident savedIncident = incidentRepository.save(incident);

        log.info("Incident created successfully. ID: {}, Priority: {}",
                savedIncident.getId(), savedIncident.getPriority());

        return toResponse(savedIncident);
    }

    // 2. Get incidents with optional filters, pagination and sorting
    @Transactional(readOnly = true)
    public Page<IncidentResponse> getAllIncidents(
            IncidentStatus status,
            IncidentPriority priority,
            Pageable pageable) {

        log.debug(
                "Fetching incidents. Status: {}, Priority: {}, Page: {}, Size: {}",
                status, priority, pageable.getPageNumber(),
                pageable.getPageSize());

        Page<IncidentResponse> results = incidentRepository
                .findByOptionalFilters(status, priority, pageable)
                .map(this::toResponse);

        log.debug("Found {} incidents on the requested page",
                results.getNumberOfElements());

        return results;
    }

    // 3. Get one incident by ID
    @Transactional(readOnly = true)
    public IncidentResponse getIncidentById(Long id) {
        log.debug("Fetching incident ID: {}", id);

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Incident ID {} not found", id);
                    return new ResourceNotFoundException(
                            "Incident with id " + id + " not found");
                });

        return toResponse(incident);
    }

    // 4. Update incident details
    public IncidentResponse updateIncident(
            Long id, IncidentRequest request) {

        log.info("Updating incident ID: {}", id);

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update incident: ID {} not found", id);
                    return new ResourceNotFoundException(
                            "Incident with id " + id + " not found");
                });

        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setPriority(request.getPriority());
        incident.setServiceName(request.getServiceName());
        incident.setEnvironment(request.getEnvironment());

        // Keep the existing status and assignee unchanged.
        Incident updatedIncident = incidentRepository.save(incident);

        log.info("Incident ID {} updated successfully", id);

        return toResponse(updatedIncident);
    }

    // 5. Delete an incident
    public void deleteIncident(Long id) {
        log.info("Deleting incident ID: {}", id);

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot delete incident: ID {} not found", id);
                    return new ResourceNotFoundException(
                            "Incident with id " + id + " not found");
                });

        incidentRepository.delete(incident);

        log.info("Incident ID {} deleted successfully", id);
    }

    // 6. Update incident status with transition validation
    public IncidentResponse updateIncidentStatus(
            Long id, IncidentStatus newStatus) {

        log.info("Updating status for incident ID {} to {}", id, newStatus);

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update status: incident ID {} not found", id);
                    return new ResourceNotFoundException(
                            "Incident with id " + id + " not found");
                });

        IncidentStatus currentStatus = incident.getStatus();

        if (!isValidTransition(currentStatus, newStatus)) {
            log.warn(
                    "Rejected status transition for incident {}: {} -> {}",
                    id, currentStatus, newStatus);

            throw new IllegalStateException(
                    "Invalid status transition from "
                            + currentStatus + " to " + newStatus);
        }

        incident.setStatus(newStatus);

        Incident updatedIncident = incidentRepository.save(incident);

        log.info("Incident ID {} status changed from {} to {}",
                id, currentStatus, newStatus);

        return toResponse(updatedIncident);
    }

    // Allowed status transitions
    private boolean isValidTransition(
            IncidentStatus current, IncidentStatus next) {

        if (current == null || next == null) {
            return false;
        }

        return switch (current) {
            case OPEN -> next == IncidentStatus.IN_PROGRESS;
            case IN_PROGRESS -> next == IncidentStatus.RESOLVED;
            case RESOLVED -> next == IncidentStatus.CLOSED;
            case CLOSED -> false;
        };
    }

    // 7. Assign incident to a team member
    public IncidentResponse assignIncident(
            Long id, IncidentAssignmentRequest request) {

        log.info("Assigning incident ID {} to {}",
                id, request.getAssignedTo());

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot assign incident: ID {} not found", id);
                    return new ResourceNotFoundException(
                            "Incident with id " + id + " not found");
                });

        incident.setAssignedTo(request.getAssignedTo());

        Incident updatedIncident = incidentRepository.save(incident);

        log.info("Incident ID {} assigned successfully", id);

        return toResponse(updatedIncident);
    }

    // Convert entity to response DTO
    private IncidentResponse toResponse(Incident incident) {
        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getPriority(),
                incident.getStatus(),
                incident.getServiceName(),
                incident.getEnvironment(),
                incident.getAssignedTo(),
                incident.getCreatedAt(),
                incident.getUpdatedAt()
        );
    }
}
