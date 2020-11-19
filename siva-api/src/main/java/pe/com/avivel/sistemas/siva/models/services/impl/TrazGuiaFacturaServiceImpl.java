package pe.com.avivel.sistemas.siva.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.ITrazGuiaFacturaDao;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.GuiaFactura;
import pe.com.avivel.sistemas.siva.models.services.spec.IGuiaFacturaService;

import java.util.List;

@Service
public class TrazGuiaFacturaServiceImpl implements IGuiaFacturaService {

	@Autowired
	private ITrazGuiaFacturaDao guiaFacturaDao;

	@Override
	@Transactional(readOnly = true)
	public List<GuiaFactura> findAll() {
		return (List<GuiaFactura>) guiaFacturaDao.findAll();
	}

	@Override
	public Page<GuiaFactura> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public GuiaFactura findById(Integer id) {
		return guiaFacturaDao.findById(id).orElse(null);
	}

	@Override
	public GuiaFactura save(GuiaFactura guiaFactura) {
		return guiaFacturaDao.save(guiaFactura);
	}

	@Override
	public void delete(Integer id) {
		guiaFacturaDao.deleteById(id);
	}

}
