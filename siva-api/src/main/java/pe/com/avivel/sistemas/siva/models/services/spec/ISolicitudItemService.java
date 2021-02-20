package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.dto.FiltroSolicitudDTO;
import pe.com.avivel.sistemas.siva.models.dto.SolicitudItemQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SolicitudItem;

import java.util.List;

public interface ISolicitudItemService {

    List<SolicitudItem> findAll();
    Page<SolicitudItem> findAll(Pageable pageable);
    SolicitudItem findById(Integer id);
    List<SolicitudItem> findAllByFiltroSi(FiltroSolicitudDTO filtroSolicitudDTO);
    List<SolicitudItem> findAllByCodigoSi(Integer codigoSolicitud);

    List<SolicitudItemQueryDTO> findAllDTO();
    List<SolicitudItemQueryDTO> findAllByFiltroSiDTO(FiltroSolicitudDTO filtroSolicitudDTO);
    List<SolicitudItemQueryDTO> findAllByCodigoSiDTO(Integer codigoSolicitud);
}
