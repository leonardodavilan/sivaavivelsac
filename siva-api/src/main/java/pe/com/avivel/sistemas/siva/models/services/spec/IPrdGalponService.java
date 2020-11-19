package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGalpon;

import java.util.List;

public interface IPrdGalponService {

	 List<PrdGalpon> findAll();
	
	 Page<PrdGalpon> findAll(Pageable pageable);

	PrdGalpon findById(Integer id);


}
