package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Data
@Entity
@Table(name = "traz_guiasfacturas")
public class GuiaFactura {

    @Id
    @Column(name="guiafactura_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Size(max=15, message="el tamaño tiene que ser como máximo 15 dígitos")
    @Column(name = "guiafactura_numero")
    private String numero;

    @Column(name="guiafactura_estado")
    private int estado;

    private static final long serialVersionUID = 1L;
}
