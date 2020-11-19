package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Presentacion;

public interface IPresentacionDao extends JpaRepository<Presentacion, Integer>{

}
