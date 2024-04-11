package cespi.induccion.estacionamiento.controllers;


import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cespi.induccion.estacionamiento.DTO.ErrorMessage;
import cespi.induccion.estacionamiento.DTO.RechargeDTO;
import cespi.induccion.estacionamiento.models.Permission;
import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.repositories.PermissionRepository;
import cespi.induccion.estacionamiento.services.AuthorizationService;
import cespi.induccion.estacionamiento.services.AutomovilistaService;
import cespi.induccion.estacionamiento.services.SellerService;

@RestController
@CrossOrigin
@RequestMapping(value="/seller", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellerRestController {

	@Autowired
	AutomovilistaService automovilistaService;
	@Autowired
	AuthorizationService authorizationService;
	@Autowired
	SellerService sellerService;
	@Autowired
	PermissionRepository permissionRepository;
	
	@PostMapping(value="/recharge")
	public ResponseEntity<?> recharge(@RequestHeader("Authorization") String token, @RequestBody RechargeDTO rechargeDTO){
		if(rechargeDTO.getAmount() < 100) {
			ErrorMessage error = new ErrorMessage(400, "El monto debe ser minimo de $100");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
		}
		User seller;
		String username = this.authorizationService.getUser(token);
		try {
			seller = automovilistaService.findByCellphone(username);
			System.out.println("Usuario encontrado");
		} catch (Exception e) {
			ErrorMessage error = new ErrorMessage(404, "Credenciales invalidas");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
		}
		Permission sellPermission = this.permissionRepository.findByPermission("Sell").get();
		if (this.authorizationService.hasPermission(seller, sellPermission)) {
			try {
				this.sellerService.recharge(seller, rechargeDTO);
				return new ResponseEntity<Void>(HttpStatus.OK);
			} catch (Exception e) {
				ErrorMessage error = new ErrorMessage(400, e.getMessage());
				return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
			}
		}
		else {
			ErrorMessage error = new ErrorMessage(401, "No tiene permiso para hacer eso.");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
		}
	}
	
	
}
