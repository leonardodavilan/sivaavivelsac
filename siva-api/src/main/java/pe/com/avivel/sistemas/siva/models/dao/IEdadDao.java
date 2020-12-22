package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.avivel.sistemas.siva.models.entity.roedor.Edad;


public interface IEdadDao extends JpaRepository<Edad, Integer>{

}
