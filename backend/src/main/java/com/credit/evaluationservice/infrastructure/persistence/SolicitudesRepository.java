package com.credit.evaluationservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudesRepository
        extends JpaRepository<SolicitudesEntity, String> {

    List<SolicitudesEntity>
    findByTipoDocumentoAndNumeroDocumento(
            String tipoDocumento,
            String numeroDocumento
    );

    long countByTipoDocumentoAndNumeroDocumento(
        String tipoDocumento,
        String numeroDocumento
  );
}
