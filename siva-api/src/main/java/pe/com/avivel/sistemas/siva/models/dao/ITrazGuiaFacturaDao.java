package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.GuiaFactura;

public interface ITrazGuiaFacturaDao extends JpaRepository<GuiaFactura, Integer>{

}
