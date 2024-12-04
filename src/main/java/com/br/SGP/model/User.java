package com.br.SGP.model;

import com.br.SGP.base.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_tbl")
public class User extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkingHours workingHours;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

}