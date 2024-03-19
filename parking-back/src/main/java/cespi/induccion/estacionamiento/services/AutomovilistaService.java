package cespi.induccion.estacionamiento.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.DTO.LoginDTO;
import cespi.induccion.estacionamiento.DTO.VehiculoDTO;
import cespi.induccion.estacionamiento.models.Automovilista;
import cespi.induccion.estacionamiento.models.Parking;
import cespi.induccion.estacionamiento.models.Transaction;
import cespi.induccion.estacionamiento.repositories.AutomovilistaRepository;
import cespi.induccion.estacionamiento.repositories.ParkingRepository;

@Service
@Transactional
public class AutomovilistaService {
	
	@Autowired
	private AutomovilistaRepository automovilistaRepository;
	@Autowired 
	private ParkingService parkingService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private BankAccountService bankAccountService;
	
	public void login(LoginDTO loginDTO) throws Exception{
		try {			
			Automovilista automovilista = this.findByCellphone(loginDTO.getCellphone());
			if(!automovilista.getPassword().equals(loginDTO.getPassword())) throw new Exception("Credenciales invalidas.");
		} catch (Exception e) {
			throw new Exception("Credenciales invalidas.");
		}
	}
	
	public Automovilista create (Automovilista automovilista) throws Exception {
		automovilistaRepository.save(automovilista);
		return automovilista;
	}
	
	public Automovilista getUser(String token) {
		return this.automovilistaRepository.findByCellphone(token).get();
	}
	
	public List<Automovilista> findAllAutomovilistas(){
		return this.automovilistaRepository.findAll();
	}
	
	public Automovilista findById(long id) throws Exception{
		try {			
			return this.automovilistaRepository.findById(id).get();
		}
		catch (Exception e) {
			throw new Exception("El automovilista no existe.");
		}
	}
	
	public Automovilista findByCellphone(String cellphone) throws Exception{
		try {
			return this.automovilistaRepository.findByCellphone(cellphone).get();
		} catch (Exception e) {
			throw new Exception("El automovilista no existe");
		}
	}
	
	public void startParking(VehiculoDTO vehiculoDTO) throws Exception{
		Automovilista automovilista = this.findById(vehiculoDTO.getId());
		System.out.println("automovilista encontrado");
		String licensePlate = vehiculoDTO.getLicensePlate();
		if(this.canPark(automovilista, licensePlate)) {
			parkingService.park(automovilista, licensePlate);
			automovilistaRepository.save(automovilista);
		}
	}

	public void endParking(VehiculoDTO vehiculoDTO) throws Exception{
		Automovilista automovilista = this.findById(vehiculoDTO.getId());
		//Chequea si el vehiculo está estacionado. Si lo está, termina el estacionamiento.
		if (automovilista.getParking() != null) { 
				double monto = parkingService.unpark(automovilista, vehiculoDTO.getLicensePlate());
				bankAccountService.substractBalance(automovilista, monto);
				Transaction consumo = transactionService.createConsumption(monto, "Pago de estacionamiento.");
				automovilista.addTransaction(consumo);
				automovilistaRepository.save(automovilista);
				System.out.println("Estacionamiento terminado. El valor del mismo es: "+ monto);
		}
		else throw new Exception("El automovilista no existe o no se encuentra estacionado");
	}
	
	public Automovilista addVehicle(long id, String licensePlate) throws Exception{
		Automovilista automovilista = this.findById(id);
		if (!automovilista.getVehiculos().contains(licensePlate)) {
			automovilista.getVehiculos().add(licensePlate);
			automovilistaRepository.save(automovilista);
			return automovilista;
		}
		else {
			throw new Exception("El automovilista ya tiene ese vehiculo");
		}
	}
	
	private boolean hasVehicle(Automovilista automovilista, String licensePlate) throws Exception{
		if (automovilista.getVehiculos().contains(licensePlate)) {
			System.out.println("El vehiculo pertenece al automovilista");
			return true;
		}
		else {
			throw new Exception ("Ese vehiculo no pertenece al automovilista");
		}
	}
	
	public List<String> getVehicles(Automovilista automovilista) throws Exception{
		return automovilista.getVehiculos();
	}
	
	private boolean hasEnoughCredit(Automovilista automovilista) {
		if (automovilista.getBankAccount().getBalance() >= automovilista.getCity().getPrice()) {
			return true;
		}
		return false;
	}
	
	private boolean canPark(Automovilista automovilista, String licensePlate) throws Exception{
		if(!automovilista.getCity().isBusinessHour(LocalDateTime.now().getHour())){
			throw new Exception("No es horario habil");
		}else if((this.hasEnoughCredit(automovilista)) && (this.hasVehicle(automovilista, licensePlate))){
			return true;
		}else {
			throw new Exception("El automovilista no tiene credito o ese vehiculo no le pertenece");
		}
	}
}
