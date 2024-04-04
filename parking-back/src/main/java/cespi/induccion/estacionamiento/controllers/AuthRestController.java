package cespi.induccion.estacionamiento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cespi.induccion.estacionamiento.DTO.UserDTO;
import cespi.induccion.estacionamiento.DTO.ErrorMessage;
import cespi.induccion.estacionamiento.DTO.LoginDTO;
import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.models.Permission;
import cespi.induccion.estacionamiento.services.AuthorizationService;
import cespi.induccion.estacionamiento.services.AutomovilistaService;

@RestController
@CrossOrigin
@RequestMapping(value="/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthRestController {
	
	@Autowired
	private AutomovilistaService automovilistaService;
	@Autowired
	private AuthorizationService authorizationService;
	
	@PostMapping(value="/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		if (user.getCellphone().isBlank() || user.getPassword().isBlank()) {
			ErrorMessage error = new ErrorMessage(400, "Debe ingresar todos los campos.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		try{
			this.automovilistaService.findByCellphone(user.getCellphone());			
			ErrorMessage error = new ErrorMessage(409, "Ese numero de celular ya se encuentra registrado.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.CONFLICT);				
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	try {
			UserDTO dto = automovilistaService.register(user);
			dto.setToken(user.getCellphone());
			return new ResponseEntity<UserDTO>(dto, HttpStatus.CREATED);
		}
		catch (Exception e) {
			ErrorMessage error = new ErrorMessage(409, "Ha ocurrido un error, inténtelo nuevamente.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.CONFLICT);
		}
		//Se podria mejorar el manejo de los bloques try catch.
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
		if(loginDTO.getCellphone().isBlank() || loginDTO.getPassword().isBlank()) {
			ErrorMessage error = new ErrorMessage(400, "Complete todos los campos.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		try {
			UserDTO dto = automovilistaService.login(loginDTO);
			dto.setToken(loginDTO.getCellphone());
			return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(401, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping(value="/has_permission")
	public ResponseEntity<?> hasPermission(@RequestHeader("JWT") String token, @RequestBody Permission permission){
		boolean hasPermission = false;
		User user = null;
		if(automovilistaService.getUser(token) == null) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		try {
			user = automovilistaService.findByCellphone(token);
			System.out.println("automovilista encontrado");
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
		hasPermission = authorizationService.hasPermission(user, permission);
		return new ResponseEntity<Boolean>(hasPermission, HttpStatus.OK);
	}
	
//	public ResponseEntity<?> logout(@RequestBody LoginDTO loginDTO){
//		Automovilista automovilista = null;
//		try {
//		} catch (Exception e) {
//			
//		}
//	}

}
