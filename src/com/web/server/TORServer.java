package com.web.server;

import javax.xml.ws.Endpoint;

import com.web.service.impl.Server;
import java.io.IOException;
import java.util.HashMap;



public class TORServer{


public static void main(String args[]) throws SecurityException, IOException {

	try{
	HashMap<String,HashMap<String,Integer>> TORrecord=new HashMap<String,HashMap<String,Integer>>();
	
	HashMap<String,Integer>value=new HashMap<String,Integer>();
	HashMap<String,Integer>value1=new HashMap<String,Integer>();
	HashMap<String,Integer>value2=new HashMap<String,Integer>();
	HashMap<String,Integer>value3=new HashMap<String,Integer>();

	value.put("TORA080617", 2);
	value1.put("TORA080617", 2);
	value2.put("TORA110617", 1);

	TORrecord.put("Conference", value);
	TORrecord.put("TradeShows", value1);
	TORrecord.put("Seminars", value2);
	
	Server tor = new Server("TOR",2004,TORrecord);

	System.out.println("Toronto Server is running");
	
	Endpoint endpoint = Endpoint.publish("http://localhost:2004/tor", tor);


} 

catch (Exception e) {
	System.err.println("ERROR: " + e);
	e.printStackTrace(System.out);
}

//System.out.println("Toronto Server Exiting ...");

}

	
	
}
