package cespi.induccion.estacionamiento.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class City {

	@Id @GeneratedValue
	private Long id;
	private String city;
	private int startTime;
	private int endTime;
	private double price;
	private int firstPeriodLength; //periodo en minutos
	private int secondPeriodLength; //periodo en minutos
	
	
	//constructor
	public City() {
		
	}
	
	public City(String city, int startTime, int endTime, double price, int firstPeriodLength, int secondPeriodLength) {
		this.city = city;
		this.startTime= startTime;
		this.endTime = endTime;
		this.price = price;
		this.firstPeriodLength = firstPeriodLength;
		this.secondPeriodLength = secondPeriodLength;
	}
	
	public boolean isBusinessHour(int hour) {
		if(hour > this.startTime && hour < this.endTime) {
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
	
	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
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
