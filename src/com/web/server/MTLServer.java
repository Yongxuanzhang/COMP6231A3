package com.web.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.xml.ws.Endpoint;
import com.web.service.impl.Server;

public class MTLServer  {


//MTL2002;OTA2003;TOR2004

public static void main(String args[]) throws SecurityException, IOException {


	try{
		
		//Data setup
		HashMap<String,HashMap<String,Integer>> MTLrecord=new HashMap<String,HashMap<String,Integer>>();
		
		HashMap<String,Integer>value=new HashMap<String,Integer>();
		HashMap<String,Integer>value1=new HashMap<String,Integer>();
		HashMap<String,Integer>value2=new HashMap<String,Integer>();
		HashMap<String,Integer>value3=new HashMap<String,Integer>();
		value.put("MTLE090617", 2);
		value2.put("MTLE080617", 1);
		value3.put("MTLE100617", 1);
	
		
		MTLrecord.put("Conference", value);
		//MTLrecord.put("Conference", value2);
		MTLrecord.put("TradeShows", value2);
		MTLrecord.put("Seminars", value3);
		

		Server mtl = new Server("MTL",2002,MTLrecord);

		System.out.println("Montreal Server is running");
		Endpoint endpoint = Endpoint.publish("http://localhost:2002/mtl", mtl);

	} 

	catch (Exception e) {
		System.err.println("ERROR: " + e);
		e.printStackTrace(System.out);
	}

	//System.out.println("Montreal Server Exiting ...");

}




}



