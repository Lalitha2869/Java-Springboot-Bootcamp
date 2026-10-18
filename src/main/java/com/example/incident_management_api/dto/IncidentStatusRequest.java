
package com.example.incident_management_api.dto;

import com.example.incident_management_api.model.IncidentStatus;
import jakarta.validation.constraints.NotNull;

public class IncidentStatusRequest {

    @NotNull(message = "Status is required")
    private IncidentStatus status;

    public IncidentStatusRequest() {
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }
}
