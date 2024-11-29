package com.br.SGP.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkingHours {
    SEIS_HORAS(6),
    OITO_HORAS(8);

    private final int hours;
}