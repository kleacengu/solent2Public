<%-- 
    Document   : DoorLockPage.jsp
    Created on : Oct 20, 2018, 6:34:33 PM
    Author     : cgallen
--%>


<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.LogManager"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.model.CardKey"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.secretkey.SecretKeyProviderImpl"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.model.SecretKeyProvider"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.roomlock.HotelRoomLockServiceImpl"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.model.HotelReceptionService"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.model.HotelRoomLockService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   HotelRoomLockService roomLockService = (HotelRoomLockService) session.getAttribute("hotelRoomLockService");

     //If the user session has no HotelRoomLockService, create a new one
    if (roomLockService == null) {
        roomLockService = new HotelRoomLockServiceImpl();
        SecretKeyProvider secretKeyProvider = new SecretKeyProviderImpl();
        roomLockService.setSecretKeyProvider(secretKeyProvider);
        session.setAttribute("hotelRoomLockService", roomLockService);
    }

 
    // used to access current roomNumber either from session or from request
   String roomNumber = (String) request.getParameter("roomNumber");
    if (roomNumber == null) {
        roomNumber = (String) session.getAttribute("sessionRoomNumber");
       if (roomNumber == null) {
           roomNumber = "";
        }
    } else {
       session.setAttribute("sessionRoomNumber", roomNumber);
   }

 CardKey keyCard = null;
   
    String resultmessage="door locked";
  String requestCardCode = (String) request.getParameter("cardCode");
        if (requestCardCode != null) {
            try {
                roomLockService.setRoomNumber(roomNumber);
                boolean unlocked = roomLockService.unlockDoor(requestCardCode);
                if (unlocked){
                    resultmessage="door unlocked";
                }
            } catch (Exception ex) {
                resultmessage="cannot read card";
            }
        }

      
  
    
   
    
 %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Door Lock Page</title>
    </head>
    <body>
        <h1>Door Lock</h1>

        <form action="./DoorLockPage.jsp">
            Enter Room Number:<br>
            <input type="text" name="roomNumber" value="<%=roomNumber %>">
            <br>
            Enter Card Code:<br>
            
            <input type="text" name="cardCode"  size="100" value="<%=requestCardCode%>">
            <br>
            <input type="submit" value="Unlock Door">
        </form> 
         <br>
        <div id="result"><%=resultmessage%></div>

    </body>
</html>
