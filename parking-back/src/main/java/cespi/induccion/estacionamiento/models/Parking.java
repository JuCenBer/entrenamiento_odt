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
        double amount = 0;
        int periods = 1; //Por defecto se cuenta el primer periodo para el cobro.
        amount += city.getPrice();
        if(minutosTranscurridos >= city.getFirstPeriodLength()) {
        	minutosTranscurridos -= city.getFirstPeriodLength(); 
        	double ratio = ( ((double)city.getSecondPeriodLength()) / ((double)city.getFirstPeriodLength()) ); //El precio de los periodos que siguen se calcula en base a la proporcion del precio del periodo inicial
        	periods = 1; //Por defecto se cuenta el primer periodo de los que le siguen al inicial.
        	int amountPeriods = (int) Math.ceil(((minutosTranscurridos) / city.getSecondPeriodLength()));
        	if(periods <= amountPeriods) periods = amountPeriods; //De haber pasado mas de un periodo de los que le siguen al inicial, se le asigna esa cantidad.
        	System.out.println("Ratio:" + ratio);
        	amount += periods * (city.getPrice()*ratio);           	
        }
		int intAmount = (int)(amount*100.0);
		return amount = ((double)intAmount)/100; //Esto es para asegurar que el los deciamales del precio sea solo de dos cifras (a lo sumo).
	}
}
