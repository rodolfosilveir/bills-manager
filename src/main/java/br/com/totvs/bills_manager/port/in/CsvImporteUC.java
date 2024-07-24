package br.com.totvs.bills_manager.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface CsvImporteUC {

    void bills(MultipartFile file);
    
}
