package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.entity.seguridad.Usuario;

import java.util.List;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{


	public Usuario findByUsername(String username);

	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String username);

	@SuppressWarnings(value = "all")
	@Query(value = " select u.* " +
			" from seg_usuarios u " +
			"    join seg_notificacion_usuarios nu " +
			"       on u.usuario_id = nu.usuario_id " +
			"    join seg_notificacion n " +
			"       on nu.notificacion_id = n.notificacion_id " +
			" where n.notificacion_codigo = :grupo " +
			"   and u.usuario_activado=1 ", nativeQuery = true)
	List<Usuario> findByUsuariosByGrupo(@Param("grupo") String grupo);

}
