package com.br.SGP.controller;

import com.br.SGP.config.ApiVersion;
import com.br.SGP.dto.CredentialsDTO;
import com.br.SGP.model.Credentials;
import com.br.SGP.repository.CredentialsRepository;
import com.br.SGP.repository.UserRepository;
import com.br.SGP.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;


@RestController
@RequestMapping(ApiVersion.V1 + "/credentials")
public class CredentialsController {
    @Autowired
    CredentialsService credentialsService;

    @Autowired
    CredentialsRepository credentialsRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CredentialsDTO credentialsDTO){
        Optional<Credentials> credentials = Optional.ofNullable(credentialsService.createNewUser(userDetails.getUsername(), credentialsDTO));

        if(credentials.isPresent()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Usuário não autorizado! Apenas usuários ADMIN podem realizar um novo cadastro.");

    }

}

