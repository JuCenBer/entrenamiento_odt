package cespi.induccion.estacionamiento.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.ParkingPermission;
import cespi.induccion.estacionamiento.models.Permission;
import cespi.induccion.estacionamiento.models.Role;
import cespi.induccion.estacionamiento.models.SellerPermission;
import cespi.induccion.estacionamiento.models.VehiclePermission;
import cespi.induccion.estacionamiento.repositories.PermissionRepository;
import cespi.induccion.estacionamiento.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;

@Service
@Transactional
public class RoleService {
		
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	
	public RoleService() {
	}
	
	@PostConstruct
	public void checkExistingRoles() {
	    List<Role> roles = this.roleRepository.findAll();
	    if (roles.size() != 2) {
	    	System.out.println("Creando roles...");
	    	roleRepository.deleteAll();
	    	Role automovilistaRole = new Role();
	    	automovilistaRole.setRole("Automovilista");
	    	List<Permission> automovilistaPermissions = new ArrayList<Permission>();
	    	Permission parking = new ParkingPermission();
	    	Permission vehicle = new VehiclePermission();
	    	automovilistaPermissions.add(parking);
	    	automovilistaPermissions.add(vehicle);
	    	automovilistaRole.setPermissions(automovilistaPermissions);
	    	
	    	Role vendedorRole = new Role();
	    	vendedorRole.setRole("Vendedor");
	    	List<Permission> vendedorPermissions = new ArrayList<Permission>();
	    	Permission sell = new SellerPermission();
	    	vendedorPermissions.add(sell);
	    	vendedorRole.setPermissions(vendedorPermissions);
	    	
	    	
	    	permissionRepository.save(parking);
	    	permissionRepository.save(vehicle);
	    	permissionRepository.save(sell);
	    	roleRepository.save(automovilistaRole);
	    	roleRepository.save(vendedorRole);
	    	System.out.println("Roles creados");
	    }
	}
}
