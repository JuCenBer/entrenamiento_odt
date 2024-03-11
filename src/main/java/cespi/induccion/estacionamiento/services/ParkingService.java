package cespi.induccion.estacionamiento.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.Automovilista;
import cespi.induccion.estacionamiento.models.Parking;
import cespi.induccion.estacionamiento.repositories.AutomovilistaRepository;
import cespi.induccion.estacionamiento.repositories.ParkingRepository;

@Service
@Transactional
public class ParkingService {

	@Autowired
	ParkingRepository parkingRepository;
	@Autowired
	AutomovilistaRepository automovilistaRepository;
	
	public boolean isParked(String plate) {
		List<Parking> parkedCars = parkingRepository.findAll();
		for (Parking parkedCar: parkedCars) {
			if(parkedCar.getLicensePlate() != null && parkedCar.getLicensePlate().equals(plate)) {
				return true;
			}
		}
		return false;
	}
	
	public void park(Automovilista automovilista, String licensePlate) throws Exception{
		//Chequea si el vehiculo está estacionado y si el vehiculo le pertenece al automovilista. Si lo está, no lo estaciona
		if (!this.isParked(licensePlate)) {
			if (automovilista != null && automovilista.getParking() == null) {
				//Si el automovilista existe y está estacionado, lo estaciona
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
	
	public void unpark(Automovilista automovilista, String licensePlate) throws Exception{
		if(this.isParked(licensePlate)) {
			//Si el automovilista existe y esta estacionado, finaliza el estacionamiento
			Parking parking = automovilista.getParking();
			automovilista.end();
			System.out.println("Estacionamiento terminado");
			parkingRepository.delete(parking);
			automovilistaRepository.save(automovilista);
		}
		else throw new Exception("El vehiculo no se encuentra estacionado.");
	}
}
