package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.com.avivel.sistemas.siva.models.entity.seguridad.Menu;

import java.util.List;

public interface IMenuDao extends JpaRepository<Menu, Integer>{

    @Query(" select m from Menu m " +
            "where m.parentId is null ")
    List<Menu> findAllLevelOneMenu();
}
