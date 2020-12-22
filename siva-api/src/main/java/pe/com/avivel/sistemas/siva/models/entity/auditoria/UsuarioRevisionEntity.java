package pe.com.avivel.sistemas.siva.models.entity.auditoria;

import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import pe.com.avivel.sistemas.siva.util.EntityRevisionListener;

import javax.persistence.*;
import java.beans.Transient;
import java.util.Date;

@Entity
@Table(name = "revision_aud", catalog = "db_vacuna_aud")
@RevisionEntity(EntityRevisionListener.class)
@Data
public class UsuarioRevisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "revision_id")
    private int id;

    @RevisionTimestamp
    @Column(name = "revision_timestamp")
    private long timestamp;

    @Column(name = "revision_usuario", length = 30)
    private String username;

    @Transient
    public Date getRevisionDate() {
        return new Date(this.timestamp);
    }
}
