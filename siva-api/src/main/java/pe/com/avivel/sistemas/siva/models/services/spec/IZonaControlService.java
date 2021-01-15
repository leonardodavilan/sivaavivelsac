package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ZonaControl;

import java.util.List;

public interface IZonaControlService {

	List<ZonaControl> findAll();
	
	Page<ZonaControl> findAll(Pageable pageable);
	
	ZonaControl findById(Integer id);
	
	ZonaControl save(ZonaControl proveedor);
	
	void delete(Integer id);


}
