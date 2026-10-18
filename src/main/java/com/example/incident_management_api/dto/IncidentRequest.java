
package com.example.incident_management_api.dto;

import com.example.incident_management_api.model.IncidentEnvironment;
import com.example.incident_management_api.model.IncidentPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class IncidentRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title cannot exceed 150 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Priority is required")
    private IncidentPriority priority;

    @NotBlank(message = "Service name is required")
    private String serviceName;

    @NotNull(message = "Environment is required")
    private IncidentEnvironment environment;

    public IncidentRequest() {
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
}
