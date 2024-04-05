package cespi.induccion.estacionamiento.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import cespi.induccion.estacionamiento.DTO.ErrorMessage;
import cespi.induccion.estacionamiento.DTO.RechargeDTO;
import cespi.induccion.estacionamiento.models.City;
import cespi.induccion.estacionamiento.models.Role;
import cespi.induccion.estacionamiento.models.Transaction;
import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.repositories.CityRepository;
import cespi.induccion.estacionamiento.repositories.RoleRepository;
import cespi.induccion.estacionamiento.repositories.UserRepository;

@Service
@Transactional
public class SellerService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	TransactionService transactionService;
	@Autowired
	BankAccountService bankAccountService;
	
	@EventListener(ApplicationReadyEvent.class)
	public boolean checkExistingRoles() {
		System.out.println("Chequeando existencia de vendedor");
		List<User> users = userRepository.findAll();
		Role sellerRole = roleRepository.findById((long) 2).get();
		City city = cityRepository.findById((long)1).get();
		for(User user: users) {
			if(user.getRoles().contains(sellerRole)) return true;
		}
		System.out.println("No existe ningun vendedor");
		System.out.println("Creando vendedor");
		User user = new User("vendedor", "12345", null, city);
		user.addRole(sellerRole);
		userRepository.save(user);
		System.out.println("Vendedor creado");
		return false;
	}
	
	public void recharge(User seller, RechargeDTO dto) throws Exception{
		User user;
		try {
			user = userRepository.findByCellphone(dto.getCellphone()).get();
			
		} catch (Exception e) {
			throw new Exception("El usuario no existe");
		}
		try {
			double newBalance = bankAccountService.addBalance(user, dto.getAmount());
			Transaction recarga = this.transactionService.createRecharge(dto.getAmount(), newBalance, "Recarga de saldo.");
			Transaction consumo = this.transactionService.createConsumption(dto.getAmount(), 0, "Venta de saldo");
			
			user.addTransaction(recarga);
			seller.addTransaction(consumo);			
		} catch (Exception e) {
			throw new Exception("No se pudo realizar la carga.");
		}
	}
}
