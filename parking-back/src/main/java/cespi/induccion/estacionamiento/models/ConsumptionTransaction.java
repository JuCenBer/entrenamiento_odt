package cespi.induccion.estacionamiento.models;

import jakarta.persistence.Entity;

@Entity
public class ConsumptionTransaction extends Transaction{
	
	public ConsumptionTransaction() {
		super();
	}

	public ConsumptionTransaction(double monto, String operacion) {
		super(monto, operacion);
	}
}
