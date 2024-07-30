package br.com.totvs.bills_manager.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.totvs.bills_manager.domain.exception.ImportCsvException;
import br.com.totvs.bills_manager.port.out.BillPort;

import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
class CsvImportServiceTest {

    @Mock
    private BillPort billPort;

    @InjectMocks
    private CsvImportService csvImportService;

    @Test
    @DisplayName("Deve importar contas do CSV com sucesso, metodo bills")
    void shouldImportBillsSuccessfullyBills() {

        String csvContent = "dataVencimento;dataPagamento;valor;descricao;situacao\n" +
                "2024-12-31;2024-11-30;1234.56;Descrição 1;A VENCER\n" +
                "2024-10-15;2024-09-14;789.01;Descrição 2;PAGO";
        MultipartFile mockFile = new MockMultipartFile("file", "contas.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        ReflectionTestUtils.setField(csvImportService, "csvContasPath", "csv/contas/");
        csvImportService.bills(mockFile);

        verify(billPort, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve lançar ImportCsvException ao ocorrer um erro, metodo bills")
    void shouldThrowImportCsvExceptionOnErrorBills() {

        ReflectionTestUtils.setField(csvImportService, "csvContasPath", "csv/contas/");

        String csvContent = "dataVencimento;dataPagamento;valor;descricao;situacao\n" +
                            "2024-12-31;2024-11-30;1234.56;Descrição 1;A PAGAR\n" +
                            "2024-10-15;2024-09-14;789.01;Descrição 2;PAGO";
        MultipartFile mockFile = new MockMultipartFile("file", "contas.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        ImportCsvException e = assertThrows(ImportCsvException.class, () -> csvImportService.bills(mockFile));

        assertEquals("Erro ao importar o arquivo contas.csv. Situação 'A PAGAR' não é uma situação válida. Valores válidos: 'a vencer', 'em atraso', 'pago' ", e.getMessage());
    }

    
}
