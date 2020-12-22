package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;

import java.util.List;


public interface IPrdLoteDao extends JpaRepository<PrdLote, Integer>{



    @Query("select l from PrdLote l " +
            "join fetch l.galpon g " +
            "join fetch g.granja gr " +
            "where l.loteEtapa = :filtroLote "+
            "order by l.fechaIngreso, l.codigo")
    List<PrdLote> findAllByFiltro(@Param("filtroLote") String filtroLote);

    @Query(value = "select l from PrdLote l " +
            "join fetch l.galpon g " +
            "join fetch g.granja gr " +
            "where l.galpon.granja.id = :granjaId " +
            "order by l.fechaIngreso, l.codigo")
    List<PrdLote> findAllByGranjaId(@Param("granjaId") Integer granjaId);



}
