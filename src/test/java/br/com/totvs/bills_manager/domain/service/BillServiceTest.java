package br.com.totvs.bills_manager.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
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

import br.com.totvs.bills_manager.adapter.in.rest.request.CreateBillRequestMock;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateBillRequestMock;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateSituationBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateSituationBillRequestMock;
import br.com.totvs.bills_manager.domain.exception.BillNotFoundException;
import br.com.totvs.bills_manager.domain.model.Bill;
import br.com.totvs.bills_manager.domain.model.BillMock;
import br.com.totvs.bills_manager.domain.model.Situacao;
import br.com.totvs.bills_manager.port.out.BillPort;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private BillPort billPort;

    @InjectMocks
    private BillService billService;

    @Test
    @DisplayName("Deve retonar a Bill com sucesso, metodo Create")
    void shouldReturnsBillSuccessfullyCreate(){

        Integer id = 2;
        when(billPort.save(any())).thenReturn(BillMock.create(id));

        Bill bill = billService.create(CreateBillRequestMock.create());

        assertNotNull(bill);
        assertEquals(id, bill.getId());
    }

    @Test
    @DisplayName("Deve retonar BillNotFoundException, metodo update")
    void shouldReturnsBillNotFoundExceptionUpdate(){

        Integer id = 2;
        when(billPort.findById(id)).thenReturn(Optional.empty());

        UpdateBillRequest request = UpdateBillRequestMock.create();

        BillNotFoundException e = Assertions.assertThrows(BillNotFoundException.class, () -> {
            billService.update(id, request);
        });

        assertEquals("Conta com id " + id.toString() + " não encontrada", e.getMessage());
    }

    @Test
    @DisplayName("Deve retonar Bill atualizada com sucesso, metodo update")
    void shouldReturnsBillUpdatedSuccessfullyUpdate(){

        Integer id = 2;

        String oldestDescription = "oldest description";
        String updatedDescription = "updated description";

        Bill oldestBill = BillMock.create(id, oldestDescription, Situacao.A_VENCER);
        oldestBill.update(null);

        when(billPort.findById(id)).thenReturn(Optional.of(
            oldestBill
        ));

        when(billPort.update(oldestBill)).thenReturn(
            BillMock.create(id, updatedDescription, Situacao.A_VENCER)
        );

        Bill billResponse = billService.update(id, UpdateBillRequestMock.create(updatedDescription));

        assertNotEquals(oldestDescription, billResponse.getDescricao());
        assertEquals(updatedDescription, billResponse.getDescricao());

    }

    @Test
    @DisplayName("Deve retonar BillNotFoundException, metodo updateSituation")
    void shouldReturnsBillNotFoundExceptionUpdateSituation(){

        Integer id = 2;
        when(billPort.findById(id)).thenReturn(Optional.empty());

        UpdateSituationBillRequest request = UpdateSituationBillRequestMock.create("a vencer");

        BillNotFoundException e = Assertions.assertThrows(BillNotFoundException.class, () -> {
            billService.updateSituation(id, request);
        });

        assertEquals("Conta com id " + id.toString() + " não encontrada", e.getMessage());
    }

    @Test
    @DisplayName("Deve retonar Bill atualizada com sucesso, metodo updateSituation")
    void shouldReturnsBillUpdatedSuccessfullyUpdateSituation(){

        Integer id = 2;

        String description = "description";
        Situacao oldestSituation = Situacao.A_VENCER;
        Situacao updatedSituation = Situacao.PAGO;

        Bill oldestBill = BillMock.create(id, description, oldestSituation);
        oldestBill.updateSituation(null);

        when(billPort.findById(id)).thenReturn(Optional.of(
            oldestBill
        ));

        when(billPort.update(oldestBill)).thenReturn(
            BillMock.create(id, description, updatedSituation)
        );

        Bill billResponse = billService.updateSituation(id, UpdateSituationBillRequestMock.create(updatedSituation.getText()));

        assertNotEquals(oldestSituation, billResponse.getSituacao());
        assertEquals(updatedSituation, billResponse.getSituacao());

    }

    @Test
    @DisplayName("Deve retonar uma Paginação de Bill com sucesso, metodo findByFilters")
    void shouldReturnsBillSuccessfullyFindByFilters(){

        Pageable pageable = PageRequest.of(0, 10);

        String description = "description";

        Page<Bill> billPage = new PageImpl<>(Collections.singletonList(BillMock.create(2, description, Situacao.A_VENCER)), pageable, 1);

        
        LocalDate dataVencimento = LocalDate.of(2023, 7, 19);

        when(billPort.findByDescricaoAndDataVencimento(anyString(), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(billPage);

        Page<Bill> result = billService.findByFilters(description, dataVencimento, pageable);

        assertEquals(billPage, result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Deve retonar BillNotFoundException, metodo findById")
    void shouldReturnsBillNotFoundExceptionFindById(){

        Integer id = 2;
        when(billPort.findById(id)).thenReturn(Optional.empty());

        BillNotFoundException e = Assertions.assertThrows(BillNotFoundException.class, () -> {
            billService.findById(id);
        });

        assertEquals("Conta com id " + id.toString() + " não encontrada", e.getMessage());
    }

    @Test
    @DisplayName("Deve retonar Bill com sucesso, metodo findById")
    void shouldReturnsBillUpdatedSuccessfullyFindById(){

        Integer id = 2;

        Bill bill = BillMock.create(id);

        when(billPort.findById(id)).thenReturn(Optional.of(
            bill
        ));

        Bill billResponse = billService.findById(id);

        assertEquals(bill.getId(), billResponse.getId());

    }

    @Test
    @DisplayName("Deve retonar BigDecimal com sucesso, metodo summaryAmountPaid")
    void shouldReturnsBillUpdatedSuccessfullySummaryAmountPaid(){

        BigDecimal summary = BigDecimal.valueOf(20L);
        LocalDate dataInicio = LocalDate.of(2024, 12, 31);
        LocalDate dataFim = LocalDate.of(2024, 12, 31);

        when(billPort.summaryAmountPaid(dataInicio, dataFim)).thenReturn(summary);

        BigDecimal response = billService.summaryAmountPaid(dataInicio, dataFim);

        assertEquals(summary, response);

    }
    
}
