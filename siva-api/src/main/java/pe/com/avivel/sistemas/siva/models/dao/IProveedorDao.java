package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Proveedor;

public interface IProveedorDao extends JpaRepository<Proveedor, Integer>{

}
