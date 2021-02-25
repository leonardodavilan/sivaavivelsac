package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Audited
@Entity
@Data
@Table(name="vac_insumos_presentaciones")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class InsumoPresentacion extends Auditable<String> {

    @Id
    @Column(name="insumo_presentacion_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max=45, message="el tamaño tiene que ser como máximo 45 dígitos")
    @Column(name = "insumo_presentacion_codigo_sap")
    private String codigoSap;

    @Size(max=45, message="el tamaño tiene que ser como máximo 45 dígitos")
    @Column(name = "insumo_presentacion_codigo_ref")
    private String codigoRef;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @ManyToOne(targetEntity = Presentacion.class)
    @JoinColumn(name = "presentacion_id")
    private Presentacion presentacion;

    @ManyToOne(targetEntity = UnidadMedida.class)
    @JoinColumn(name = "unidad_medida_id")
    private UnidadMedida unidadMedida;

    @Column(name="insumo_presentacion_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

}
