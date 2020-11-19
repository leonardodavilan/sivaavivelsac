package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SubFamilia;

import java.util.List;

public interface ISubFamiliaService {

	public List<SubFamilia> findAll();
	
	public Page<SubFamilia> findAll(Pageable pageable);
	
	public SubFamilia findById(Integer id);
	
	public SubFamilia save(SubFamilia subFamilia);
	
	public void delete(Integer id);


}
