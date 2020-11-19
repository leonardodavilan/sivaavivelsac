package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;


public interface IPrdGranjaDao extends JpaRepository<PrdGranja, Integer>{

}
