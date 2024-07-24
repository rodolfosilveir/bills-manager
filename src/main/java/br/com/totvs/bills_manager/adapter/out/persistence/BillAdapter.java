package br.com.totvs.bills_manager.adapter.out.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import br.com.totvs.bills_manager.adapter.out.persistence.entity.ContaEntity;
import br.com.totvs.bills_manager.adapter.out.persistence.repository.ContaRepository;
import br.com.totvs.bills_manager.domain.model.Bill;
import br.com.totvs.bills_manager.port.out.BillPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BillAdapter implements BillPort{
    
    private final ContaRepository repository;

    @Override
    public Bill save(Bill conta) {
        return repository.save(ContaEntity.fromDomain(conta)).toDomain();
    }

    @Override
    public Optional<Bill> findById(Integer id) {
        return repository.findById(id).map(ContaEntity::toDomain);
    }

    @Override
    public Bill update(Bill conta) {
        return this.save(conta);
    }

    @Override
    public Page<Bill> findByDescricaoAndDataVencimento(String descricao, LocalDate dataVencimento, Pageable pageable) {

        Page<ContaEntity> pageableBill;

        if(StringUtils.isBlank(descricao) && Objects.nonNull(dataVencimento)){
            pageableBill = repository.findByDataVencimento(dataVencimento, pageable);
        }else if(StringUtils.isNotBlank(descricao) && Objects.isNull(dataVencimento)){
            pageableBill = repository.findByDescricaoContainingIgnoreCase(descricao, pageable);
        }else if(StringUtils.isNotBlank(descricao) && Objects.nonNull(dataVencimento)){
            pageableBill = repository.findByDescricaoContainingIgnoreCaseAndDataVencimento(descricao, dataVencimento, pageable);
        }else{
            pageableBill = repository.findAll(pageable);
        }

        return pageableBill.map(ContaEntity::toDomain);
    }

    @Override
    public BigDecimal summaryAmountPaid(LocalDate dataPagamentoInicio, LocalDate dataPagamentoFim) {
        BigDecimal value = repository.summaryAmountPaid(dataPagamentoInicio, dataPagamentoFim);
        return Objects.nonNull(value)?value:BigDecimal.valueOf(0);
    }

    @Override
    public List<Bill> saveAll(List<Bill> bills) {
        return repository.saveAll(bills.stream().map(ContaEntity::fromDomain).toList())
            .stream().map(ContaEntity::toDomain).toList();
    }

}
