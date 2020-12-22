package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.siva.models.dto.FiltroProgramacionDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ProgramacionVacuna;

import java.util.List;

@Repository
public interface IProgramacionDao extends JpaRepository<ProgramacionVacuna, Integer>{



    @Query("  select p from ProgramacionVacuna p " +
            "where p.prdEtapa.nombre = :#{#filtroProgramacionDTO.prdEtapa} " +
            "and p.estado=1 " +
            "order by p.numeroProgramacion.nombre, p.tiempoProgramacion.nombre, p.edadVacunacion asc")
    List<ProgramacionVacuna> findByFiltro1(@Param("filtroProgramacionDTO") FiltroProgramacionDTO filtroProgramacionDTO);

    @Query("  select p from ProgramacionVacuna p " +
            "where p.prdEtapa.nombre = :#{#filtroProgramacionDTO.prdEtapa} " +
            "and  p.numeroProgramacion.id = :#{#filtroProgramacionDTO.numProgId} " +
            "and p.estado=1")
    List<ProgramacionVacuna> findByFiltro2(@Param("filtroProgramacionDTO") FiltroProgramacionDTO filtroProgramacionDTO);


    @Query("  select p from ProgramacionVacuna p " +
            "where p.prdEtapa.nombre = :#{#filtroProgramacionDTO.prdEtapa} " +
            "and  p.numeroProgramacion.id = :#{#filtroProgramacionDTO.numProgId} " +
            "and p.tiempoProgramacion.id = :#{#filtroProgramacionDTO.tiempoProgId} " +
            "and p.estado=1")
    List<ProgramacionVacuna> findByFiltro3(@Param("filtroProgramacionDTO") FiltroProgramacionDTO filtroProgramacionDTO);


    @Query("  select p from ProgramacionVacuna p " +
            "where p.prdEtapa.nombre = :#{#filtroProgramacionDTO.prdEtapa} " +
            "and  p.numeroProgramacion.id = :#{#filtroProgramacionDTO.numProgId} " +
            "and  p.tiempoProgramacion.id = :#{#filtroProgramacionDTO.tiempoProgId} " +
            "and  p.edadVacunacion = :#{#filtroProgramacionDTO.edad} " +
            "and p.estado=1")
    List<ProgramacionVacuna> findByFiltro4(@Param("filtroProgramacionDTO") FiltroProgramacionDTO filtroProgramacionDTO);

    @Query("  select p from ProgramacionVacuna p " +
            "where p.prdEtapa.nombre = :#{#filtroProgramacionDTO.prdEtapa} " +
            "and p.numeroProgramacion.id = :#{#filtroProgramacionDTO.numProgId} " +
            "and  p.tiempoProgramacion.id = :#{#filtroProgramacionDTO.tiempoProgId} " +
            "and  p.edadVacunacion = :#{#filtroProgramacionDTO.edad} " +
            "and p.insumo.id = :#{#filtroProgramacionDTO.insumoId} " +
            "and p.estado=1")
    List<ProgramacionVacuna> findByFiltro5(@Param("filtroProgramacionDTO") FiltroProgramacionDTO filtroProgramacionDTO);


    @Query(value = "call SP_PROGRAMACION_VACUNA(:prdEtapaId, :vacNumProg)", nativeQuery = true)
    List<ProgramacionVacuna> findByEtapaNum(@Param("prdEtapaId") Integer prdEtapaId,
                                            @Param("vacNumProg") Integer vacNumProg);

}

