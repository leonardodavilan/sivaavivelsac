package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ISolicitudItemDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroMovimientoDTO;
import pe.com.avivel.sistemas.siva.models.dto.FiltroSolicitudDTO;
import pe.com.avivel.sistemas.siva.models.dto.MovimientoQueryDTO;
import pe.com.avivel.sistemas.siva.models.dto.SolicitudItemQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SolicitudItem;
import pe.com.avivel.sistemas.siva.models.services.spec.ISolicitudItemService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitudItemServiceImpl implements ISolicitudItemService {

    @Autowired
    private ISolicitudItemDao solicitudItemDao;

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudItem> findAll() {
        return (List<SolicitudItem>) solicitudItemDao.findAll();
    }

    @Override
    public Page<SolicitudItem> findAll(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudItem findById(Integer id) {
        return solicitudItemDao.findById(id).orElse(null);
    }

    @Override
    public List<SolicitudItem> findAllByFiltroSi(FiltroSolicitudDTO filtroSolicitudDTO) {
        return solicitudItemDao.findAllByFiltroSi(filtroSolicitudDTO);
    }

    @Override
    public List<SolicitudItem> findAllByCodigoSi(Integer codigoSolicitud) {
        return solicitudItemDao.findAllByCodigoSi(codigoSolicitud);
    }


    //DTO
    @Transactional(readOnly = true)
    @Override
    public List<SolicitudItemQueryDTO> findAllDTO() {
        List<SolicitudItemQueryDTO> solicitudItemQueryDTOS = new ArrayList<>();
        solicitudItemDao.findAll().forEach(solicitudItem -> solicitudItemQueryDTOS.add(SolicitudItemQueryDTO.getInstance(solicitudItem)));
        return solicitudItemQueryDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SolicitudItemQueryDTO> findAllByFiltroSiDTO(FiltroSolicitudDTO filtroSolicitudDTO) {
        List<SolicitudItemQueryDTO> solicitudItemQueryDTOS = new ArrayList<>();
        solicitudItemDao.findAllByFiltroSi(filtroSolicitudDTO).forEach(solicitudItem -> solicitudItemQueryDTOS.add(SolicitudItemQueryDTO.getInstance(solicitudItem)));
        return solicitudItemQueryDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SolicitudItemQueryDTO> findAllByCodigoSiDTO(Integer codigoSolicitud) {
        List<SolicitudItemQueryDTO> solicitudItemQueryDTOS = new ArrayList<>();
        solicitudItemDao.findAllByCodigoSi(codigoSolicitud).forEach(solicitudItem -> solicitudItemQueryDTOS.add(SolicitudItemQueryDTO.getInstance(solicitudItem)));
        return solicitudItemQueryDTOS;
    }

}
