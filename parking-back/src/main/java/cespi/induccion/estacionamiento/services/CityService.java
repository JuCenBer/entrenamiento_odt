package cespi.induccion.estacionamiento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.City;
import cespi.induccion.estacionamiento.repositories.CityRepository;

@Service
@Transactional
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	public City create() {
		City city = new City();
		return city;
	}
	
	public City getCity() throws Exception{
		City city = null;
		city = this.cityRepository.findById((long) 1).get();
		return city;
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void checkExistingCity() {
		if(cityRepository.findAll().size() != 1) {
			cityRepository.deleteAll();
			City city = new City("La Plata", 8, 20, 2.5);
			cityRepository.save(city);
		}
	}
}
