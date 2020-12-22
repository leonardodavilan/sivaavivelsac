package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.LoteSerie;

import java.util.List;


public interface ITrazLoteSerieDao extends JpaRepository<LoteSerie, Integer>{


    @Query("select distinct i.loteSerie from StockInsumo i "+
            "where i.insumoProveedor.insumoPresentacion.insumo.subFamilia.familia.id = :#{#familiaId} ")
    List<LoteSerie> findAllByFamiliaFromStock(@Param("familiaId") Integer familiaId);


    @Query("select i.loteSerie from StockInsumo i "+
            "where (coalesce(:#{#insumoId}, 0) = 0 or i.insumoProveedor.insumoPresentacion.insumo.id = :#{#insumoId}) " +
            "and i.cantidad>0")
    List<LoteSerie> findAllByIsumoFromStock(@Param("insumoId") Integer insumoId);

}
