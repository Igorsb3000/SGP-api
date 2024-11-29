package com.br.SGP.model;

import com.br.SGP.base.BaseModel;
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

    @Column(nullable = false)
    private PointType pointType;

    @Column (nullable = false)
    private LocalDateTime dateHour;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId
    @JoinColumn(name = "id_user")
    User usuario;

}