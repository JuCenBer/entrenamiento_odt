package cespi.induccion.estacionamiento.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.Automovilista;
import cespi.induccion.estacionamiento.models.ConsumptionTransaction;
import cespi.induccion.estacionamiento.models.Parking;
import cespi.induccion.estacionamiento.models.Transaction;
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
			if (automovilista.getParking() == null) {
				//Si el automovilista existe y no está estacionado, lo estaciona
				automovilista.start(licensePlate);
				parkingRepository.save(automovilista.getParking());
				System.out.println("Estacionamiento iniciado");
			}
			else {					
				throw new Exception("El automovilista no existe o se encuentra estacionado.");
			}
		}else {
			throw new Exception("El vehiculo ya se encuentra estacionado o no pertenece al automovilista.");				
		} 
	}
	
	public double unpark(Automovilista automovilista, String licensePlate) throws Exception{
		if(this.isParked(licensePlate)) {
			//Si el automovilista existe y esta estacionado, finaliza el estacionamiento
			Parking parking = automovilista.getParking();
			double monto = automovilista.end();
			parkingRepository.delete(parking);
			System.out.println("parking eliminado");
			return monto;
		}
		else throw new Exception("El vehiculo no se encuentra estacionado.");
	}
}
