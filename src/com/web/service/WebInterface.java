package com.web.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)

public interface WebInterface 
{

	//public int add(int a, int b);
	
	  void shutdown ();
	  String sayHello ();
	  String sayHello2 ();
	  int swapEvent (String customerID, String newEventID, String newEventType, String oldEventID, String oldEventType);
	  boolean addEvent (String managerID, String eventID, String eventType, int bookingCapacity);
	  boolean removeEvent (String ID, String eventID, String eventType);
	  String listEventAvailability (String managerID, String eventType);
	  int bookEvent (String customerID, String eventID, String eventType);
	  boolean cancelEvent (String eventID, String eventType, String customerID);
	  String getBookingSchedule (String customerID);

}
