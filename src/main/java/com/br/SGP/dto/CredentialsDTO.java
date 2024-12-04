package com.br.SGP.dto;

import com.br.SGP.model.UserType;
import com.br.SGP.model.WorkingHours;
import lombok.Data;

@Data
public class CredentialsDTO {
    String username;
    String password;
    String roles = "USER";
    String name;
    String email;
    WorkingHours workingHours;
    UserType userType;
}