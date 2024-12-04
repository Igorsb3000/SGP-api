package com.br.SGP.model;

import com.br.SGP.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "points_tbl")
public class Point extends BaseModel {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointType pointType;

    @Column (nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateHour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    User usuario;

}