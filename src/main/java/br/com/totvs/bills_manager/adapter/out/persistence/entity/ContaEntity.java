package br.com.totvs.bills_manager.adapter.out.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.totvs.bills_manager.domain.model.Bill;
import br.com.totvs.bills_manager.domain.model.Situacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "conta")
@NoArgsConstructor
@AllArgsConstructor
public class ContaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "situacao")
    private String situacao;

    public static ContaEntity fromDomain(Bill domain){
        return ContaEntity.builder()
            .id(domain.getId())
            .dataVencimento(domain.getDataVencimento())
            .dataPagamento(domain.getDataPagamento())
            .valor(domain.getValor())
            .descricao(domain.getDescricao())
            .situacao((domain.getSituacao().getText()))
        .build();
    }

    public Bill toDomain(){
        return Bill.builder()
            .id(this.getId())
            .dataVencimento(this.getDataVencimento())
            .dataPagamento(this.getDataPagamento())
            .valor(this.getValor())
            .descricao(this.getDescricao())
            .situacao((Situacao.fromText(this.getSituacao())))
        .build();
    }
    
}
