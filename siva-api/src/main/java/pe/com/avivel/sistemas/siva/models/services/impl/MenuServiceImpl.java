package pe.com.avivel.sistemas.siva.models.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.avivel.sistemas.siva.models.dao.IMenuDao;
import pe.com.avivel.sistemas.siva.models.entity.seguridad.Menu;
import pe.com.avivel.sistemas.siva.models.services.spec.IMenuService;

import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

	@Autowired
	private IMenuDao menuDao;

	@Override
	@Transactional(readOnly = true)
	public List<Menu> findAll() {
		return (List<Menu>) menuDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Menu> findAll(Pageable pageable) {
		return menuDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Menu findById(Integer id) {
		return menuDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Menu save(Menu menu) {
		return menuDao.save(menu);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		menuDao.deleteById(id);
	}

	@Override
	public List<Menu> findAllLevelOneMenu() {
		return menuDao.findAllLevelOneMenu();
	}

}
