package cespi.induccion.estacionamiento.services;

import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.repositories.BankAccountRepository;
import cespi.induccion.estacionamiento.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImportService {
	
	@Autowired
	AutomovilistaService automovilistaService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BankAccountRepository bankAccountRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void checkMigration() throws Exception {	
		System.out.println("Chequeando existencia de archivo import.csv para migracion...");
		Path path = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			URL resourceUrl = classLoader.getResource("import.csv");
			path = Paths.get(resourceUrl.toURI());			
			System.out.println(path);
		}
		catch(Exception e){
			
		}
		if (path != null) {
			System.out.println("Archivo import.csv encontrado");
			System.out.println("Importando usuarios...");
			this.readLineByLine(path);
			
		}
	}
	
	
	public void readLineByLine(Path filePath) throws Exception {
	    try (Reader reader = Files.newBufferedReader(filePath)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            String[] line;
	            String cellphone;
	            double balance; 
	            while ((line = csvReader.readNext()) != null) {
	            	cellphone = line[0];
	            	try {
	            		automovilistaService.findByCellphone(cellphone);
	            		System.out.println("El usuario "+cellphone+" ya se encuentra registrado en el sistema.");
	            	}
	            	catch(Exception e) {	            		
	            		balance = Integer.valueOf(line[1]);
	            		User user = new User();
	            		user.setCellphone(cellphone);
	            		user.setPassword(UUID.randomUUID().toString().replace("-", "")); //se genera una contraseña random.
	            		automovilistaService.register(user); 
	            		this.setBalance(user, balance);
	            	}
	            }
	        }
	    }
	}
	
	private void setBalance(User user, double balance) {
		user.getBankAccount().setBalance(balance);
		bankAccountRepository.save(user.getBankAccount());
	}
}
