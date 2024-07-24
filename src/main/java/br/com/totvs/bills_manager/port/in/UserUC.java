package br.com.totvs.bills_manager.port.in;

import br.com.totvs.bills_manager.adapter.in.rest.request.RegisterRequest;

public interface UserUC {

    void registerUser(RegisterRequest request);
    
}
