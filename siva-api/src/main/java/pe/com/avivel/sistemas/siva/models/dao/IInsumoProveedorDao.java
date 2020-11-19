package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoProveedor;

import java.util.List;

public interface IInsumoProveedorDao extends JpaRepository<InsumoProveedor, Long>{


    @Query(" select ip from InsumoProveedor ip " +
            "where (coalesce(:#{#insumoPresentacionId}, '') = '' or ip.insumoPresentacion.id = :#{#insumoPresentacionId}) "
            )
    List<InsumoProveedor> findAllByInsumoPresentacion(@Param("insumoPresentacionId") Integer insumoPresentacionId  );

}
