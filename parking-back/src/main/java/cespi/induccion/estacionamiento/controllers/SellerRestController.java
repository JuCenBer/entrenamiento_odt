package cespi.induccion.estacionamiento.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value="/seller", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellerRestController {

	
	@PostMapping(value="/recharge")
	public ResponseEntity<?> recharge(@RequestHeader("JWT") String token){
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
