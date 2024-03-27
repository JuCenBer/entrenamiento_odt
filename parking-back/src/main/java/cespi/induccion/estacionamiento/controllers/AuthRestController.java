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

import cespi.induccion.estacionamiento.DTO.AutomovilistaDTO;
import cespi.induccion.estacionamiento.DTO.ErrorMessage;
import cespi.induccion.estacionamiento.DTO.LoginDTO;
import cespi.induccion.estacionamiento.models.Automovilista;
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
	public ResponseEntity<?> register(@RequestBody Automovilista automovilista){
		if (automovilista.getCellphone().isBlank() || automovilista.getPassword().isBlank()) {
			ErrorMessage error = new ErrorMessage(400, "Debe ingresar todos los campos.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		try{
			this.automovilistaService.findByCellphone(automovilista.getCellphone());			
			ErrorMessage error = new ErrorMessage(409, "Ese numero de celular ya se encuentra registrado.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.CONFLICT);				
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			automovilistaService.register(automovilista);
			String token = "{\"JWT\": \""+ automovilista.getCellphone()+"\"}";
			return new ResponseEntity<String>(token, HttpStatus.CREATED);
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
			automovilistaService.login(loginDTO);
			String token = "{\"JWT\": \""+ loginDTO.getCellphone()+"\"}";
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(401, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping(value="/has_permission")
	public ResponseEntity<?> hasPermission(@RequestHeader("JWT") String token, @RequestBody Permission permission){
		boolean hasPermission = false;
		Automovilista automovilista = null;
		if(automovilistaService.getUser(token) == null) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		try {
			automovilista = automovilistaService.findByCellphone(token);
			System.out.println("automovilista encontrado");
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
		hasPermission = authorizationService.hasPermission(automovilista, permission);
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
