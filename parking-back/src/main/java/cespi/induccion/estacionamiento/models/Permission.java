package cespi.induccion.estacionamiento.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Permission {
	
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String permission;
	
	public Permission(String permission) {
		this.permission = permission;
	}
	
	public Permission() {
		
	}
	
	public long getId() {
		return id;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
}
