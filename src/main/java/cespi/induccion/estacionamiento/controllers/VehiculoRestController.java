package cespi.induccion.estacionamiento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cespi.induccion.estacionamiento.DTO.VehiculoDTO;
import cespi.induccion.estacionamiento.services.VehiculoService;

@RestController
@RequestMapping(value="/vehicles")
public class VehiculoRestController {

	@Autowired
	VehiculoService vehiculoService;
	
	@PostMapping(value="/licensePlate")
	public ResponseEntity<Boolean> checkLicensePlateFormat(@RequestBody VehiculoDTO vehiculoDTO){
		boolean checked = vehiculoService.checkLicensePlateFormatRegex(vehiculoDTO.getLicensePlate());
		return new ResponseEntity<Boolean>(checked, HttpStatus.OK);
	}
	
	
}
