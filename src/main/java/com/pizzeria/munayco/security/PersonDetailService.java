package com.pizzeria.munayco.security;

import com.pizzeria.munayco.entity.UsersEntity;
import com.pizzeria.munayco.repository.UsersRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class PersonDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;
    UsersEntity usersEntity;

    public PersonDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Iniciando sesión como: " + username);
        usersEntity = usersRepository.findByEmail(username);
        if (!Objects.isNull(usersEntity)) {
            log.info("Carga de usuario exitoso");
            return new User(usersEntity.getEmail(),usersEntity.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public UsersEntity getUsers() {
        return usersEntity;
    }
}
