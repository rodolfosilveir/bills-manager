package br.com.totvs.bills_manager.port.in;

import br.com.totvs.bills_manager.adapter.in.rest.request.CreateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateSituationBillRequest;
import br.com.totvs.bills_manager.domain.model.Bill;

public interface PersistBillUC {

    public Bill create(CreateBillRequest request);

    public Bill update(Integer id, UpdateBillRequest request);

    public Bill updateSituation(Integer id, UpdateSituationBillRequest request);
    
}
