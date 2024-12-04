package com.br.SGP.model;

import com.br.SGP.dto.TimeDifferenceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class MatchDaySummary {
    private String name;
    private String date;
    private boolean isJourneyComplete;
    private TimeDifferenceDTO hoursWorked;
    private TimeDifferenceDTO hoursRemaining;
    private TimeDifferenceDTO overtime;

}
