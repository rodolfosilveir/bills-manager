package br.com.totvs.bills_manager.domain.model;

public class UserMock {

    public static User create(String username){
        return User.builder()
            .id("1")
            .login(username)
            .password("password")
            .role(Role.ADMIN)
        .build();
    }
    
}
