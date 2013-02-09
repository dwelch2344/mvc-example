package com.dropship.example.config;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.bakehouse.config.BakehouseConfig;
import com.mangofactory.bakehouse.config.BakehouseConfigBuilder;
import com.mangofactory.bakehouse.config.BakehouseConfigProvider;
import com.mangofactory.bakehouse.config.BakehouseSupport;
import com.mangofactory.bakehouse.core.ResourceCache;
import com.mangofactory.bakehouse.core.io.FileManager;

@Configuration
public class AssetConfig implements BakehouseConfigProvider {
	

	@Bean 
	public BakehouseSupport bakehouseSupport(){
		return new BakehouseSupport();
	}
	
	@Bean
	public ResourceCache resourceCache(BakehouseSupport support){
		return support.getResourceCache(support.getFileManager());
	}
	
	@Bean
	public BakehouseConfigBuilder bakehouseConfigBuilder(BakehouseSupport bakehouseSupport, ServletContext ctx){
		FileManager fileManager = bakehouseSupport.getFileManager();
		fileManager.setServletContext(ctx);
		ResourceCache resourceCache = bakehouseSupport.getResourceCache(fileManager);
		return bakehouseSupport.getBuilder(resourceCache, fileManager);
	}

    @Override
    @Bean
    public BakehouseConfig build(BakehouseConfigBuilder builder) {
            return builder
                .process("javascript").serveAsSingleFile("AppCode.js")
                // .process("typescript").with(new TypescriptProcessor("TypescriptCode.js"))
                //.serveResourcesFromCdn()
                .build();
        }
	
	

}
