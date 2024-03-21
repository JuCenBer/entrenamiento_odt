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
import cespi.induccion.estacionamiento.DTO.VehiculoDTO;
import cespi.induccion.estacionamiento.models.Automovilista;
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
	public ResponseEntity<List<Automovilista>> listAllUsers() {
		List<Automovilista> users = automovilistaService.findAllAutomovilistas();
		if(users.isEmpty()){
			 return new ResponseEntity<List<Automovilista>>(users, HttpStatus.NO_CONTENT);
		}
		 	return new ResponseEntity<List<Automovilista>>(users, HttpStatus.OK);
		}
	
	@GetMapping(value="/{id}")
	 public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		Automovilista automovilista = null;
		try {
			automovilista = automovilistaService.findById(id);						
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "El usuario no existe");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
	 	return new ResponseEntity<Automovilista>(automovilista, HttpStatus.OK);
	}
	
	@GetMapping(value="/vehicles")
	public ResponseEntity<?> getUserVehicles(@RequestHeader("jwt") String token){
		List<String> vehiculos = null;
		Automovilista automovilista = null;
		try {
			automovilista = automovilistaService.findByCellphone(token);
			vehiculos = automovilistaService.getVehicles(automovilista);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "El usuario no existe");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<String>>(vehiculos, HttpStatus.OK);
	}
	
	@PostMapping(value="/start_parking")
	public ResponseEntity<?> startParking(@RequestHeader("JWT") String token, @RequestBody VehiculoDTO vehiculoDTO){
		Automovilista automovilista = null;
		if(automovilistaService.getUser(token) == null) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		try {
			automovilista = automovilistaService.findByCellphone(token);
			System.out.println("automovilista encontrado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//El id pertenece al automovilista que quiere iniciar el estacionamiento, y la patente es del vehiculo que se quiere estacionar		
		try {
			automovilistaService.startParking(automovilista, vehiculoDTO.getLicensePlate());
			return new ResponseEntity<Void>(HttpStatus.OK);			
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(400, e.getMessage());
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/end_parking")
	public ResponseEntity<?> endParking(@RequestHeader("JWT") String token){
		//El id pertenece al automovilista que quiere finalizar el estacionamiento, y la patente es del vehiculo cuyo estacionamiento se quiere terminar.
		Automovilista automovilista = null;
		if(automovilistaService.getUser(token) == null) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		try {
			automovilista = automovilistaService.findByCellphone(token);
			System.out.println("automovilista encontrado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			automovilistaService.endParking(automovilista);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(400, e.getMessage().toString());
			System.out.println(e.getMessage());
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/{id}/add_vehicle")
	public ResponseEntity<?> addVehicle(@PathVariable("id") long id, @RequestBody VehiculoDTO vehiculoDTO){
		Automovilista automovilista = null;
		if (vehiculoService.checkLicensePlateFormatRegex(vehiculoDTO.getLicensePlate())) {
			try {
				automovilista = automovilistaService.addVehicle(id, vehiculoDTO.getLicensePlate());			
			} catch (Exception e) {
				ErrorMessage error = new ErrorMessage(400, "No se pudo agregar el vehiculo");
				return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
			}
		} else {
			ErrorMessage error = new ErrorMessage(400, "La patente del vehiculo no cumple con ninguno de los formatos estándar.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Automovilista>(automovilista, HttpStatus.OK);
	}
	
	@GetMapping(value="/parking_status")
	public ResponseEntity<?> is_parked(@RequestHeader("JWT") String token){
		Automovilista automovilista = null;
		try {
			automovilista = automovilistaService.findByCellphone(token);
		} catch (Exception e) {
			// TODO: handle exception
		}
		ParkingDTO parkingDTO = this.automovilistaService.isParked(automovilista);
		return new ResponseEntity<ParkingDTO>(parkingDTO, HttpStatus.OK);
		
	}
}