package br.com.totvs.bills_manager.adapter.in.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateBillRequestMock {

    public static UpdateBillRequest create(){
        return new UpdateBillRequest(
            LocalDate.of(2024, 12, 31),
            LocalDate.of(2024, 12, 31),
            BigDecimal.valueOf(200L),
            "descrição"
        );
    }

    public static UpdateBillRequest create(String description){
        return new UpdateBillRequest(
            LocalDate.of(2024, 12, 31),
            LocalDate.of(2024, 12, 31),
            BigDecimal.valueOf(200L),
            description
        );
    }
    
}
