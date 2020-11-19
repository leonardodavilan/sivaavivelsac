package pe.com.avivel.sistemas.siva.models.services.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.GuiaFactura;

import java.util.List;

public interface IGuiaFacturaService {

	 List<GuiaFactura> findAll();

	 Page<GuiaFactura> findAll(Pageable pageable);

	 GuiaFactura findById(Integer id);

	 GuiaFactura save(GuiaFactura guiaFactura);

	 void delete(Integer id);


}
