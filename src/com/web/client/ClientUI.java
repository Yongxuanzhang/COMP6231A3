package com.web.client;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.omg.CosNaming.*;

import com.web.service.WebInterface;
import com.web.service.impl.Log;

import org.omg.CORBA.*;


public class ClientUI{

    private String ID;
    private String location;
    private  ORB orb;
    private Log userLog;
    private HashMap<String,String> userInfo=new HashMap<String,String>();
	private boolean opFlag=true;
	private Scanner sc = new Scanner(System.in); 
	//private  Addition stub;
	static WebInterface stub;
	
    private ClientUI(ORB orb) throws SecurityException, IOException {       
        this.orb=orb;
        this.userInfoStart();
    }
    
    /*
     * TORE080619
     * TORE110619
     * MTLA090619
     * MTLA080619
     * OTWA190619
     * OTWA250619
     * 
     * Conference
     * Seminars
     * TradeShows
     * 
     */
    
    
    
    private void userInfoStart() {
      //User data in client side.
      userInfo.put("TORM3456", "123");
      userInfo.put("MTLC2345", "123");
      userInfo.put("TORC1234", "123");
      userInfo.put("MTLM9000", "123");
      userInfo.put("OTWM6785", "123");
      userInfo.put("OTWC1234", "123");
    }

  
    
    private boolean checkUser(String ID,String pw) {
      
      
      
      if(userInfo.containsKey(ID)) {
        if(userInfo.get(ID).equals(pw)) {
          return true;
        }
       
      }
      return false;
    }
    
    
    public void mOperation() throws RemoteException {
      
      System.out.println("**********************************"); 
      System.out.println("Please Select One Manager Operation:");       
      System.out.println("1. Book Event"); 
      System.out.println("2. Get BookingSchedule"); 
      System.out.println("3. Cancel Event"); 
      System.out.println("4. Add Event"); 
      System.out.println("5. Remove Event"); 
      System.out.println("6. List Event Availability"); 
      System.out.println("7. Swap Event"); 
      System.out.println("E for Exit"); 
      System.out.println("**********************************"); 
      Scanner sc = new Scanner(System.in); 
      String input = sc.nextLine();
      operateM(input);
     // System.out.println(operate(input)); 
    }
    
    public void cOperation() throws RemoteException {
      System.out.println("**********************************"); 
      System.out.println("Please Select One Customer Operation:"); 
      System.out.println("1. Book Event"); 
      System.out.println("2. Get BookingSchedule"); 
      System.out.println("3. Cancel Event"); 
      System.out.println("4. Swap Event"); 
      System.out.println("E for Exit"); 
      System.out.println("**********************************"); 
      Scanner sc = new Scanner(System.in); 
      String input = sc.nextLine();
      operateC(input);
      //System.out.println(operate(input)); 
    }
 
