package cespi.induccion.estacionamiento.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Transaction {

	@Id @GeneratedValue
	private Long id;
	private LocalDate date;
	private double amount;
	private String operation;
	
	public Transaction() {
		
	}
	
	public Transaction(double amount, String operation) {
		this.date = LocalDate.now();
		this.amount = amount;
		this.operation = operation;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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
	
	
}
