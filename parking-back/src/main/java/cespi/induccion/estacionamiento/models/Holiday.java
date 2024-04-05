package cespi.induccion.estacionamiento.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Holiday {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDate date;
	
	public Holiday() {
		
	}
	
	public Holiday(LocalDate date) {
		this.date = date;
	}
	
	public LocalDate getDate() {
	    return date;
	}
	
	public void setDate(LocalDate date) {
	    this.date = date;
	}
	
}
