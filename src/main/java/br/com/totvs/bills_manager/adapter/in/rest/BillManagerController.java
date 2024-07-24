package br.com.totvs.bills_manager.adapter.in.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.totvs.bills_manager.adapter.in.rest.request.CreateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.request.UpdateSituationBillRequest;
import br.com.totvs.bills_manager.adapter.in.rest.response.BillResponse;
import br.com.totvs.bills_manager.adapter.in.rest.response.DefaultResponse;
import br.com.totvs.bills_manager.adapter.in.rest.response.SummaryAmountPaidResponse;
import br.com.totvs.bills_manager.domain.model.Bill;
import br.com.totvs.bills_manager.port.in.FindBillUC;
import br.com.totvs.bills_manager.port.in.PersistBillUC;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/conta")
@RestController
@RequiredArgsConstructor
public class BillManagerController {

    private final PersistBillUC persistBillUC;

    private final FindBillUC findBillUC;

    @Operation(summary = "Cadastro de conta de pagamento")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponse<BillResponse>> createBill(@RequestBody @Valid final CreateBillRequest request) {
        
        log.info("Starting creation bill");
        Bill response = persistBillUC.create(request);

        log.info("Bill created with success. ID {}", response.getId().toString());
        return ResponseEntity.ok(DefaultResponse.<BillResponse>builder()
            .httpStatus(200)
            .resultData(BillResponse.createFromDomain(response))
            .build());
    }

    @Operation(summary = "Atualiza a conta de pagamento")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponse<BillResponse>> updateBill (
        @PathVariable(name = "id", required = true) final Integer id,
        @RequestBody final UpdateBillRequest request){
        
        log.info("Starting update bill. ID {}", id.toString());
        Bill response = persistBillUC.update(id, request);

        log.info("Bill updated with success. ID {}", id.toString());
        return ResponseEntity.ok(DefaultResponse.<BillResponse>builder()
            .httpStatus(200)
            .resultData(BillResponse.createFromDomain(response))
            .build());
    }

    @Operation(summary = "Atualiza a situação da conta de pagamento")
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponse<BillResponse>> updateSituationBill(
        @PathVariable(name = "id", required = true) final Integer id,
        @RequestBody @Valid final UpdateSituationBillRequest request){
        
        log.info("Starting update situation bill. ID {}", id.toString());
        Bill response = persistBillUC.updateSituation(id, request);

        log.info("Bill situation updated with success. ID {}", id.toString());
        return ResponseEntity.ok(DefaultResponse.<BillResponse>builder()
            .httpStatus(200)
            .resultData(BillResponse.createFromDomain(response))
            .build());
    }

    @Operation(summary = "Busca contas de pagamento com paginação")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponse<List<BillResponse>>> getBillsByFilter(
        @RequestParam(required = false) String descricao,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento,
        @PageableDefault(size = 10) Pageable pageable) {

        log.info("Starting search bills by filters");
        Page<Bill> billsPage = findBillUC.findByFilters(descricao, dataVencimento, pageable);

        List<BillResponse> billResponses = billsPage.stream()
            .map(BillResponse::createFromDomain)
            .collect(Collectors.toList());

        log.info("Search bills by filter finished. {} bills found", billResponses.size());
        return ResponseEntity.ok(DefaultResponse.<List<BillResponse>>builder()
            .httpStatus(200)
            .resultData(billResponses)
            .build());
    }

    @Operation(summary = "Busca conta de pagamento por id")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponse<BillResponse>> getBill(
        @PathVariable(name = "id", required = true) final Integer id)   {

        log.info("Starting find bill. ID {}", id.toString());
        Bill bill = findBillUC.findById(id);

        log.info("Bill with ID {} found", id.toString());
        return ResponseEntity.ok(DefaultResponse.<BillResponse>builder()
            .httpStatus(200)
            .resultData(BillResponse.createFromDomain(bill))
            .build());
    }

    @Operation(summary = "Busca valor total pago por periodo")
    @GetMapping(path = "/valorTotal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponse<SummaryAmountPaidResponse>> getAmountPaid(
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamentoInicio,
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamentoFim) {

        log.info("Starting CSV import proccess.");
        BigDecimal totalAmount = findBillUC.summaryAmountPaid(dataPagamentoInicio, dataPagamentoFim);

        log.info("CSV import proccess finished. {} bills imported", totalAmount.toString());
        return ResponseEntity.ok(DefaultResponse.<SummaryAmountPaidResponse>builder()
            .httpStatus(200)
            .resultData(new SummaryAmountPaidResponse(totalAmount))
            .build());
    }
    
}
