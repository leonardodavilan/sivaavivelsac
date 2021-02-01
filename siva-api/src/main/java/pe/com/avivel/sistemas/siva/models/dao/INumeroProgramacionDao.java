package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.entity.seguridad.Usuario;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.NumeroProgramacion;

import java.util.List;


public interface INumeroProgramacionDao extends JpaRepository<NumeroProgramacion, Integer>{

    @Query(" select max(s.codigo) as maxcodigo from NumeroProgramacion s ")
    Integer findMaxCodProg();
}
