package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoPresentacion;

import java.util.List;

public interface IInsumoPresentacionDao extends JpaRepository<InsumoPresentacion, Integer>{

    @Query("select i.insumoProveedor.insumoPresentacion from StockInsumo i "+
            "where (coalesce(:#{#granjaId}, 0) = 0 or i.prdGranja.id = :#{#granjaId}) " +
            "and i.cantidad>0")
    List<InsumoPresentacion> findAllByGranjaFromStock(@Param("granjaId") Integer granjaId);

}
