package pe.com.avivel.sistemas.siva.models.entity.seguridad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name="seg_menus")
public class Menu implements Serializable {

    @Id
    @Column(name="menu_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "menu_path")
    private String path;

    @Column(name = "menu_title")
    private String title;

    @Column(name = "menu_modulename")
    private String moduleName;

    @Column(name = "menu_icontype")
    private String iconType;

    @Column(name = "menu_icon")
    private String icon;

    @Column(name = "menu_class")
    private String menuClass;

    @Column(name = "menu_groupTitle")
    private Boolean groupTitle;

    @Column(name = "menu_badge")
    private String badge;

    @Column(name = "menu_badge_class")
    private String badgeClass;

    @Column(name = "menu_is_external_link")
    private int externalLink;

    @Column(name = "menu_orden")
    private int orden;

    @JsonIgnoreProperties(value={"menus", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="seg_roles_menus", joinColumns= @JoinColumn(name="menu_id"),
            inverseJoinColumns=@JoinColumn(name="rol_id"),
            uniqueConstraints= {@UniqueConstraint(columnNames= {"menu_id", "rol_id"})})
    private List<Role> role;

    @JsonIgnoreProperties(value={"submenu", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne
    @JoinColumn(name = "menu_parent_id")
    private Menu parentId;

    @OneToMany(mappedBy = "parentId")
    private List<Menu> submenu;

    private static final long serialVersionUID = 1L;
}
