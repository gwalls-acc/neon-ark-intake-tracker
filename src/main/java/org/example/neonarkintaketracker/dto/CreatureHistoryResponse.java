package org.example.neonarkintaketracker.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreatureHistoryResponse(
        Long id,
        String name,
        String habitatName,
        List<ObservationDetail> observations
) {
    // Nesting the detail record inside the main response record
    public record ObservationDetail(
            String content,
            String authorName,
            LocalDateTime timestamp
    ) {}
}
