package cespi.induccion.estacionamiento.DTO;

import java.util.List;

import cespi.induccion.estacionamiento.models.Automovilista;
import cespi.induccion.estacionamiento.models.BankAccount;
import cespi.induccion.estacionamiento.models.City;
import cespi.induccion.estacionamiento.models.Parking;
import cespi.induccion.estacionamiento.models.Transaction;

public class AutomovilistaDTO {
	
	private long id;
	private String cellphone;
	private BankAccount bankAccount;
	private City city;
	private List<Transaction> transactions;
	private List<String> vehiculos;
	private Parking parking;
	
	public AutomovilistaDTO() {
		
	}
	
	public void getDTO(Automovilista automovilista) {
		this.cellphone = automovilista.getCellphone();
		this.vehiculos = automovilista.getVehiculos(); //en vez de pasarle la referencia, deberia clonar la lista
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
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
