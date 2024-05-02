package cespi.induccion.estacionamiento.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import cespi.induccion.estacionamiento.models.City;
import cespi.induccion.estacionamiento.models.Holiday;
import cespi.induccion.estacionamiento.repositories.CityRepository;
import cespi.induccion.estacionamiento.repositories.HolidayRepository;

@Service
@Transactional
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private HolidayRepository holidayRepository;
	@Autowired 
	private ImportService importService;
	private City city;
	
	public City getCity() throws Exception{ 
		//Funcionaria como un singleton, se asume que hay una sola ciudad en la aplicacion.
		if (this.city == null) {
			city = this.cityRepository.findByCity(this.importService.getRegionName()).get();
		}
		return city;
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void checkExistingCity() {
		String regionName = this.importService.getRegionName();
		double price = this.importService.getPrice();
		int startHour = this.importService.getStartTime();
		int endHour = this.importService.getEndTime();
		int basePeriodFraction = this.importService.getBasePeriodFraction();
		int posteriorPeriodFraction = this.importService.getPosteriorPeriodFraction();
	
		if(cityRepository.findAll().size() != 1) {
			cityRepository.deleteAll();
			City city = new City(regionName, startHour, endHour, price, basePeriodFraction, posteriorPeriodFraction);
			cityRepository.save(city);
		}
	}
	
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void checkExistingHolidays() {
		List<Holiday> holidays = this.holidayRepository.findAll();
		System.out.println("Chequeando existencia de feriados...");
		if(holidays.size() == 0) {
			System.out.println("No hay feriados. Creando feriados...");
			Holiday navidad = new Holiday(LocalDate.of(2024, 12, 25));
			Holiday anoNuevo = new Holiday(LocalDate.of(2025, 1, 1));
			Holiday independencia = new Holiday(LocalDate.of(2024, 7, 9));
			Holiday diaTrabajador = new Holiday(LocalDate.of(2025, 5, 1));
			//Holiday hoy = new Holiday(LocalDate.now());
			this.holidayRepository.save(navidad);
			this.holidayRepository.save(anoNuevo);
			this.holidayRepository.save(independencia);
			this.holidayRepository.save(diaTrabajador);
			//this.holidayRepository.save(hoy);
			System.out.println("Feriados creados.");
		}
	}
	
	public boolean esDiaHabil(LocalDate fecha) {
		List<Holiday> holidays = this.holidayRepository.findAll();
		System.out.println(holidays);
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
        	System.out.println("Es fin de semana.");
            return false;
        }
        for(Holiday holiday: holidays) {
        	if (holiday.getDate().equals(fecha)) {
        		System.out.println("Es feriado");
        		return false;
        	}
        }
        return true;
    }
}
