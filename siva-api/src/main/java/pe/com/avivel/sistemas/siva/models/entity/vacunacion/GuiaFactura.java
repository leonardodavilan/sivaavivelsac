package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Audited
@Data
@Entity
@Table(name = "traz_guiasfacturas")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class GuiaFactura  extends Auditable<String> {

    @Id
    @Column(name="guiafactura_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max=15, message="el tamaño tiene que ser como máximo 15 dígitos")
    @Column(name = "guiafactura_numero")
    private String numero;

    @Column(name="guiafactura_estado")
    private int estado;

    private static final long serialVersionUID = 1L;
}
