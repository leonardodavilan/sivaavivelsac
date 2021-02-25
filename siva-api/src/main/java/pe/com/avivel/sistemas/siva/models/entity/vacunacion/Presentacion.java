package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Audited
@Entity
@Data
@Table(name="vac_presentaciones")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Presentacion extends Auditable<String> {

    @Id
    @Column(name="presentacion_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max=80, message="el tamaño tiene que ser como máximo 80 dígitos")
    @Column(name = "presentacion_nombre")
    private String nombre;


    @Column(name = "presentacion_cantidad")
    private BigDecimal cantidad;


    @Column(name="presentacion_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

}
