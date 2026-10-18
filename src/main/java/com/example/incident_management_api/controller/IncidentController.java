
package com.example.incident_management_api.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.incident_management_api.dto.IncidentRequest;
import com.example.incident_management_api.dto.IncidentResponse;
import com.example.incident_management_api.dto.IncidentStatusRequest;
import com.example.incident_management_api.model.IncidentPriority;
import com.example.incident_management_api.model.IncidentStatus;
import com.example.incident_management_api.service.IncidentService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.incident_management_api.dto.IncidentAssignmentRequest;
@Tag(
        name = "Incident Management",
        description = "APIs for managing operational incidents"
)
@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // 1. Create an incident
    @PostMapping
    public ResponseEntity<IncidentResponse> createIncident(
            @Valid @RequestBody IncidentRequest request) {

        IncidentResponse response =
                incidentService.createIncident(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. Get incidents with optional filters, pagination and sorting
    @GetMapping
    public ResponseEntity<Page<IncidentResponse>> getAllIncidents(
            @RequestParam(required = false) IncidentStatus status,
            @RequestParam(required = false) IncidentPriority priority,
            Pageable pageable) {

        return ResponseEntity.ok(
                incidentService.getAllIncidents(
                        status, priority, pageable)
        );
    }

    // 3. Get one incident by ID
    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponse> getIncidentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                incidentService.getIncidentById(id)
        );
    }

    // 4. Update incident details
    @PutMapping("/{id}")
    public ResponseEntity<IncidentResponse> updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody IncidentRequest request) {

        return ResponseEntity.ok(
                incidentService.updateIncident(id, request)
        );
    }

    // 5. Delete an incident
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(
            @PathVariable Long id) {

        incidentService.deleteIncident(id);

        return ResponseEntity.noContent().build();
    }

    // 6. Update incident status
    @PatchMapping("/{id}/status")
    public ResponseEntity<IncidentResponse> updateIncidentStatus(
            @PathVariable Long id,
            @Valid @RequestBody IncidentStatusRequest request) {

        IncidentResponse response =
                incidentService.updateIncidentStatus(
                        id, request.getStatus());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<IncidentResponse> assignIncident(
            @PathVariable Long id,
            @Valid @RequestBody IncidentAssignmentRequest request) {

        IncidentResponse response =
                incidentService.assignIncident(id, request);

        return ResponseEntity.ok(response);
    }

}
