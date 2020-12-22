package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.StockInsumo;

import java.util.List;


public interface IStockInsumoDao extends JpaRepository<StockInsumo, Integer>{


    @Query(value = "select si from StockInsumo si " +
            "join fetch si.prdGranja g " +
            "where (coalesce(:#{#granjaId}, 0) = 0 or si.prdGranja.id = :#{#granjaId}) " +
            "and si.cantidad <> 0")
    List<StockInsumo> findAllByGranjaId(@Param("granjaId") Integer granjaId);


}
