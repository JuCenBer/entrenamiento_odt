package cespi.induccion.estacionamiento.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.DTO.VehiculoDTO;
import cespi.induccion.estacionamiento.models.Automovilista;
import cespi.induccion.estacionamiento.models.Parking;
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
	private ParkingRepository parkingRepository;
	
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
			return this.automovilistaRepository.findByCellphone(cellphone);
		} catch (Exception e) {
			throw new Exception("El automovilista no existe");
		}
	}
	
	public void startParking(VehiculoDTO vehiculoDTO) throws Exception{
		Automovilista automovilista = this.findById(vehiculoDTO.getId());
		String licensePlate = vehiculoDTO.getLicensePlate();
		//Primero chequea que sea horario habil
		if(automovilista.getCity().isBusinessHour(LocalDateTime.now().getHour())) {
			//Chequea si el vehiculo está estacionado. Si lo está, no lo estaciona
			if (!parkingService.isParked(licensePlate) && automovilista.getVehiculos().contains(licensePlate)) {
				if (automovilista != null && automovilista.getParking() == null) {
					System.out.println(automovilista.getParking() == null);
					automovilista.start(licensePlate);
					System.out.println("Estacionamiento iniciado");
					parkingRepository.save(automovilista.getParking());
					automovilistaRepository.save(automovilista);
				}
				else {					
					throw new Exception("El automovilista no existe o se encuentra estacionado.");
				}
			}else {
				throw new Exception("El vehiculo ya se encuentra estacionado o no pertenece al automovilista.");				
			} 	
		}
		else {
			throw new Exception("No es horario habil.");			
		}
	}

	public void endParking(VehiculoDTO vehiculoDTO) throws Exception{
		Automovilista automovilista = this.findById(vehiculoDTO.getId());
		//Chequea si el vehiculo está estacionado. Si lo está, termina el estacionamiento.
		if(parkingService.isParked(vehiculoDTO.getLicensePlate())) { 
			//Si el automovilista existe y esta estacionado, finaliza el estacionamiento
			if (automovilista.getParking() != null) {
				Parking parking = automovilista.getParking();
				automovilista.end();
				parkingRepository.delete(parking);
				automovilistaRepository.save(automovilista);
			}
			else throw new Exception("El automovilista no existe o no se encuentra estacionado");
		}
		else throw new Exception("El vehiculo no se encuentra estacionado.");
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
}
