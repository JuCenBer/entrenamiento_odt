package cespi.induccion.estacionamiento.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.models.ConsumptionTransaction;
import cespi.induccion.estacionamiento.models.Parking;
import cespi.induccion.estacionamiento.models.Transaction;
import cespi.induccion.estacionamiento.repositories.UserRepository;
import cespi.induccion.estacionamiento.repositories.ParkingRepository;

@Service
@Transactional
public class ParkingService {

	@Autowired
	ParkingRepository parkingRepository;
	@Autowired
	UserRepository userRepository;
	
	public boolean isParked(String plate) {
		List<Parking> parkedCars = parkingRepository.findAll();
		for (Parking parkedCar: parkedCars) {
			if(parkedCar.getLicensePlate() != null && parkedCar.getLicensePlate().equals(plate)) {
				return true;
			}
		}
		return false;
	}
	
	public void park(User user, String licensePlate) throws Exception{
		//Chequea si el vehiculo está estacionado y si el vehiculo le pertenece al automovilista. Si lo está, no lo estaciona
		if (!this.isParked(licensePlate)) {
			if (user.getParking() == null) {
				//Si el automovilista existe y no está estacionado, lo estaciona
				user.start(licensePlate);
				parkingRepository.save(user.getParking());
				System.out.println("Estacionamiento iniciado");
			}
			else {					
				throw new Exception("El automovilista no existe o se encuentra estacionado.");
			}
		}else {
			throw new Exception("El vehiculo ya se encuentra estacionado o no pertenece al automovilista.");				
		} 
	}
	
	public double unpark(User user) {
		//Si el automovilista existe, finaliza el estacionamiento
		Parking parking = user.getParking();
		double monto = user.end();
		parkingRepository.delete(parking);
		System.out.println("parking eliminado");
		return monto;
	}
}