    public boolean operateC(String input) throws RemoteException {
    	Scanner sct;
    	String eventID;
    	String eventType;
    	String customerID;
    	int bookingCapacity;
        switch(input) {
          case "1":          	
        	  sct = new Scanner(System.in);
        	  //System.out.println("Please Enter Customer ID:");         	  
	          customerID = this.ID; 
	          System.out.println("Please Enter Event ID:"); 
	          eventID = sct.nextLine(); 
	          System.out.println("Please Enter Event Type:"); 
	          eventType = sct.nextLine(); 

	          int bookRes=stub.bookEvent(customerID, eventID, eventType);
	          
	          //System.out.println("res is "+bookRes); 
              
	          if(bookRes==1) {
	              System.out.println("Booked successfully."); 
	              userLog.logger.info(customerID+" booked "+eventID+" successfully.");
	              return true;
	          }else if(bookRes==-3) {
	          	 System.out.println("Capacity is full."); 
	          	userLog.logger.info(customerID+" cannot book "+eventID);
	          	 return false;
	          }else if(bookRes==-1) {
                System.out.println(customerID+" cannot book "+eventID+ "from other cities more than 3 times 1 month"); 
               userLog.logger.info(customerID+" cannot book "+eventID+ "from other cities more than 3 times 1 month");
                return false;
             }
	          else if(bookRes==-2) {
	          	 System.out.println("Customer "+ customerID+" Already booked this event."); 
	          	userLog.logger.info(customerID+" has already booked"+eventID);
	          	 return false;
	          }else if(bookRes==-5) {
		          	 System.out.println("Event doesn't exist"); 
		          	 userLog.logger.info(customerID+" cannot book "+eventID);
		          	 return false;
	          }else if(bookRes==-6) {
		          	 System.out.println("Wrong type"); 
		          	 userLog.logger.info(customerID+" cannot book "+eventID);
		          	 return false;
		          }
	          else {
	        	  System.out.println("Wrong Input.");
	        	  userLog.logger.info(customerID+" cannot book "+eventID);
	        	  return false;
	          }
	     
          case "2":
        	  sct = new Scanner(System.in);
     	  
	          customerID = this.ID; 
	          String userSchedule=stub.getBookingSchedule(customerID);
	          
	          if(userSchedule!=null) {
	              //System.out.println(stub.getBookingSchedule(customerID)); 
	              userLog.logger.info(customerID+" got the schedule");
	            
	              System.out.println(userSchedule); 
	              //System.out.println(userSchedule.substring(4, userSchedule.length())); 
	              /*
	              for(String o:stub.getBookingSchedule(customerID)) {
	            	     System.out.println(o); 
	              }*/
	              
	              return true;
	          }else{
	          	 System.out.println("Failure."); 
	          	 userLog.logger.info(customerID+" cannot get the schedule");
	          	 return false;
	          }
        	  
          case "3":
        	  sct = new Scanner(System.in);
        	  //System.out.println("Please Enter Customer ID:");         	  
        	  customerID = this.ID; 
	          System.out.println("Please Enter Event ID:"); 
	          eventID = sct.nextLine(); 
              System.out.println("Please Enter Event Type:"); 
              eventType = sct.nextLine(); 	
	          if(stub.cancelEvent(eventID,eventType,customerID)) {
	              System.out.println("Canceled successfully."); 
	              userLog.logger.info(customerID+" canceled "+eventID+" successfully.");
	              return true;
	          }else{
	          	 System.out.println("Failure."); 
	          	 userLog.logger.info(customerID+" cannot cancel "+eventID);
	          	 return false;
	          }
          case "4":
          	  sct = new Scanner(System.in); 

              System.out.println("Please Enter new Event ID:"); 
              String newEventID = sct.nextLine(); 
              System.out.println("Please Enter new Event Type:"); 
              String newEventType = sct.nextLine(); 	  
              System.out.println("Please Enter old Event ID:"); 
              String oldEventID = sct.nextLine(); 
              System.out.println("Please Enter old Event Type:"); 
              String oldEventType = sct.nextLine(); 	
              
              int swapRes=stub.swapEvent(ID, newEventID, newEventType, oldEventID, oldEventType);
              System.out.println("Swapped res."+swapRes); 
              if(swapRes==1) {
                  System.out.println("Swapped successfully."); 
                  userLog.logger.info(ID+" swapped "+oldEventID+" with "+newEventID+" successfully.");
                  return true;
              }
              else if(swapRes==-3) {
                  System.out.println(newEventID+" is full.");
          		 userLog.logger.info(ID+" cannot swap "+oldEventID);
          		 return false;
              }
              else if(swapRes==-1) {
            	  System.out.println(ID+" cannot book "+newEventID+ "from other cities more than 3 times 1 month"); 
                  userLog.logger.info(ID+" cannot book "+newEventID+ "from other cities more than 3 times 1 month");
        		 return false;
          	}
           
              else {
              	 System.out.println("You cannot swap now."); 
              	userLog.logger.info(ID+" cannot swap "+oldEventID);
              	 return false;
              }   
	          
	          

          case "E":
        	  opFlag=false;
        	  userLog.logger.info(ID+" exit the system. ");
        	  break;
          case "e":
        	  opFlag=false;
        	   userLog.logger.info(ID+" exit the system. ");
        	  break;
        }
        return false;
        }
    
