package com.dropship.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Bootstraps the Spring Application via Spring's automagic {@link SpringServletContainerInitializer}.
 * See {@code WebApplicationInitializer} for more information.
 *
 */
public class SpringWebAppInitializer implements WebApplicationInitializer {

	private static final Logger logger = Logger.getLogger( SpringWebAppInitializer.class.getName() );
	
	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
		Map<String, ? extends ServletRegistration> servlets = servletContext.getServletRegistrations();
        logger.finest("Servlets: " + servlets.size());
        for(String key : servlets.keySet()){
        	ServletRegistration reg = servlets.get(key);
        	logger.finest(" ==> " + key + " : " + reg.getClassName() + " [" + reg.getMappings() +"]");
        }
		
		// Create the 'root' Spring application context & scans anything for annotations this package
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.scan( getClass().getPackage().getName() );
        
        
        // Add Spring Security Filter
        //DelegatingFilterProxy securityFilter = root.getBean(DelegatingFilterProxy.class);
		DelegatingFilterProxy securityFilter = new DelegatingFilterProxy();
        servletContext.addFilter("springSecurityFilterChain", securityFilter)
        	.addMappingForUrlPatterns(null,false,"/*");

        // Manages the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(root));

        // Add our DispatcherServlet to the application root
        ServletRegistration.Dynamic appServlet = servletContext.addServlet("dispatchServlet", new DispatcherServlet(root));
        appServlet.setLoadOnStartup(1);
        Set<String> mappingConflicts = appServlet.addMapping("/");
        if (!mappingConflicts.isEmpty()) {
            throw new IllegalStateException("Failed mapping dispatcher to root");
        }
        
        // Add a debug servlet
        Dynamic ctx = servletContext.addServlet("test", new TestServlet());
        Set<String> testMappingConflicts = ctx.addMapping("/test");
        
        if (!testMappingConflicts.isEmpty()) {
            throw new IllegalStateException("Failed mapping test servlet");
        }
    }
	
	@SuppressWarnings("serial")
	private class TestServlet extends HttpServlet{
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			PrintWriter writer = resp.getWriter();
			writer.write("Hello World");
			writer.flush();
			writer.close();
		}
	}

}
