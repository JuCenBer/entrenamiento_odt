package cespi.induccion.estacionamiento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import cespi.induccion.estacionamiento.DTO.VehiculoDTO;
import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.services.AutomovilistaService;
import cespi.induccion.estacionamiento.services.VehiculoService;

@RestController
@CrossOrigin
@RequestMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutomovilistaRestController {

	@Autowired
	private AutomovilistaService automovilistaService;
	@Autowired
	private VehiculoService vehiculoService;
	
	@GetMapping(value="/all") //para usar de prueba
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = automovilistaService.findAllAutomovilistas();
		if(users.isEmpty()){
			 return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
		}
		 	return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}
	
	@GetMapping(value="/{id}")
	 public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		User user = null;
		try {
			user = automovilistaService.findById(id);						
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "El usuario no existe");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
	 	return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping(value="/vehicles")
	public ResponseEntity<?> getUserVehicles(@RequestHeader("jwt") String token){
		List<String> vehiculos = null;
		User user = null;
		try {
			user = automovilistaService.findByCellphone(token);
			vehiculos = automovilistaService.getVehicles(user);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "El usuario no existe");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<String>>(vehiculos, HttpStatus.OK);
	}
	
	@PostMapping(value="/start_parking")
	public ResponseEntity<?> startParking(@RequestHeader("JWT") String token, @RequestBody VehiculoDTO vehiculoDTO){
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
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
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
	public ResponseEntity<?> endParking(@RequestHeader("JWT") String token){
		//El id pertenece al automovilista que quiere finalizar el estacionamiento, y la patente es del vehiculo cuyo estacionamiento se quiere terminar.
		User user = null;
		if(automovilistaService.getUser(token) == null) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		try {
			user = automovilistaService.findByCellphone(token);
			System.out.println("automovilista encontrado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			automovilistaService.endParking(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(400, e.getMessage().toString());
			System.out.println(e.getMessage());
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/add_vehicle")
	public ResponseEntity<?> addVehicle(@RequestHeader("JWT") String token, @RequestBody VehiculoDTO vehiculoDTO){
		System.out.println(vehiculoDTO.getLicensePlate());
		User user = null;
		List<String> vehicles = null;
		if(automovilistaService.getUser(token) == null) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		try {
			user = automovilistaService.findByCellphone(token);
			System.out.println("automovilista encontrado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (vehiculoService.checkLicensePlateFormatRegex(vehiculoDTO.getLicensePlate())) {
			try {
				vehicles = automovilistaService.addVehicle(user, vehiculoDTO.getLicensePlate());			
			} catch (Exception e) {
				ErrorMessage error = new ErrorMessage(400, "No se pudo agregar el vehiculo");
				return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
			}
		} else {
			ErrorMessage error = new ErrorMessage(400, "La patente del vehiculo no cumple con ninguno de los formatos estándar.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<String>>(vehicles, HttpStatus.OK);
	}
	
	@GetMapping(value="/parking_status")
	public ResponseEntity<?> isParked(@RequestHeader("JWT") String token){
		User user = null;
		try {
			user = automovilistaService.findByCellphone(token);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(400, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		ParkingDTO parkingDTO = this.automovilistaService.isParked(user);
		return new ResponseEntity<ParkingDTO>(parkingDTO, HttpStatus.OK);
		
	}
	
	@GetMapping(value="/transactions")
	public ResponseEntity<?> getUserTransactions(@RequestHeader("JWT") String token){
		List<TransactionDTO> transacciones = null;
		User user = null;
		try {
			user = automovilistaService.findByCellphone(token);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(400, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		transacciones = automovilistaService.findTransactions(user);
		return new ResponseEntity<List<TransactionDTO>>(transacciones, HttpStatus.OK);
	}
	
}