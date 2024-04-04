package cespi.induccion.estacionamiento.models;

import jakarta.persistence.Entity;

@Entity
public class ParkingPermission extends Permission{
	
	public ParkingPermission() {
		super.setPermission("Park");
	}

}
