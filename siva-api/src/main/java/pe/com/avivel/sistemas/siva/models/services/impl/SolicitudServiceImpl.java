package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ISolicitudDao;
import pe.com.avivel.sistemas.siva.models.dto.FiltroSolicitudDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Solicitud;
import pe.com.avivel.sistemas.siva.models.services.spec.ISolicitudService;

import java.util.List;

@Service
public class SolicitudServiceImpl implements ISolicitudService {

	@Autowired
	private ISolicitudDao solicitudDao;

	@Override
	@Transactional(readOnly = true)
	public List<Solicitud> findAll() { return (List<Solicitud>) solicitudDao.findAll();
	}

	@Override
	public Page<Solicitud> findAll(Pageable pageable) {
		return null;
	}


	@Override
	@Transactional(readOnly = true)
	public Solicitud findById(Integer id) {
		return solicitudDao.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public Solicitud save(Solicitud solicitud) {
		return solicitudDao.save(solicitud);
	}


	@Override
	public void delete(Integer id) {
		solicitudDao.deleteById(id);
	}

	@Override
	public List<Solicitud> findAllByFiltro(FiltroSolicitudDTO filtroSolicitudDTO) {
		return solicitudDao.findAllByFiltro(filtroSolicitudDTO);
	}

	@Override
	public List<Solicitud> findAllByCodigo(Integer codigoSolicitud) {
		return solicitudDao.findAllByCodigo(codigoSolicitud);
	}

	@Override
	public Integer findMaxCodSolicitud() {
		return solicitudDao.findMaxCodSolicitud();
	}

}
