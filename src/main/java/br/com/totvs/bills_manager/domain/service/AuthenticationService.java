package br.com.totvs.bills_manager.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.totvs.bills_manager.adapter.in.rest.request.RegisterRequest;
import br.com.totvs.bills_manager.domain.exception.UserAlreadyExistsException;
import br.com.totvs.bills_manager.domain.model.Role;
import br.com.totvs.bills_manager.domain.model.User;
import br.com.totvs.bills_manager.port.in.UserUC;
import br.com.totvs.bills_manager.port.out.UserPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService, UserUC {

    private final UserPort userPort;

    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userPort.findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " não encontrado"));
    }

    @Override
    public void registerUser(RegisterRequest request) {
        if(userPort.findByLogin(request.login()).isPresent()){
            throw new UserAlreadyExistsException(request.login());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        userPort.save(User.builder()
            .login(request.login())
            .password(encryptedPassword)
            .role(Role.fromText(request.role()))
        .build());
    }

}