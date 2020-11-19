package pe.com.avivel.sistemas.siva.models.entity.seguridad;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name="seg_roles")
public class Role implements Serializable{

	@Id
	@Column(name="rol_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "rol_nombre")
	private String nombre;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="seg_role_menus", joinColumns= @JoinColumn(name="rol_id"),
			inverseJoinColumns=@JoinColumn(name="menu_id"),
			uniqueConstraints= {@UniqueConstraint(columnNames= {"menu_id", "rol_id"})})
	private List<Menu> menus;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
