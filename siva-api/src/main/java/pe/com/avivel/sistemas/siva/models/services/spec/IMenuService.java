package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.seguridad.Menu;

import java.util.List;

public interface IMenuService {

	 List<Menu> findAll();
	
	 Page<Menu> findAll(Pageable pageable);

	Menu findById(Integer id);

	Menu save(Menu menu);
	
	 void delete(Integer id);

	 List<Menu> findAllLevelOneMenu();

}
