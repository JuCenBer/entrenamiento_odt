package cespi.induccion.estacionamiento.models;

import java.time.LocalDateTime;

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
	private int startHour;
	private int endHour;
	private String licensePlate;
	@OneToOne
	private Automovilista automovilista;
	
	public Parking() {
		
	}
	
	public Parking(Automovilista automovilista, String licensePlate) {
		this.automovilista = automovilista;
		this.licensePlate = licensePlate;
		this.startHour = LocalDateTime.now().getHour();
	}
	
	public int getStartHour() {
		return startHour;
	}
	public int getEndHour() {
		return endHour;
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public double endParking() {
		return (LocalDateTime.now().getHour() - this.startHour) * this.automovilista.getCity().getPrice();
	}
}
