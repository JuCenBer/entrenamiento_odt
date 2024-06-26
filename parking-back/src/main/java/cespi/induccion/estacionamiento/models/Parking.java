package cespi.induccion.estacionamiento.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Parking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private LocalDateTime startHour;
	private LocalDateTime endHour;
	private String licensePlate;
	@OneToOne
	private User user;
	
	public Parking() {
		
	}
	
	public Parking(User user, String licensePlate) {
		this.user = user;
		this.licensePlate = licensePlate;
		this.startHour = LocalDateTime.now();
	}
	
	public LocalDateTime getStartHour() {
		return startHour;
	}
	public LocalDateTime getEndHour() {
		return endHour;
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public double endParking() {
        LocalDateTime endHour = LocalDateTime.now();
        long minutosTranscurridos = ChronoUnit.MINUTES.between(this.startHour, endHour);
        City city = user.getCity();
        double periods = 1; //Por defecto se cuenta el primer periodo para el cobro.
        double amountPeriods = Math.ceil(((minutosTranscurridos) / city.getMinutesPerPeriod()));
        if (periods < amountPeriods) {
        	periods = amountPeriods; // en caso de haber transcurrido más de un periodo, se le asigna esa cantidad.
        }
		double amount = periods * city.getPrice();
		int intAmount = (int)(amount*100.0);
		return amount = ((double)intAmount)/100; //Esto es para asegurar que el precio sea solo de dos cifras.
	}
}
