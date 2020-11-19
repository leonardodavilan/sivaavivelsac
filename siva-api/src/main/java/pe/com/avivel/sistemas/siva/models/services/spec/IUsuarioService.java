package pe.com.avivel.sistemas.siva.models.services.spec;

import pe.com.avivel.sistemas.siva.models.entity.seguridad.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
