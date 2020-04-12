package com.jppla.workshopmongo.resources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
