package com.br.SGP.utils;

import com.br.SGP.dto.TimeDifferenceDTO;
import java.time.Duration;

public class TimeUtils {
    public static TimeDifferenceDTO convertDurationToTime(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return new TimeDifferenceDTO(hours, minutes);
    }
}