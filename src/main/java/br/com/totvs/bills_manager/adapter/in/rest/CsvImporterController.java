package br.com.totvs.bills_manager.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.totvs.bills_manager.port.in.CsvImporteUC;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequestMapping("/csv")
@RestController
@RequiredArgsConstructor
@Tag(name = "Importação CSV", description = "Operações relacionadas a importação de dados baseados em CSV")
public class CsvImporterController {

    private final CsvImporteUC csvImportUC;

    @Operation(summary = "Importa CSV para cadastro de contas")
    @PostMapping(path = "/contas")
    public ResponseEntity<Void> importBillCsv(@RequestParam("file") MultipartFile file) {
        
        csvImportUC.bills(file);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
}
