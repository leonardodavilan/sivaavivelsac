package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IInsumoPresentacionDao;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoPresentacion;
import pe.com.avivel.sistemas.siva.models.services.spec.IInsumoPresentacionService;

import java.util.List;

@Service
public class InsumoPresentacionServiceImpl implements IInsumoPresentacionService {

    @Autowired
    private IInsumoPresentacionDao insumoPresentacionDao;

    @Override
    @Transactional(readOnly = true)
    public List<InsumoPresentacion> findAll() {
        return (List<InsumoPresentacion>) insumoPresentacionDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsumoPresentacion> findAll(Pageable pageable) {
        return insumoPresentacionDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public InsumoPresentacion findById(Integer id) { return insumoPresentacionDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public InsumoPresentacion save(InsumoPresentacion insumoProveedor) {
        return insumoPresentacionDao.save(insumoProveedor);
    }

    @Override
    public void delete(Long id) {

    }
}
