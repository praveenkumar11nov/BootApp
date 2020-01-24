package com.example.demo.Utils;

import java.util.logging.Logger;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class MySiteMeshFilter extends ConfigurableSiteMeshFilter{
	
	Logger log = Logger.getLogger("MySiteMeshFilter.java");
	
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		log.info("inside sitemesh");
		builder
		.addDecoratorPath("/","/WEB-INF/Decorator/LoginLayout.jsp")
		.addDecoratorPath("/login","/WEB-INF/Decorator/LoginLayout.jsp")

		.addDecoratorPath("/app/**","/WEB-INF/ThemeDecorator/PageLayout.jsp")
		.addDecoratorPath("/app/paygrouppage","/WEB-INF/ThemeDecorator/PageLayoutWithoutHeaderAndLeftMenu.jsp")
		.addDecoratorPath("/customfield","/WEB-INF/ThemeDecorator/PageLayoutWithoutHeaderAndLeftMenu.jsp")
		//.addDecoratorPath("/app/decor1","/WEB-INF/ThemeDecorator/PageLayout.jsp")
		//.addDecoratorPath("/app/decor2","/WEB-INF/jsps/ThemePages/JSP2.jsp")
	    
		//.addDecoratorPath("/login","/WEB-INF/Decorator/LoginLayout.jsp")
		//.addDecoratorPath("/login?logout","/WEB-INF/Decorator/LoginLayout.jsp")
	    
		.addExcludedPath("/login")
		.addExcludedPath("/app/*")
		.addExcludedPath("/login?logout")
		.addExcludedPath("/webapi/**");
		
	}
}