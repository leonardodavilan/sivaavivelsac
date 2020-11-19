package pe.com.avivel.sistemas.siva.models.entity.seguridad;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="seg_menus")
public class Menu implements Serializable {

    @Id
    @Column(name="menu_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_path")
    private String path;

    @Column(name = "menu_title")
    private String title;

    @Column(name = "menu_icon")
    private String icon;

    @Column(name = "menu_badge")
    private String badge;

    @Column(name = "menu_badge_class")
    private String bagdeClass;

    @Column(name = "menu_is_external_link")
    private int externalLink;

    @Column(name = "menu_orden")
    private int orden;

    @Column(name = "menu_parent_id")
    private Menu parentId;

    private static final long serialVersionUID = 1L;
}
