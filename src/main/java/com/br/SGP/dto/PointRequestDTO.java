package com.br.SGP.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PointRequestDTO {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime dateHour;
}