    public boolean operateM(String input) throws RemoteException {
    	Scanner sct;
    	String eventID;
    	String eventType;
    	String customerID;
    	int bookingCapacity;
        switch(input) {
          case "1":          	
        	  sct = new Scanner(System.in);
        	  System.out.println("Please Enter Customer ID:");         	  
	          customerID = sct.nextLine(); 
	          System.out.println("Please Enter Event ID:"); 
	          eventID = sct.nextLine(); 
	          System.out.println("Please Enter Event Type:"); 
	          eventType = sct.nextLine(); 

	          String eloc=eventID.substring(0, 3);
	          String uloc=customerID.substring(0, 3);
	          if(eloc.equals("MTL")||eloc.equals("OTW")||eloc.equals("TOR")||uloc.equals("MTL")||uloc.equals("OTW")||
	              uloc.equals("TOR")||eventType.equals("Conference")||eventType.equals("Seminars")||eventType.equals("Trade shows")) {
	            int bookRes=stub.bookEvent(customerID, eventID, eventType);
	          
	            //System.out.println("res is "+bookRes); 
	              
	              if(bookRes==1) {
	                  System.out.println("Booked successfully."); 
	                  userLog.logger.info(ID+" booked "+eventID+" for "+customerID+" successfully.");
	                  return true;
	              }else if(bookRes==-3) {
	                 System.out.println("Capacity is full."); 
	                 userLog.logger.info(ID+" cannot book "+eventID+" for "+customerID);
	                 return false;
	              }else if(bookRes==-1) {
	                System.out.println(customerID+" cannot book "+eventID+ "from other cities more than 3 times 1 month"); 
	                userLog.logger.info(customerID+" cannot book "+eventID+ "from other cities more than 3 times 1 month");
	                 return false;
	              }
	              else if(bookRes==-2) {
	                 System.out.println("Customer "+ customerID+" Already booked this event."); 
	                 userLog.logger.info(ID+" cannot book "+eventID+" for "+customerID);
	                 return false;
	              }else if(bookRes==-5) {
	                     System.out.println("Event doesn't exist"); 
	                     userLog.logger.info(ID+" cannot book "+eventID+" for "+customerID);
	                     return false;
	                  }
	          
	          
	          }
	          else {
	        	  System.out.println("Wrong Input.");
	        	    userLog.logger.info(ID+" cannot book "+eventID+" for "+customerID);
	        	  return false;
	          }
	     
          case "2":
        	  sct = new Scanner(System.in);
        	  System.out.println("Please Enter Customer ID:");         	  
	          customerID = sct.nextLine(); 
	          String userSchedule=stub.getBookingSchedule(customerID);


	          if(userSchedule!=null) {
	              //System.out.println(stub.getBookingSchedule(customerID)); 
	              userLog.logger.info(customerID+" got the schedule");
	            
	              System.out.println(userSchedule.substring(4, userSchedule.length())); 
	              /*
	              for(String o:stub.getBookingSchedule(customerID)) {
	            	     System.out.println(o); 
	              }*/
	              
	              return true;
	          }else{
	          	 System.out.println("Failure."); 
	          	userLog.logger.info(customerID+" cannot get the schedule");
	          	 return false;
	          }
        	  
          case "3":
        	  sct = new Scanner(System.in);
        	  System.out.println("Please Enter Customer ID:");         	  
	          customerID = sct.nextLine(); 
	          System.out.println("Please Enter Event ID:"); 
	          eventID = sct.nextLine(); 
              System.out.println("Please Enter Event Type:"); 
              eventType = sct.nextLine(); 	
              
	          if(stub.cancelEvent(eventID,eventType,customerID)) {
	              System.out.println("Canceled successfully."); 
	              userLog.logger.info(customerID+" canceled "+eventID+" successfully.");
	              return true;
	          }else{
	          	 System.out.println("Failure."); 
	             userLog.logger.info(customerID+" cannot cancel "+eventID);
	          	 return false;
	          }
          case "4":

	        	sct = new Scanner(System.in);
	            System.out.println("Please Enter Event ID:"); 
	            eventID = sct.nextLine(); 
	            System.out.println("Please Enter Event Type:"); 
	            eventType = sct.nextLine(); 
	            System.out.println("Please Enter Booking Capacity:"); 
	            bookingCapacity = sct.nextInt(); 
            
	            if(!eventID.substring(0, 3).equals(location)) {
	            	 //System.out.println(eventID.substring(0, 3)+" "+location); 
	            	  System.out.println("You cannot add events in other cities."); 
	            	  userLog.logger.info(ID+" cannot add "+eventID);
	            	  return false;
	            }
	            
	            if(stub.addEvent(ID, eventID, eventType, bookingCapacity)) {
	                System.out.println("Added successfully."); 
	                userLog.logger.info(ID+" added "+eventID+" successfully.");
	                return true;
	            }else {
	            	 System.out.println("Failure."); 
	            	 userLog.logger.info(ID+" cannot add "+eventID);
	            	 return false;
	            }
	            
          case "5":            
	          	  sct = new Scanner(System.in); 
	              System.out.println("Please Enter Event ID:"); 
	              eventID = sct.nextLine(); 
	              System.out.println("Please Enter Event Type:"); 
	              eventType = sct.nextLine(); 	             	              
	              if(stub.removeEvent(ID, eventID, eventType)) {
	                  System.out.println("Removed successfully."); 
	                  userLog.logger.info(ID+" removed "+eventID+" successfully.");
	                  return true;
	              }else {
	              	 System.out.println("Wrong Input."); 
	              	userLog.logger.info(ID+" cannot remove "+eventID);
	              	 return false;
	              }   
	              
          case "6":
	        	  sct = new Scanner(System.in); 
	              System.out.println("Please Enter Event Type:"); 
	              eventType = sct.nextLine(); 
	              String res=stub.listEventAvailability(ID,eventType);
	              if(res!=null) {
	            	  System.out.println(eventType+res);
	            	  userLog.logger.info(ID+" get the infomation of "+eventType);
	              }else {
	               	 System.out.println("Wrong Input."); 
	               	userLog.logger.info(ID+" cannot get the infomation of "+eventType);
	               	 return false;
	              }            	
	              
	            return true;
	            
          case "7":
          	  sct = new Scanner(System.in); 
        	  System.out.println("Please Enter Customer ID:");         	  
	          customerID = sct.nextLine(); 
              System.out.println("Please Enter new Event ID:"); 
              String newEventID = sct.nextLine(); 
              System.out.println("Please Enter new Event Type:"); 
              String newEventType = sct.nextLine(); 	  
              System.out.println("Please Enter old Event ID:"); 
              String oldEventID = sct.nextLine(); 
              System.out.println("Please Enter old Event Type:"); 
              String oldEventType = sct.nextLine(); 	
              
              
              int swapRes=stub.swapEvent(customerID, newEventID, newEventType, oldEventID, oldEventType);
              if(swapRes==1) {
                  System.out.println("Swapped successfully."); 
                  userLog.logger.info(customerID+" swapped "+oldEventID+" with "+newEventID+" successfully.");
                  return true;
              }
              else if(swapRes==-3) {
                  System.out.println(newEventID+" is full.");
          		 userLog.logger.info(ID+" cannot swap "+oldEventID);
          		 return false;
              }
              else if(swapRes==-1) {
          		System.out.println(ID+" doesn't book "+oldEventID);
          		 userLog.logger.info(ID+" cannot swap "+oldEventID);
        		 return false;
          	}
	          else {
	              	 System.out.println("You cannot swap now."); 
	              	userLog.logger.info(customerID+" cannot swap "+oldEventID);
	              	 return false;
	              }   
		            
	            
          case "E":
        	  opFlag=false;
        	  userLog.logger.info(ID+" exit the system. ");
        	  break;
          case "e":
        	  opFlag=false;
        	  userLog.logger.info(ID+" exit the system. ");
        	  break;
          
        }
        
        
        return false;
    }
    
