package br.com.fapen.estoque;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.fapen.estoque.services.ArquivoService;

@Configuration
public class FileResourceConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);		
		registry.addResourceHandler("/media/**").addResourceLocations("file://" + ArquivoService.DIRETORIO_BASE);
	}
	
}
