package com.example.demo.Utils;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class MySiteMeshFilter extends ConfigurableSiteMeshFilter{
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		System.err.println("inside sitemesh");
		builder
		.addDecoratorPath("/","/WEB-INF/Decorator/LoginLayout.jsp")
		.addDecoratorPath("/login","/WEB-INF/Decorator/LoginLayout.jsp")

		.addDecoratorPath("/app/**","/WEB-INF/ThemeDecorator/PageLayout.jsp")
		.addDecoratorPath("/app/paygrouppage","/WEB-INF/ThemeDecorator/PageLayoutWithoutHeaderAndLeftMenu.jsp")
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