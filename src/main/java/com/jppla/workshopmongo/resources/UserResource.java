package com.jppla.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jppla.workshopmongo.domain.User;
import com.jppla.workshopmongo.dto.UserDTO;
import com.jppla.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll() {
		
		List<User> list = service.findAll(); // buscar do banco os usuários e guardar na lista
		List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());	
		
		return ResponseEntity.ok().body(listDTO); // no corpo da resposta terá a lista
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public <T> ResponseEntity<UserDTO> findById(@PathVariable String id) {
		
		User obj = service.findById(id);
		
		return ResponseEntity.ok().body(new UserDTO(obj)); // no corpo da resposta terá a lista
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public <T> ResponseEntity<Void> insert(@RequestBody UserDTO objDTO){
		
		User obj = service.fromDTO(objDTO);
		obj  = service.insert(obj); // inserindo
		
		// pega o endereço do novo objeto que foi inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		// retorna o código 201 que responde ao criar novo recurso
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public <T> ResponseEntity<Void> delete(@PathVariable String id) {
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public <T> ResponseEntity<Void> update(@RequestBody UserDTO objDTO, @PathVariable String id){
		
		User obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj  = service.update(obj); // inserindo
		
		return ResponseEntity.noContent().build();
	}
}
