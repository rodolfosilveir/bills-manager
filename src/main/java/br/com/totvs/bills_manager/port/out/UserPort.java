package br.com.totvs.bills_manager.port.out;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.totvs.bills_manager.domain.model.User;

public interface UserPort {

    Optional<UserDetails> findByLogin(String login);

    void save(User user);
    
}
