package com.br.SGP.repository;

import com.br.SGP.base.BaseRepository;
import com.br.SGP.model.Credentials;
import java.util.Optional;

public interface CredentialsRepository extends BaseRepository<Credentials> {
    Optional<Credentials> findCredentialsByUsername(String username);
}
