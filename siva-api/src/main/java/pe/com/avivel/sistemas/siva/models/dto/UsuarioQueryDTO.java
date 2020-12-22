package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
import pe.com.avivel.sistemas.siva.models.entity.seguridad.Usuario;


@Data
public class UsuarioQueryDTO {
    private Integer id;

    private String usuario;

    private String email;

    @JsonIgnore
    private String contrasena;

    public static UsuarioQueryDTO getInstance(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioQueryDTO usuarioQueryDTO = new UsuarioQueryDTO();
        usuarioQueryDTO.setId(usuario.getId());
        usuarioQueryDTO.setUsuario(usuario.getUsername());
        usuarioQueryDTO.setEmail(usuario.getEmail());
        usuarioQueryDTO.setContrasena(usuario.getPassword());

        return usuarioQueryDTO;
    }

}
