package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdEtapa;

import java.util.List;

public interface IPrdEtapaService {

	 List<PrdEtapa> findAll();
	
	 Page<PrdEtapa> findAll(Pageable pageable);

	 PrdEtapa findById(Integer id);

	 PrdEtapa save(PrdEtapa prdEtapa);
	
	 void delete(Integer id);


}
