package br.com.totvs.bills_manager.adapter.in.rest;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.totvs.bills_manager.adapter.in.rest.request.LoginRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.RegisterRequest;
import br.com.totvs.bills_manager.adapter.in.rest.response.LoginResponse;
import br.com.totvs.bills_manager.domain.model.User;
import br.com.totvs.bills_manager.domain.service.TokenService;
import br.com.totvs.bills_manager.port.in.UserUC;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserUC userUC;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request){

        userUC.registerUser(request);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }
    
}
