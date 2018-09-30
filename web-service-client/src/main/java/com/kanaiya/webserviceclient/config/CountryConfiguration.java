package com.kanaiya.webserviceclient.config;

import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import com.kanaiya.webserviceclient.webservice.CountryClient;

@Configuration
public class CountryConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("hello.wsdl");
		return marshaller;
	}

	@Bean
	public CountryClient countryClient(Jaxb2Marshaller marshaller) {
		CountryClient client = new CountryClient();
		client.setDefaultUri("http://localhost:8080/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		client.setInterceptors(new ClientInterceptor[] {wsSecurityInterceptor()});
		return client;
	}
	
	@Bean
	  public Wss4jSecurityInterceptor wsSecurityInterceptor() {
		Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
		wss4jSecurityInterceptor.setSecurementActions(WSHandlerConstants.TIMESTAMP + " " + WSHandlerConstants.USERNAME_TOKEN);
		wss4jSecurityInterceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
		wss4jSecurityInterceptor.setSecurementUsername("user");
	    wss4jSecurityInterceptor.setSecurementPassword("password");
		return wss4jSecurityInterceptor;
	}	

}