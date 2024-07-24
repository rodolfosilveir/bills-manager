package br.com.totvs.bills_manager.domain.service;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.totvs.bills_manager.domain.exception.ImportCsvException;
import br.com.totvs.bills_manager.domain.model.Bill;
import br.com.totvs.bills_manager.domain.model.Situacao;
import br.com.totvs.bills_manager.port.in.CsvImporteUC;
import br.com.totvs.bills_manager.port.out.BillPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsvImportService implements CsvImporteUC{

    private final BillPort billPort;

    @Value("${csv.contas.path}")
    private String csvContasPath;

    @Override
    public void bills(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        try{
            File directory = new File(csvContasPath);
            FileUtils.deleteDirectory(directory);
            directory.mkdir();
            file.transferTo(Paths.get(csvContasPath + fileName));
            Path path = Paths.get(csvContasPath + fileName);
            List<String> linesFile = Files.readAllLines(path, StandardCharsets.UTF_8);
            billPort.saveAll(this.generateBillsFromCsvLinhas(linesFile));
        }catch(Exception e){
            log.error(
					"Error in csv import proccess.", e);
            throw new ImportCsvException(fileName, e.getMessage());
        }
        
    }

    private List<Bill> generateBillsFromCsvLinhas(List<String> linesFile){

        return linesFile.stream()
            .skip(1)
            .map(line -> {
                String[] fields = line.split(";");
                return Bill.builder()
                    .dataVencimento(LocalDate.parse(fields[0]))
                    .dataPagamento(LocalDate.parse(fields[1]))
                    .valor(BigDecimal.valueOf(Double.parseDouble(fields[2])))
                    .descricao(fields[3])
                    .situacao(Situacao.fromText(fields[4]))
                    .build();
            })
            .collect(Collectors.toList());
    }
    
}
