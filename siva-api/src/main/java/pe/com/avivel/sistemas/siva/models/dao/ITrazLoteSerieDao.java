package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.LoteSerie;


public interface ITrazLoteSerieDao extends JpaRepository<LoteSerie, Integer>{

}