    private void login() {
      location = ID.substring(0, 3);
      
      switch(location) {
      
      case"MTL":
          try {
        	    
        		    
        		    URL addURL = new URL("http://localhost:2002/mtl");
        			QName addQName = new QName("http://impl.service.web.com/", "ServerService");
        			
        			Service addition = Service.create(addURL, addQName);
        			stub = addition.getPort(WebInterface.class);
        		    
        		    
        		    //stub = (Addition)  AdditionHelper.narrow(ncRef.resolve_str("MTL"));
                           
                } catch (Exception e) {
                    System.err.println("Client exception: " + e.toString());
                    e.printStackTrace();
                }
                
          break;
      case"OTW":
          try {
      	 
   		    URL addURL = new URL("http://localhost:2003/otw");
			QName addQName = new QName("http://impl.service.web.com/", "ServerService");
			
			Service addition = Service.create(addURL, addQName);
			stub = addition.getPort(WebInterface.class);
		    
                         
              } catch (Exception e) {
                  System.err.println("Client exception: " + e.toString());
                  e.printStackTrace();
              }
              
          break;
      case"TOR":
          try {
 
   		    URL addURL = new URL("http://localhost:2004/tor");
			QName addQName = new QName("http://impl.service.web.com/", "ServerService");
			
			Service addition = Service.create(addURL, addQName);
			stub = addition.getPort(WebInterface.class);
		    
                           
                } catch (Exception e) {
                    System.err.println("Client exception: " + e.toString());
                    e.printStackTrace();
                }
                
          break;
      default:
          System.out.println("Wrong ID");
  
      }
      
      

    }
    
    
    public void demo() throws RemoteException {
      
      boolean inRun=true;
      
      while(inRun){
        Scanner sc = new Scanner(System.in); 
        System.out.println("Please Enter Your ID:"); 
        String userID = sc.nextLine(); 
        System.out.println("Please Enter Your Passward:"); 
        String password = sc.nextLine(); 
        
        if(this.checkUser(userID, password)==true) {
          System.out.println("Sucessfully Login in:"); 
          System.out.println("Name:"+userID+"\n");
          inRun=false;
       
          this.ID=userID;
          try {
            userLog=new Log(ID+"-userLog.txt");
          } catch (SecurityException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          this.login();
          String userType=userID.substring(3, 4);

          while(opFlag) {
              if(userType.equals("M"))this.mOperation();
              if(userType.equals("C"))this.cOperation();
          }

          
          
        }else {
          System.out.println("Information Error. Please Try Again."); 
          
        }
                        
      } 
 


    }
    public static void main(String[] args) throws SecurityException, IOException {

  
 
            
            try {
  	    	  
    		    ORB orb = ORB.init(args, null);

    	   		ClientUI client;

                client = new ClientUI(orb);
                client.demo();
    		    
    	       }
    	       catch (Exception e) {
    	          System.out.println(e.getStackTrace());
    		  e.printStackTrace();
    	       }
    	 
            


    }
}
