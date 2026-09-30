package com.jackalcode.BootForge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "BootForge API",
				version = "1.0.0",
				description = """
                        REST API for generating Spring Boot application configuration
                        in YAML or properties format.
                        """
		),
		servers = {
				@Server(url = "http://localhost:8000", description = "Local docker server"),
				@Server(url = "https://bootforge.onrender.com", description = "Production server")
		}
)
@SpringBootApplication
public class BootForgeApplication {

	public static void main(String[] args) {

		SpringApplication.run(BootForgeApplication.class, args);
	}

}
