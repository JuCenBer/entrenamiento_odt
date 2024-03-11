package cespi.induccion.estacionamiento;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

public class SpringWebApp implements WebApplicationInitializer{

	public void onStartup(ServletContext container) throws ServletException{
//		Este método es invocado por Tomcat al iniciar la aplicación. 
//		Permite registrar servlets, filtros y listeners en tiempo de 
//		ejecución evitando la necesidad del archivo web.xml o 
//		anotaciones en las clases
		
		// Create the root Spring aplication context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(AppConfig.class);
		
		//ContextLoaderListener - Manage the lifecycle of the root aplication context
		//container.addListener(new ContextLoaderListener(rootContext));
		
		//DispatcherServlet - Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = container.addServlet("DispatcherServlet", new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
