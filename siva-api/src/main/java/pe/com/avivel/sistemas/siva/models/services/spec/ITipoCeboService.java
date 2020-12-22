package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.roedor.TipoCebo;

import java.util.List;

public interface ITipoCeboService {

	 List<TipoCebo> findAll();
	
	 Page<TipoCebo> findAll(Pageable pageable);

	TipoCebo findById(Integer id);

	TipoCebo save(TipoCebo tipoCebo);
	
	 void delete(Integer id);


}
