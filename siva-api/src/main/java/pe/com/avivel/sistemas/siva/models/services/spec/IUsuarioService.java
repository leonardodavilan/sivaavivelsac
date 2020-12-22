package pe.com.avivel.sistemas.siva.models.services.spec;

import pe.com.avivel.sistemas.siva.models.dto.UsuarioQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.seguridad.Usuario;

import java.util.List;

public interface IUsuarioService {

	Usuario findByUsername(String username);

	List<UsuarioQueryDTO> findByUsuariosByGrupo(String grupo);

}
