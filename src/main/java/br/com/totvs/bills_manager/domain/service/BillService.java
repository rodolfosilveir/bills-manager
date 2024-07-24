package br.com.totvs.bills_manager.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.totvs.bills_manager.adapter.in.rest.request.CreateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateSituationBillRequest;
import br.com.totvs.bills_manager.domain.exception.BillNotFoundException;
import br.com.totvs.bills_manager.domain.model.Bill;
import br.com.totvs.bills_manager.domain.model.Situacao;
import br.com.totvs.bills_manager.port.in.FindBillUC;
import br.com.totvs.bills_manager.port.in.PersistBillUC;
import br.com.totvs.bills_manager.port.out.BillPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillService implements PersistBillUC, FindBillUC{

    private final BillPort billPort;
    
    @Override
    public Bill create(CreateBillRequest request) {
        return billPort.save(Bill.builder()
            .dataVencimento(request.getDataVencimento())
            .dataPagamento(request.getDataPagamento())
            .valor(request.getValor())
            .descricao(request.getDescricao())
            .situacao(Situacao.fromText(request.getSituacao()))
        .build());

    }

    @Override
    public Bill update(Integer id, UpdateBillRequest request){

        Bill bill = billPort.findById(id)
            .orElseThrow(() -> new BillNotFoundException(id));

        return billPort.update(bill.update(request));
    }

    @Override
    public Bill updateSituation(Integer id, UpdateSituationBillRequest request){

        Bill bill = billPort.findById(id)
            .orElseThrow(() -> new BillNotFoundException(id));

        return billPort.update(bill.updateSituation(request));
    }

    @Override
    public Page<Bill> findByFilters(String descricao, LocalDate dataVencimento, Pageable pageable) {
        return billPort.findByDescricaoAndDataVencimento(descricao, dataVencimento, pageable);        
    }

    @Override
    public Bill findById(Integer id){
        return billPort.findById(id)
        .orElseThrow(() -> new BillNotFoundException(id));
    }

    @Override
    public BigDecimal summaryAmountPaid(LocalDate dataPagamentoInicio, LocalDate dataPagamentoFim) {
        return billPort.summaryAmountPaid(dataPagamentoInicio, dataPagamentoFim);
    }

    
}
