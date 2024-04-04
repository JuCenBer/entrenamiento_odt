package cespi.induccion.estacionamiento.models;

import jakarta.persistence.Entity;

@Entity
public class VehiclePermission extends Permission{

	public VehiclePermission() {
		super.setPermission("Vehicle");
	}
}
