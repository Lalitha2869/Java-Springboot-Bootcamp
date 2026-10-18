
package com.example.incident_management_api.repository;

import com.example.incident_management_api.model.Incident;
import com.example.incident_management_api.model.IncidentPriority;
import com.example.incident_management_api.model.IncidentStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository
        extends JpaRepository<Incident, Long> {

    @Query("""
            SELECT i FROM Incident i
            WHERE (:status IS NULL OR i.status = :status)
              AND (:priority IS NULL OR i.priority = :priority)
            """)
    Page<Incident> findByOptionalFilters(
            @Param("status") IncidentStatus status,
            @Param("priority") IncidentPriority priority,
            Pageable pageable
    );
}
