package br.com.totvs.bills_manager.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.totvs.bills_manager.adapter.out.persistence.entity.ContaEntity;
import br.com.totvs.bills_manager.adapter.out.persistence.repository.ContaRepository;
import br.com.totvs.bills_manager.domain.model.Bill;
import br.com.totvs.bills_manager.domain.model.BillMock;

@ExtendWith(MockitoExtension.class)
class BillAdapterTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private BillAdapter billAdapter;

    @Test
    @DisplayName("Deve retonar a Bill com sucesso, metodo save")
    void shouldReturnsBillSuccessfullySave(){

        Integer id = 2;
        Bill billMock = BillMock.create(id);
        when(contaRepository.save(any())).thenReturn(ContaEntity.fromDomain(billMock));

        Bill bill = billAdapter.save(billMock);

        assertNotNull(bill);
        assertEquals(billMock.getId(), bill.getId());
    }

    @Test
    @DisplayName("Deve retonar um Optional Bill com sucesso, metodo findById")
    void shouldReturnsOptionalBillSuccessfullyFindById(){

        Integer id = 2;
        Bill billMock = BillMock.create(id);
        when(contaRepository.findById(any())).thenReturn(Optional.of(ContaEntity.fromDomain(billMock)));

        Optional<Bill> bill = billAdapter.findById(id);

        assertNotNull(bill);
        assertTrue(bill.isPresent());
    }

    @Test
    @DisplayName("Deve retonar um Optional Bill vazio com sucesso, metodo findById")
    void shouldReturnsOptionalEmptySuccessfullyFindById(){

        Integer id = 2;
        when(contaRepository.findById(any())).thenReturn(Optional.empty());

        Optional<Bill> bill = billAdapter.findById(id);

        assertNotNull(bill);
        assertTrue(bill.isEmpty());
    }

    @Test
    @DisplayName("Deve retonar a Bill com sucesso, metodo update")
    void shouldReturnsBillSuccessfullyUpdate(){

        Integer id = 2;
        Bill billMock = BillMock.create(id);
        when(contaRepository.save(any())).thenReturn(ContaEntity.fromDomain(billMock));

        Bill bill = billAdapter.update(billMock);

        assertNotNull(bill);
        assertEquals(billMock.getId(), bill.getId());
    }

    @Test
    @DisplayName("Deve retonar um Page<Bill> com sucesso com DataVencimento, metodo findByDescricaoAndDataVencimento")
    void shouldReturnsPageBillDataVencimentoSuccessfullyFindByDescricaoAndDataVencimento(){

        LocalDate dataVencimento = LocalDate.of(2023, 7, 19);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ContaEntity> contaEntityPage = new PageImpl<>(Collections.singletonList(ContaEntity.fromDomain(BillMock.create(2))), pageable, 1);
        when(contaRepository.findByDataVencimento(dataVencimento, pageable)).thenReturn(contaEntityPage);

        Page<Bill> pageBill = billAdapter.findByDescricaoAndDataVencimento(null, dataVencimento, pageable);

        assertNotNull(pageBill);
        assertEquals(1, pageBill.getTotalElements());
    }

    @Test
    @DisplayName("Deve retonar um Page<Bill> com sucesso com descricao, metodo findByDescricaoAndDataVencimento")
    void shouldReturnsPageBillDescricaoSuccessfullyFindByDescricaoAndDataVencimento(){

        String descricao = "descricao";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ContaEntity> contaEntityPage = new PageImpl<>(Collections.singletonList(ContaEntity.fromDomain(BillMock.create(2))), pageable, 1);
        when(contaRepository.findByDescricaoContainingIgnoreCase(descricao, pageable)).thenReturn(contaEntityPage);

        Page<Bill> pageBill = billAdapter.findByDescricaoAndDataVencimento(descricao, null, pageable);

        assertNotNull(pageBill);
        assertEquals(1, pageBill.getTotalElements());
    }

    @Test
    @DisplayName("Deve retonar um Page<Bill> com sucesso com descricao e dataVencimento, metodo findByDescricaoAndDataVencimento")
    void shouldReturnsPageBillDescricaoDataVencimentoSuccessfullyFindByDescricaoAndDataVencimento(){

        String descricao = "descricao";
        LocalDate dataVencimento = LocalDate.of(2023, 7, 19);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ContaEntity> contaEntityPage = new PageImpl<>(Collections.singletonList(ContaEntity.fromDomain(BillMock.create(2))), pageable, 1);
        when(contaRepository.findByDescricaoContainingIgnoreCaseAndDataVencimento(descricao, dataVencimento, pageable)).thenReturn(contaEntityPage);

        Page<Bill> pageBill = billAdapter.findByDescricaoAndDataVencimento(descricao, dataVencimento, pageable);

        assertNotNull(pageBill);
        assertEquals(1, pageBill.getTotalElements());
    }

    @Test
    @DisplayName("Deve retonar um Page<Bill> com sucesso, metodo findByDescricaoAndDataVencimento")
    void shouldReturnsPageBillSuccessfullyFindByDescricaoAndDataVencimento(){

        Pageable pageable = PageRequest.of(0, 10);
        Page<ContaEntity> contaEntityPage = new PageImpl<>(Collections.singletonList(ContaEntity.fromDomain(BillMock.create(2))), pageable, 1);
        when(contaRepository.findAll(pageable)).thenReturn(contaEntityPage);

        Page<Bill> pageBill = billAdapter.findByDescricaoAndDataVencimento(null, null, pageable);

        assertNotNull(pageBill);
        assertEquals(1, pageBill.getTotalElements());
    }

    @Test
    @DisplayName("Deve retonar um BigDecimal com sucesso, metodo summaryAmountPaid")
    void shouldReturnsBigDecimalSuccessfullysummaryAmountPaid(){

        LocalDate dataInicio = LocalDate.of(2024, 12, 31);
        LocalDate dataFim = LocalDate.of(2024, 12, 31);

        when(contaRepository.summaryAmountPaid(dataInicio, dataFim)).thenReturn(BigDecimal.valueOf(100));

        BigDecimal summary = billAdapter.summaryAmountPaid(dataInicio, dataFim);

        assertNotNull(summary);
        assertEquals(100D, summary.doubleValue());
    }

    @Test
    @DisplayName("Deve retonar um BigDecimal null com sucesso, metodo summaryAmountPaid")
    void shouldReturnsBigDecimalNullSuccessfullysummaryAmountPaid(){

        LocalDate dataInicio = LocalDate.of(2024, 12, 31);
        LocalDate dataFim = LocalDate.of(2024, 12, 31);

        when(contaRepository.summaryAmountPaid(dataInicio, dataFim)).thenReturn(null);

        BigDecimal summary = billAdapter.summaryAmountPaid(dataInicio, dataFim);

        assertNotNull(summary);
        assertEquals(0, summary.doubleValue());
    }

    @Test
    @DisplayName("Deve retonar a Lista de Bill com sucesso, metodo saveAll")
    void shouldReturnsListBillsSuccessfullySaveAll(){

        Integer id = 2;
        Bill billMock = BillMock.create(id);
        when(contaRepository.saveAll(anyList())).thenReturn(Arrays.asList(ContaEntity.fromDomain(billMock)));

        List<Bill> bills = billAdapter.saveAll(Arrays.asList(billMock));

        assertNotNull(bills);
        assertFalse(bills.isEmpty());
        assertEquals(1, bills.size());
    }
    
}
