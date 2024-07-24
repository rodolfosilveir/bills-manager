package br.com.totvs.bills_manager.adapter.in.rest.request;

public class RegisterRequestMock {

    public static RegisterRequest create(String login, String role){
        return new RegisterRequest(
            login,
            "password",
            role
        );
    }
    
}
