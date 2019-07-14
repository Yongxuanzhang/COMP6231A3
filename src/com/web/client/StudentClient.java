package com.web.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.web.service.WebInterface;

public class StudentClient 
{	
	static WebInterface additionInterface;
	
	public static void main(String[] args) throws MalformedURLException 
	{
		URL addURL = new URL("http://localhost:8080/addition?wsdl");
		QName addQName = new QName("http://impl.service.web.com/", "ImplementationService");
		
		Service addition = Service.create(addURL, addQName);
		additionInterface = addition.getPort(WebInterface.class);

		//int c=additionInterface.bookEvent(customerID, eventID, eventType);	
		//System.out.println("Addition is: "+c);
		
	}
}
