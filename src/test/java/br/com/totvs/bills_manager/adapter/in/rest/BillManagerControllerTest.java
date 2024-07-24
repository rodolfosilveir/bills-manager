package br.com.totvs.bills_manager.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
import org.springframework.http.ResponseEntity;

import br.com.totvs.bills_manager.adapter.in.rest.request.CreateBillRequestMock;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateBillRequestMock;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateSituationBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateSituationBillRequestMock;
import br.com.totvs.bills_manager.adapter.in.rest.response.BillResponse;
import br.com.totvs.bills_manager.adapter.in.rest.response.DefaultResponse;
import br.com.totvs.bills_manager.adapter.in.rest.response.SummaryAmountPaidResponse;
import br.com.totvs.bills_manager.domain.model.Bill;
import br.com.totvs.bills_manager.domain.model.BillMock;
import br.com.totvs.bills_manager.domain.model.Situacao;
import br.com.totvs.bills_manager.port.in.FindBillUC;
import br.com.totvs.bills_manager.port.in.PersistBillUC;

@ExtendWith(MockitoExtension.class)
class BillManagerControllerTest {

    @Mock
    private PersistBillUC persistBillUC;

    @Mock
    private FindBillUC findBillUC;

    @InjectMocks
    private BillManagerController billManagerController;

    @Test
    @DisplayName("Deve retonar a Bill com sucesso, metodo createBill")
    void shouldReturnsBillSuccessfullyCreateBill(){

        Integer id = 2;
        when(persistBillUC.create(any())).thenReturn(BillMock.create(id));

        ResponseEntity<DefaultResponse<BillResponse>> response = billManagerController.createBill(CreateBillRequestMock.create());

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());

    }

    @Test
    @DisplayName("Deve retonar a Bill com sucesso, metodo updateBill")
    void shouldReturnsBillSuccessfullyUpdateBill(){

        Integer id = 2;
        UpdateBillRequest request = UpdateBillRequestMock.create();
        when(persistBillUC.update(id, request)).thenReturn(BillMock.create(id));

        ResponseEntity<DefaultResponse<BillResponse>> response = billManagerController.updateBill(id, request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());
        
    }

    @Test
    @DisplayName("Deve retonar a Bill com sucesso, metodo updateSituationBill")
    void shouldReturnsBillSuccessfullyUpdateSituationBill(){

        Integer id = 2;
        UpdateSituationBillRequest request = UpdateSituationBillRequestMock.create(Situacao.A_VENCER.getText());
        when(persistBillUC.updateSituation(id, request)).thenReturn(BillMock.create(id));

        ResponseEntity<DefaultResponse<BillResponse>> response = billManagerController.updateSituationBill(id, request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());
        
    }

    @Test
    @DisplayName("Deve retonar a Paginacao de Bill com sucesso, metodo getBillsByFilters")
    void shouldReturnsBillSuccessfullyGetBillsByFilter(){

        Pageable pageable = PageRequest.of(0, 10);

        String description = "description";

        Integer id = 2;

        Page<Bill> billPage = new PageImpl<>(Collections.singletonList(BillMock.create(id, description, Situacao.A_VENCER)), pageable, 1);

        LocalDate dataVencimento = LocalDate.of(2023, 7, 19);

        
        when(findBillUC.findByFilters(anyString(), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(billPage);

        ResponseEntity<DefaultResponse<List<BillResponse>>> response = billManagerController.getBillsByFilter(description, dataVencimento, pageable);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());
        
    }

    @Test
    @DisplayName("Deve retonar a Bill com sucesso, metodo getBill")
    void shouldReturnsBillSuccessfullyGetBill(){

        Integer id = 2;
        
        when(findBillUC.findById(id))
                .thenReturn(BillMock.create(id));

        ResponseEntity<DefaultResponse<BillResponse>> response = billManagerController.getBill(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());
        
    }

    @Test
    @DisplayName("Deve retonar o BigDecimal com sucesso, metodo getAmountPaid")
    void shouldReturnsBillSuccessfullyGetAmountPaid(){

        LocalDate dataInicio = LocalDate.of(2023, 7, 19);
        LocalDate dataFim = LocalDate.of(2023, 7, 19);
        when(findBillUC.summaryAmountPaid(dataInicio, dataFim))
                .thenReturn(BigDecimal.valueOf(100L));

        ResponseEntity<DefaultResponse<SummaryAmountPaidResponse>> response = billManagerController.getAmountPaid(dataInicio, dataFim);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());
        
    }  
    
}
