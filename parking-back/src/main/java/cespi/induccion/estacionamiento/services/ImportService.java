package cespi.induccion.estacionamiento.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
	
	private String currencySymbol;
	private String region;
	private String localPlateFormat;
	private String mercosurPlateFormat;
	private String regionName;
	private double price;
	private int startTime;
	private int endTime;
	private int basePeriodFraction;
	private int posteriorPeriodFraction;
	
	public ImportService() {
		//Al instanciar la clase, inmediatamente lee los parametros de instancia.
		Properties properties = new Properties();
		ClassLoader classLoader = getClass().getClassLoader();
		Path path = null;
		System.out.println("Chequeando existencia de archivo instanceParameters.properties para obtener los parametros de instancia...");
		URL resourceUrl = classLoader.getResource("instanceParameters.properties");
		try {
			path = Paths.get(resourceUrl.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}	
		try (InputStream input = Files.newInputStream(path)) {
			System.out.println("Archivo instanceParameters encontrado. Leyendo parametros de instancia...");
            properties.load(input);
            this.currencySymbol = properties.getProperty("currencySymbol");
            this.region = properties.getProperty("region");
            this.localPlateFormat = properties.getProperty("localPlateFormat");
            this.mercosurPlateFormat = properties.getProperty("mercosurPlateFormat");
            this.regionName = properties.getProperty("regionName");
            this.price = Double.valueOf(properties.getProperty("price"));
            this.startTime = Integer.valueOf(properties.getProperty("startTime"));
            this.endTime = Integer.valueOf(properties.getProperty("endTime"));
            this.basePeriodFraction = Integer.valueOf(properties.getProperty("basePeriodFraction"));
            this.posteriorPeriodFraction = Integer.valueOf(properties.getProperty("posteriorPeriodFraction"));
        } catch (IOException ex) {
            System.err.println("Error al cargar el archivo de propiedades: " + ex.getMessage());
            ex.printStackTrace();
        }
	}

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
			this.readUsersLineByLine(path);
			
		}
	}
	
	
	public void readUsersLineByLine(Path filePath) throws Exception {
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
	

	public String getCurrencySymbol() {
		return currencySymbol;
	}
	
	public String getRegion() {
		return region;
	}

	public String getLocalPlateFormat() {
		return localPlateFormat;
	}


	public String getMercosurPlateFormat() {
		return mercosurPlateFormat;
	}

	public String getRegionName() {
		return regionName;
	}

	public double getPrice() {
		return price;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getBasePeriodFraction() {
		return basePeriodFraction;
	}


	public int getPosteriorPeriodFraction() {
		return posteriorPeriodFraction;
	}


	
}
