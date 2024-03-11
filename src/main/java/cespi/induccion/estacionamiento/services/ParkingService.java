package cespi.induccion.estacionamiento.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.Parking;
import cespi.induccion.estacionamiento.repositories.ParkingRepository;

@Service
@Transactional
public class ParkingService {

	@Autowired
	ParkingRepository parkingRepository;
	
	public boolean isParked(String plate) {
		List<Parking> parkedCars = parkingRepository.findAll();
		for (Parking parkedCar: parkedCars) {
			if(parkedCar.getLicensePlate() != null && parkedCar.getLicensePlate().equals(plate)) {
				return true;
			}
		}
		return false;
	}
}
