package com.example.demo.Utils;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class MySiteMeshFilter extends ConfigurableSiteMeshFilter{
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder
		.addDecoratorPath("/**","/WEB-INF/Decorator/PageLayout.jsp")
		.addDecoratorPath("/","/WEB-INF/Decorator/LoginLayout.jsp")
	    //.addDecoratorPath("/login","/WEB-INF/Decorator/LoginLayout.jsp")
		//.addDecoratorPath("/login?logout","/WEB-INF/Decorator/LoginLayout.jsp")
	    
		.addExcludedPath("/login")
		.addExcludedPath("/login?logout")
		.addExcludedPath("/webapi/**");
		
	}
}