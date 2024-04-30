package cespi.induccion.estacionamiento.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cespi.induccion.estacionamiento.DTO.ErrorMessage;
import cespi.induccion.estacionamiento.DTO.ParkingDTO;
import cespi.induccion.estacionamiento.DTO.TransactionDTO;
import cespi.induccion.estacionamiento.DTO.UserDTO;
import cespi.induccion.estacionamiento.DTO.VehiculoDTO;
import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.services.AuthorizationService;
import cespi.induccion.estacionamiento.services.AutomovilistaService;
import cespi.induccion.estacionamiento.services.CityService;
import cespi.induccion.estacionamiento.services.ImportService;
import cespi.induccion.estacionamiento.services.VehiculoService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutomovilistaRestController {

	@Autowired
	private AutomovilistaService automovilistaService;
	@Autowired
	private VehiculoService vehiculoService;
	@Autowired
	private CityService cityService;
	@Autowired
	private AuthorizationService authorizationService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ImportService importService;
	
	@GetMapping(value="/all") //para usar de prueba
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = automovilistaService.findAllAutomovilistas();
		if(users.isEmpty()){
			 return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
		}
		 	return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping(value="/user")
	 public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
		User user;
		String username = this.authorizationService.getUser(token);
		UserDTO dto = null;
		try {
			user = automovilistaService.findByCellphone(username);
			dto = user.getDTO();
			System.out.println("automovilista encontrado");	
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "El usuario no existe");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
	 	return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value="/vehicles")
	public ResponseEntity<?> getUserVehicles(@RequestHeader("Authorization") String token){
		List<String> vehiculos = null;
		User user;
		String username = this.authorizationService.getUser(token);
		try {
			user = automovilistaService.findByCellphone(username);
			vehiculos = automovilistaService.getVehicles(user);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(401, "El usuario no existe");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<List<String>>(vehiculos, HttpStatus.OK);
	}
	
	@PostMapping(value="/start_parking")
	public ResponseEntity<?> startParking(@RequestHeader("Authorization") String token, @RequestBody VehiculoDTO vehiculoDTO){
		User user = null;
		String username = this.authorizationService.getUser(token);
		if(!this.cityService.esDiaHabil(LocalDate.now())) {
			ErrorMessage error = new ErrorMessage(400, "Hoy no es dia habil.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		try {
			user = automovilistaService.findByCellphone(username);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(401, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
		//El id pertenece al automovilista que quiere iniciar el estacionamiento, y la patente es del vehiculo que se quiere estacionar		
		try {
			automovilistaService.startParking(user, vehiculoDTO.getLicensePlate());
			return new ResponseEntity<Void>(HttpStatus.OK);			
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(400, e.getMessage());
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/end_parking")
	public ResponseEntity<?> endParking(@RequestHeader("Authorization") String token){
		//El id pertenece al automovilista que quiere finalizar el estacionamiento, y la patente es del vehiculo cuyo estacionamiento se quiere terminar.
		User user = null;
		String username = this.authorizationService.getUser(token);
		try {
			user = automovilistaService.findByCellphone(username);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(401, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
		try {
			UserDTO dto = automovilistaService.endParking(user);
			return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(400, e.getMessage().toString());
			System.out.println(e.getMessage());
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/add_vehicle")
	public ResponseEntity<?> addVehicle(@RequestHeader("Authorization") String token, @RequestBody VehiculoDTO vehiculoDTO){
		System.out.println(vehiculoDTO.getLicensePlate());
		User user = null;
		String username = this.authorizationService.getUser(token);
		List<String> vehicles = null;
		try {
			user = automovilistaService.findByCellphone(username);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(401, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
		if (vehiculoService.checkLicensePlateFormatRegex(vehiculoDTO.getLicensePlate())) {
			try {
				vehicles = automovilistaService.addVehicle(user, vehiculoDTO.getLicensePlate());			
			} catch (Exception e) {
				ErrorMessage error = new ErrorMessage(400, e.getMessage());
				return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
			}
		} else {
			String plate = messageSource.getMessage("plate.message", null, new Locale(this.importService.getRegion()));
			ErrorMessage error = new ErrorMessage(400, "La "+ plate +" del vehiculo no cumple con ninguno de los formatos estándar.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<String>>(vehicles, HttpStatus.OK);
	}
	
	@GetMapping(value="/parking_status")
	public ResponseEntity<?> isParked(@RequestHeader("Authorization") String token){
		User user = null;
		String username = this.authorizationService.getUser(token);
		try {
			user = automovilistaService.findByCellphone(username);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(401, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
		ParkingDTO parkingDTO = this.automovilistaService.isParked(user);
		return new ResponseEntity<ParkingDTO>(parkingDTO, HttpStatus.OK);
		
	}
	
	@GetMapping(value="/transactions")
	public ResponseEntity<?> getUserTransactions(@RequestHeader("Authorization") String token){
		List<TransactionDTO> transacciones = null;
		User user = null;
		String username = this.authorizationService.getUser(token);
		try {
			user = automovilistaService.findByCellphone(username);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(401, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
		transacciones = automovilistaService.findTransactions(user);
		return new ResponseEntity<List<TransactionDTO>>(transacciones, HttpStatus.OK);
	}
	
}