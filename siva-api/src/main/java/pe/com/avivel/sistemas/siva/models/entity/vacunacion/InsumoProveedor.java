package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="vac_insumos_proveedores")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class InsumoProveedor implements Serializable {


    @Id
    @Column(name="insumo_proveedor_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = InsumoPresentacion.class)
    @JoinColumn(name = "insumo_presentacion_id")
    private InsumoPresentacion insumoPresentacion;

    @ManyToOne(targetEntity = Proveedor.class)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @Column(name = "insumo_proveedor_precio")
    private BigDecimal precio;

    @ManyToOne(targetEntity = Moneda.class)
    @JoinColumn(name = "moneda_id")
    private Moneda moneda;

    @Column(name="insumo_proveedor_estado")
    private int estado;

    private static final long serialVersionUID = 2L;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InsumoPresentacion getInsumoPresentacion() {
        return insumoPresentacion;
    }

    public void setInsumoPresentacion(InsumoPresentacion insumoPresentacion) {
        this.insumoPresentacion = insumoPresentacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
