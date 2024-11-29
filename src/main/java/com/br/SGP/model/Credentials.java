package com.br.SGP.model;

import com.br.SGP.base.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "credentials_tbl")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Credentials extends BaseModel  {//implements UserDetails

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId
    @JoinColumn(name = "id_user")
    User usuario;

    @NotNull(message = "O campo 'username' não pode ser nulo.")
    @NotBlank(message = "O campo 'username' não pode ser vazio.")
    @Column(unique = true)
    String username;

    @NotNull(message = "O campo 'password' não pode ser nulo.")
    @NotBlank(message = "O campo 'password' não pode ser vazio.")
    String password;

    String roles; // admin, user, personal

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/
}
