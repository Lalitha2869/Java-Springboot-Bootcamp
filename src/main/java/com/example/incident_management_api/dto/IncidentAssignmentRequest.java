
package com.example.incident_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IncidentAssignmentRequest {

    @NotBlank(message = "Assignee name is required")
    @Size(max = 100, message = "Assignee name cannot exceed 100 characters")
    private String assignedTo;

    public IncidentAssignmentRequest() {
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
