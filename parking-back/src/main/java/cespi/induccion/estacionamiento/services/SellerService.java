package cespi.induccion.estacionamiento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.repositories.AutomovilistaRepository;

@Service
@Transactional
public class SellerService {

	@Autowired
	AutomovilistaRepository automovilistaRepository;
	
}
