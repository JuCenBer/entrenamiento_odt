package cespi.induccion.estacionamiento.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BankAccount {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private double balance;
	
	public BankAccount() {
	}

	public BankAccount(double balance) {
		this.balance = balance;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public boolean buyCredit(double amount) {
		this.balance = this.balance + amount;
		return true;
	}
}
