package pe.com.avivel.sistemas.siva.util;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.UsuarioRevisionEntity;

public class EntityRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        UsuarioRevisionEntity usuarioRevisionEntity = (UsuarioRevisionEntity) revisionEntity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        usuarioRevisionEntity.setUsername(authentication.getName());
    }

}
