package com.br.SGP.service;

import com.br.SGP.base.BaseService;
import com.br.SGP.dto.CredentialsDTO;
import com.br.SGP.model.Credentials;
import com.br.SGP.model.User;
import com.br.SGP.model.UserType;
import com.br.SGP.repository.CredentialsRepository;
import com.br.SGP.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class CredentialsService extends BaseService<Credentials, CredentialsRepository> implements UserDetailsService {
    @Autowired
    CredentialsRepository credentialsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Credentials> credentials = repository.findCredentialsByUsername(username);
        if(credentials.isPresent()){
            return credentials.get();
        } else {
            throw new UsernameNotFoundException("O usuário com username = " + username + " não existe no sistema.");
        }
    }

    public Credentials createNewUser(String username, CredentialsDTO credentialsDTO){
        Optional<User> user;
        Optional<Credentials> credentials;

        credentials = credentialsRepository.findCredentialsByUsername(username);

        if(credentials.isPresent()){
            user = userRepository.findById(credentials.get().getId());
            if(user.isPresent()){
                if(user.get().getUserType().equals(UserType.ADMIN)){
                    User u = new User();
                    u.setName(credentialsDTO.getName());
                    u.setEmail(credentialsDTO.getEmail());
                    u.setUserType(credentialsDTO.getUserType());
                    u.setWorkingHours(credentialsDTO.getWorkingHours());

                    Credentials c = new Credentials();
                    c.setRoles(credentialsDTO.getRoles());
                    c.setUsername(credentialsDTO.getUsername());
                    c.setPassword(credentialsDTO.getPassword());
                    c.setUsuario(u);

                    return this.create(c);
                }
            }
        }
        return null;
    }


    @Override
    @Transactional
    public Credentials create(Credentials c){
        c.setPassword(encoder.encode(c.getPassword()));
        return credentialsRepository.save(c);
    }
}
