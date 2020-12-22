package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.roedor.AreaCaptura;

import java.util.List;

public interface IAreaCapturaService {

	 List<AreaCaptura> findAll();
	
	 Page<AreaCaptura> findAll(Pageable pageable);

	AreaCaptura findById(Integer id);

	AreaCaptura save(AreaCaptura areaCaptura);
	
	 void delete(Integer id);


}
