package cespi.induccion.estacionamiento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cespi.induccion.estacionamiento.DTO.AutomovilistaDTO;
import cespi.induccion.estacionamiento.DTO.ErrorMessage;
import cespi.induccion.estacionamiento.DTO.LoginDTO;
import cespi.induccion.estacionamiento.models.Automovilista;
import cespi.induccion.estacionamiento.services.AutomovilistaService;

@RestController
@CrossOrigin
@RequestMapping(value="/login")
public class AuthRestController {
	
	@Autowired
	private AutomovilistaService automovilistaService;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
		Automovilista automovilista = null;
		try {
			automovilista = automovilistaService.findByCellphone(loginDTO.getCellphone());			
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		AutomovilistaDTO automovilistaDTO = new AutomovilistaDTO();
		automovilistaDTO.getDTO(automovilista);
		return new ResponseEntity<AutomovilistaDTO>(automovilistaDTO, HttpStatus.OK);
	}

}
