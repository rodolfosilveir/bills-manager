package br.com.totvs.bills_manager.adapter.out.persistence;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.totvs.bills_manager.adapter.out.persistence.entity.UserEntity;
import br.com.totvs.bills_manager.adapter.out.persistence.repository.UserRepository;
import br.com.totvs.bills_manager.domain.model.User;
import br.com.totvs.bills_manager.port.out.UserPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort{

    private final UserRepository userRepository;

    @Override
    public Optional<UserDetails> findByLogin(String login) {
        return userRepository.findByLogin(login).map(UserEntity::toDomain);
    }

    @Override
    public void save(User user) {
        userRepository.save(UserEntity.fromDomain(user));
    }
    
}
