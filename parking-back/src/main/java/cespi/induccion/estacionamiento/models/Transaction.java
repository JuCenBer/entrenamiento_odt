package cespi.induccion.estacionamiento.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cespi.induccion.estacionamiento.DTO.TransactionDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction {

	@Id @GeneratedValue
	private Long id;
	private LocalDateTime date;
	private double amount;
	private double newBalance;
	private String operation;
	
	public Transaction() {
		
	}
	
	public Transaction(double amount, double newBalance, String operation) {
		this.date = LocalDateTime.now();
		this.amount = amount;
		this.newBalance = newBalance;
		this.operation = operation;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public abstract TransactionDTO getDTO();

	public double getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}
	
	
}
