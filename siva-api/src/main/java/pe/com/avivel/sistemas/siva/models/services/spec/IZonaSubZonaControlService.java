package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.roedor.ZonaSubZonaControl;

import java.util.List;

public interface IZonaSubZonaControlService {

	List<ZonaSubZonaControl> findAll();
	
	Page<ZonaSubZonaControl> findAll(Pageable pageable);

	ZonaSubZonaControl findById(Integer id);

	ZonaSubZonaControl save(ZonaSubZonaControl zonaSubZonaControl);
	
	void delete(Integer id);

	List<ZonaSubZonaControl> findAllBySubZonaControl(Integer subZonaControlId);

}
