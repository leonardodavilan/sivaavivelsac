package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IEstadoSolicitudDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.EstadoSolicitud;
import pe.com.avivel.sistemas.siva.models.services.spec.IEstadoSolicitudService;

import java.util.List;

@Service
public class EstadoSolicitudServiceImpl implements IEstadoSolicitudService {

	@Autowired
	private IEstadoSolicitudDao estadoSolicitudDao;

	@Override
	@Transactional(readOnly = true)
	public List<EstadoSolicitud> findAll() {
		return (List<EstadoSolicitud>) estadoSolicitudDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EstadoSolicitud> findAll(Pageable pageable) {
		return estadoSolicitudDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public EstadoSolicitud findById(Integer id) {
		return estadoSolicitudDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EstadoSolicitud save(EstadoSolicitud estadoSolicitud) {
		return estadoSolicitudDao.save(estadoSolicitud);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		estadoSolicitudDao.deleteById(id);
	}

}
