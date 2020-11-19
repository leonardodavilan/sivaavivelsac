package pe.com.avivel.sistemas.siva.models.entity.produccion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="prd_etapas")
public class PrdEtapa implements Serializable {

    @Id
    @Column(name="etapa_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="etapa_nombre")
    private String nombre;

    private static final long serialVersionUID = 1L;
}
