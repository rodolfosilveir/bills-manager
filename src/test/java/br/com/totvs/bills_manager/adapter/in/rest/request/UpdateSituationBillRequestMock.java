package br.com.totvs.bills_manager.adapter.in.rest.request;

public class UpdateSituationBillRequestMock {

    public static UpdateSituationBillRequest create(String situation){
        return new UpdateSituationBillRequest(situation);
    }
    
}
