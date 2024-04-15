package cespi.induccion.estacionamiento.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.DTO.UserDTO;
import cespi.induccion.estacionamiento.DTO.LoginDTO;
import cespi.induccion.estacionamiento.DTO.ParkingDTO;
import cespi.induccion.estacionamiento.DTO.TransactionDTO;
import cespi.induccion.estacionamiento.DTO.VehiculoDTO;
import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.models.BankAccount;
import cespi.induccion.estacionamiento.models.City;
import cespi.induccion.estacionamiento.models.Parking;
import cespi.induccion.estacionamiento.models.Role;
import cespi.induccion.estacionamiento.models.Transaction;
import cespi.induccion.estacionamiento.repositories.UserRepository;
import cespi.induccion.estacionamiento.repositories.ParkingRepository;
import cespi.induccion.estacionamiento.repositories.RoleRepository;

@Service
@Transactional
public class AutomovilistaService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private ParkingService parkingService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private CityService cityService;
	@Autowired
	private RoleRepository roleRepository;
	
	public UserDTO login(LoginDTO loginDTO) throws Exception{
		try {			
			User user = this.findByCellphone(loginDTO.getCellphone());
			UserDTO dto = user.getDTO();
			System.out.println(user.getPassword());
			System.out.println(loginDTO.getPassword());
			if(!user.getPassword().equals(loginDTO.getPassword())) throw new Exception("Credenciales invalidas.");
			else return dto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception("Credenciales invalidas.");
		}
	}
	
	public UserDTO register(User user) throws Exception {
		UserDTO dto;
		Role automovilistaRole = roleRepository.findByRole("Automovilista").get();
		BankAccount account = this.bankAccountService.create();
		City city = this.cityService.getCity();
		user.setBankAccount(account);
		user.setCity(city);
		user.addRole(automovilistaRole);
		user = this.create(user);
		dto = user.getDTO();
		return dto;
	}
	
	public User create (User user) throws Exception {
		userRepository.save(user);
		return user;
	}
	
	public User getUser(String token) {
		return this.userRepository.findByCellphone(token).get();
	}
	
	public List<User> findAllAutomovilistas(){
		return this.userRepository.findAll();
	}
	
	public User findById(long id) throws Exception{
		try {			
			return this.userRepository.findById(id).get();
		}
		catch (Exception e) {
			throw new Exception("El automovilista no existe.");
		}
	}
	
	public User findByCellphone(String cellphone) throws Exception{
		try {
			return this.userRepository.findByCellphone(cellphone).get();
		} catch (Exception e) {
			throw new Exception("El automovilista no existe");
		}
	}
	
	public void startParking(User user, String vehiculoDTO) throws Exception{
		String licensePlate = vehiculoDTO;
		System.out.println(licensePlate);
		if(this.canPark(user, licensePlate)) {
			parkingService.park(user, licensePlate);
			userRepository.save(user);
		}
	}

	public UserDTO endParking(User user) throws Exception{
		//Chequea si el vehiculo está estacionado. Si lo está, termina el estacionamiento.
		if (user.getParking() != null) { 
				double monto = parkingService.unpark(user);
				double newBalance = bankAccountService.substractBalance(user, monto);
				Transaction consumo = transactionService.createConsumption(monto, newBalance, "Pago de estacionamiento.");
				user.addTransaction(consumo);
				userRepository.save(user);
				System.out.println("Estacionamiento terminado. El valor del mismo es: "+ monto);
				return user.getDTO();
		}
		else throw new Exception("El automovilista no existe o no se encuentra estacionado");
	}
	
	public List<String> addVehicle(User user, String licensePlate) throws Exception{
		if (!user.getVehiculos().contains(licensePlate)) {
			user.getVehiculos().add(licensePlate);
			userRepository.save(user);
			return this.getVehicles(user);
		}
		else {
			throw new Exception("El automovilista ya tiene ese vehiculo");
		}
	}
	
	private boolean hasVehicle(User user, String licensePlate) throws Exception{
		if (user.getVehiculos().contains(licensePlate)) {
			System.out.println("El vehiculo pertenece al automovilista");
			return true;
		}
		else return false;
	}
	
	public List<String> getVehicles(User user) throws Exception{
		return user.getVehiculos();
	}
	
	private boolean hasEnoughCredit(User user) {
		if (user.getBankAccount().getBalance() >= user.getCity().getPrice()) {
			return true;
		}
		return false;
	}
	
	private boolean canPark(User user, String licensePlate) throws Exception{
		if(!user.getCity().isBusinessHour(LocalDateTime.now().getHour())) throw new Exception("No es horario habil");
		if (!this.hasEnoughCredit(user)) throw new Exception("El automovilista no tiene credito suficiente");
		if(!this.hasVehicle(user, licensePlate)) throw new Exception("Ese vehiculo no le pertenece al automovilista");
		return true;
	}
	
	public ParkingDTO isParked(User user) {
		if(user.getParking() == null) {
			return new ParkingDTO(false, "");
			
		}
		else return new ParkingDTO(true, user.getParking().getLicensePlate());
	}
	
	public List<TransactionDTO> findTransactions(User user){
		List<Transaction> transacciones = user.getTransactions();
		List<TransactionDTO> transaccionesDTO = null;
		transaccionesDTO = transactionService.getDTOs(transacciones);
		return transaccionesDTO;
	}
	
	@Scheduled(cron = "0 0 20 * * MON-FRI")
	public void endAllUsersParking() {
		List<User> users = this.findAllAutomovilistas();
		for (User user: users) {
			if(this.isParked(user).isParked()) {				
				try {
					this.endParking(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
