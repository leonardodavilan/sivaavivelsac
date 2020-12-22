package pe.com.avivel.sistemas.siva.models.entity.seguridad;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "seg_usuarios")
@Data
public class Usuario implements Serializable {

	@Id
	@Column(name="usuario_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, length = 20, name = "usuario_nombre")
	private String username;

	@Column(length = 200, name="usuario_contrasena")
	private String password;

	@Column(name="usuario_activado")
	private Boolean enabled;


	private String nombre;


	private String apellido;


	@Column(unique = true, name="usuario_email")
	private String email;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="seg_usuarios_roles", joinColumns= @JoinColumn(name="usuario_id"),
	inverseJoinColumns=@JoinColumn(name="rol_id"),
	uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id", "rol_id"})})
	private List<Role> roles;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
