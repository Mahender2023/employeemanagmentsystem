package com.enterprise.ems.employee_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Marks this class as a configuration class for Spring
//Spring will look for @Bean methods inside it to create beans in the application context

@Configuration
public class CorsConfig {
	 // Marks this method as a Spring Bean
    @Bean //Bean creates the method object
    public WebMvcConfigurer corsConfigurer() {
    	// Return an anonymous implementation
        return new WebMvcConfigurer() {
        	
        	// Override the addCorsMappings method to customize CORS settings
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Define CORS settings for URL patterns starting with /api/
                registry.addMapping("/api/**")
                // Allow requests coming from this frontend origin (Angular app running on localhost:4200)
                        .allowedOrigins("http://localhost:4200")
                     // Allow only these HTTP methods for cross-origin requests
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                     // Allow all headers in requests
                        .allowedHeaders("*");
            }
        };
    }
}