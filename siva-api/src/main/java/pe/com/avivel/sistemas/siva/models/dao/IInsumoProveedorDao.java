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

    @Query(" select ip from InsumoProveedor ip " +
            "where (coalesce(:#{#monedaId}, 0) = 0 or ip.moneda.id = :#{#monedaId}) "
    )
    List<InsumoProveedor> findAllByInsumoPresentacionByMoneda(@Param("monedaId") Integer monedaId );

    @Query("select i from InsumoProveedor i "+
            "where (coalesce(:#{#subFamiliaId}, 0) = 0 or i.insumoPresentacion.insumo.subFamilia.id = :#{#subFamiliaId}) ")
    List<InsumoProveedor> findAllBySubFamilia(@Param("subFamiliaId") Integer subFamiliaId);


}
