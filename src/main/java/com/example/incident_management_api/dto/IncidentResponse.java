
package com.example.incident_management_api.dto;

import com.example.incident_management_api.model.IncidentEnvironment;
import com.example.incident_management_api.model.IncidentPriority;
import com.example.incident_management_api.model.IncidentStatus;

import java.time.LocalDateTime;

public class IncidentResponse {

    private Long id;
    private String title;
    private String description;
    private IncidentPriority priority;
    private IncidentStatus status;
    private String serviceName;
    private IncidentEnvironment environment;
    private String assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public IncidentResponse() {
    }

    public IncidentResponse(
            Long id,
            String title,
            String description,
            IncidentPriority priority,
            IncidentStatus status,
            String serviceName,
            IncidentEnvironment environment,
            String assignedTo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.serviceName = serviceName;
        this.environment = environment;
        this.assignedTo = assignedTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IncidentPriority getPriority() {
        return priority;
    }

    public void setPriority(IncidentPriority priority) {
        this.priority = priority;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public IncidentEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(IncidentEnvironment environment) {
        this.environment = environment;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
