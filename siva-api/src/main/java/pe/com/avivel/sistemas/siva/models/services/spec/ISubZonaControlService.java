package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SubZonaControl;

import java.util.List;

public interface ISubZonaControlService {

	List<SubZonaControl> findAll();
	
	Page<SubZonaControl> findAll(Pageable pageable);

	SubZonaControl findById(Integer id);
	
	SubZonaControl save(SubZonaControl subZonaControl);
	
	void delete(Integer id);


}
