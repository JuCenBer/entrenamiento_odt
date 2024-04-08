package cespi.induccion.estacionamiento.DTO;

import java.util.List;

import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.models.BankAccount;
import cespi.induccion.estacionamiento.models.City;
import cespi.induccion.estacionamiento.models.Parking;
import cespi.induccion.estacionamiento.models.Transaction;

public class UserDTO {
	
	private long id;
	private String cellphone;
	private City city;
	private List<String> vehiculos;
	private List<String> permissions;
	private String token;
	private double balance;

	public UserDTO() {
		
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

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
