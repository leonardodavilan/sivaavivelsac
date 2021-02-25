package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Audited
@Data
@Entity
@Table(name="vac_proveedores")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Proveedor extends Auditable<String> {

	@Id
	@Column(name="proveedor_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotEmpty(message ="no puede estar vacio")
	@Size(min=4, max=45, message="el tamaño tiene que estar entre 4 y 45")
	@Column(nullable=false, name = "proveedor_razon_social")
	private String razonSocial;

	@NotEmpty(message ="no puede estar vacio")
	@Column(name="proveedor_ruc")
	private String ruc;

	@Column(name="proveedor_estado")
	private int estado;


	private static final long serialVersionUID = 1L;


	public Proveedor setId(Integer id) {
		this.id = id;
		return this;
	}
	public Proveedor setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
		return this;
	}
}
