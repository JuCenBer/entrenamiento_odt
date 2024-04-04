package cespi.induccion.estacionamiento.models;

import jakarta.persistence.Entity;

@Entity
public class SellerPermission extends Permission{

	public SellerPermission() {
		super.setPermission("Sell");
	}
	
}
