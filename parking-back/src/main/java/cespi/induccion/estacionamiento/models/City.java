package cespi.induccion.estacionamiento.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class City {

	@Id @GeneratedValue
	private Long id;
	private String city;
	private int startHour;
	private int endHour;
	private double price;
	private int firstPeriodLength; //periodo en minutos
	private int secondPeriodLength; //periodo en minutos
	
	
	//constructor
	public City() {
		
	}
	
	public City(String city, int startHour, int endHour, double price, int firstPeriodLength, int secondPeriodLength) {
		this.city = city;
		this.startHour = startHour;
		this.endHour = endHour;
		this.price = price;
		this.firstPeriodLength = firstPeriodLength;
		this.secondPeriodLength = secondPeriodLength;
	}
	
	public boolean isBusinessHour(int hour) {
		if(hour > this.startHour && hour < this.endHour) {
			System.out.println("Es horario habil");
			return true;
		}
		else {
			System.out.println("No es horario habil");
			return false;
		}
	}
	
	//Getter and setters
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public int getFirstPeriodLength() {
		return firstPeriodLength;
	}

	public void setFirstPeriodLength(int firstPeriodLength) {
		this.firstPeriodLength = firstPeriodLength;
	}

	public int getSecondPeriodLength() {
		return secondPeriodLength;
	}

	public void setSecondPeriodLength(int secondPeriodLength) {
		this.secondPeriodLength = secondPeriodLength;
	}

}
