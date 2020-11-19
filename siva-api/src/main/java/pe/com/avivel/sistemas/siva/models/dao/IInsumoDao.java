package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IInsumoDao extends JpaRepository<Insumo, Integer>{


    @Query(value = "call SP_INSERT_INSUMO_MULTIPLE(:insumo_descripcion, :subFamilia_id)", nativeQuery = true)
    Integer saveInsumoMultiple(@Param("insumo_descripcion") String insumo_descripcion, @Param("subFamilia_id") Integer subFamilia_id);

    @Query(value = "call SP_INSERT_INSUMO_DETALLADO(:insumo_descripcion, :subFamilia_id,:codigoRef, :codigoSap, :presentacionId, :unidadMedidaId,:proveedorID, :precio,:monedaID)", nativeQuery = true)
    Integer saveInsumoDetallado(@Param("insumo_descripcion") String insumo_descripcion, @Param("subFamilia_id") Integer subFamilia_id,
                                @Param("codigoRef") String codigoRef, @Param("codigoSap") String codigoSap, @Param("presentacionId") Integer presentacionId, @Param("unidadMedidaId") Integer unidadMedidaId,
                                @Param("proveedorID") Integer proveedorID, @Param("precio") BigDecimal precio, @Param("monedaID") Integer monedaID);



    @Query("select i from Insumo i "+
            "where i.subFamilia.familia.id = :#{#tipoInsumoId}")
    List<Insumo> findAllByTipo(@Param("tipoInsumoId") Integer tipoInsumoId);


}

