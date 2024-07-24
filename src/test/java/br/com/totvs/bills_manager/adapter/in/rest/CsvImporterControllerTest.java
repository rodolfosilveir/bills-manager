package br.com.totvs.bills_manager.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import br.com.totvs.bills_manager.port.in.CsvImporteUC;

@ExtendWith(MockitoExtension.class)
class CsvImporterControllerTest {

    @Mock
    private CsvImporteUC csvImporteUC;

    @InjectMocks
    private CsvImporterController csvImporterController;

    @Test
    @DisplayName("Deve importar csv com sucesso, metodo importBillCsv")
    void shouldImportSuccessfullyImportBillCsv(){

        String csvContent = "dataVencimento;dataPagamento;valor;descricao;situacao\n" +
                            "2024-12-31;2024-11-30;1234.56;Descrição 1;A VENCER\n" +
                            "2024-10-15;2024-09-14;789.01;Descrição 2;PAGO";
        MultipartFile mockFile = new MockMultipartFile("file", "contas.csv", "text/csv", csvContent.getBytes());

        doNothing().when(csvImporteUC).bills(mockFile);

        ResponseEntity<Void> response = csvImporterController.importBillCsv(mockFile);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());

        assertNull(response.getBody());
        
    }
    
}
