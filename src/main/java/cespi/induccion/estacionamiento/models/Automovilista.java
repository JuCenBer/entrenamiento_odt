package cespi.induccion.estacionamiento.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class Automovilista {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String cellphone;
	private String password;
	
	@OneToOne
	private BankAccount bankAccount;
	
	@ManyToOne
	private City city;
	
	@OneToMany
	@JoinColumn(name="transaction_id")
	private List<Transaction> transactions;
	
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	private List<String> vehiculos;
	
	@OneToOne
	private Parking parking;
	
	public Automovilista() {
		
	}
	
	public Automovilista(String cellphone, String password, BankAccount bankAccount, City city) {
		this.cellphone = cellphone;
		this.password = password;
		this.bankAccount = bankAccount;
		this.city = city;
		this.vehiculos = new ArrayList<String>();
	}
	
	public void start(String plate) {
		this.parking = new Parking(this, plate);
	}
	
	public double end() {
		double monto = this.parking.endParking();
		this.parking = null;
		return monto;
	}
	
	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<String> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculos(List<String> vehiculos) {
		this.vehiculos = vehiculos;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}
}
