package pe.com.avivel.sistemas.siva.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.siva.models.entity.seguridad.Menu;
import pe.com.avivel.sistemas.siva.models.services.spec.IMenuService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200","*" })
@RestController
@RequestMapping("/api")
public class MenuRestController {

	@Autowired
	private IMenuService menuService;

	@GetMapping("/menu")
	public List<Menu> index() {
		return menuService.findAll();
	}

	@GetMapping("/menus/levelone")
	public List<Menu> findAllLevelOneMenu() {
		return menuService.findAllLevelOneMenu();
	}
	
	@GetMapping("/menu/page/{page}")
	public Page<Menu> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return menuService.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_SANIDAD_USER"})
	@GetMapping("/menu/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {

		Menu menu = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			menu = menuService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(menu == null) {
			response.put("mensaje", "El menu item ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Menu>(menu, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/menu")
	public ResponseEntity<?> create(@Valid @RequestBody Menu menu, BindingResult result) {

		Menu menuNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			menuNew = menuService.save(menu);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El menu item ha sido creado con éxito!");
		response.put("menu", menuNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/menu/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Menu menu, BindingResult result, @PathVariable Integer id) {

		Menu menuActual = menuService.findById(id);

		Menu menuUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (menuActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el menu item ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			menuActual.setParentId(menu.getParentId());
			menuActual.setModuleName(menu.getModuleName());
			menuActual.setGroupTitle(menu.getGroupTitle());
			menuActual.setBadge(menu.getBadge());
			menuActual.setBadgeClass(menu.getBadgeClass());
			menuActual.setExternalLink(menu.getExternalLink());
			menuActual.setIconType(menu.getIconType());
			menuActual.setIcon(menu.getIcon());
			menuActual.setMenuClass(menu.getMenuClass());
			menuActual.setPath(menu.getPath());
			menuActual.setParentId(menu.getParentId());
			menuActual.setOrden(menu.getOrden());
			menuActual.setTitle(menu.getTitle());
			menuActual.setRole(menu.getRole());
			menuActual.setSubmenu(menu.getSubmenu());

			menuUpdated = menuService.save(menuActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo cebo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El menú item ha sido actualizado con éxito!");
		response.put("menu", menuUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			Menu menu = menuService.findById(id);
			menuService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el menú item de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El menú item eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
