package cespi.induccion.estacionamiento.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {
	
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String role;
	@ManyToMany
	private List<Permission> permissions;
	
	public Role() {
		
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public boolean hasPermission(Permission permission) {
		if (this.getPermissions().contains(permission)) return true;
		else return false;
	}
}
