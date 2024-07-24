package br.com.totvs.bills_manager.adapter.out.persistence.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.totvs.bills_manager.adapter.out.persistence.entity.ContaEntity;

public interface ContaRepository extends JpaRepository<ContaEntity, Integer> {

    Page<ContaEntity> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
    Page<ContaEntity> findByDataVencimento(LocalDate dataVencimento, Pageable pageable);
    Page<ContaEntity> findByDescricaoContainingIgnoreCaseAndDataVencimento(String descricao, LocalDate dataVencimento, Pageable pageable);

    @Query(value = "SELECT sum(valor) FROM public.conta where data_pagamento <= :dataPagamentoInicio and :dataPagamentoFim <= data_pagamento", nativeQuery = true)
    BigDecimal summaryAmountPaid(@Param("dataPagamentoInicio") LocalDate dataPagamentoInicio, @Param("dataPagamentoFim") LocalDate dataPagamentoFim);
    
}
