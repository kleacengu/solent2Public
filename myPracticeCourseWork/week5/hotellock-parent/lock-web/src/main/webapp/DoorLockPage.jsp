<%-- 
    Document   : DoorLockPage.jsp
    Created on : Oct 20, 2018, 6:34:33 PM
    Author     : cgallen
--%>

<%@page import="solent.ac.uk.ood.examples.hotellock.model.CardKey"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.secretkey.SecretKeyProviderImpl"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.model.SecretKeyProvider"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.reception.HotelReceptionServiceImpl"%>
<%@page import="solent.ac.uk.ood.examples.hotellock.model.HotelReceptionService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    HotelReceptionService hotelReceptionService = (HotelReceptionService) session.getAttribute("hotelReceptionService");

     //If the user session has no hotelReceptionService, create a new one
     if (hotelReceptionService == null) {
        hotelReceptionService = new HotelReceptionServiceImpl();
        SecretKeyProvider secretKeyProvider = new SecretKeyProviderImpl();
        hotelReceptionService.setSecretKeyProvider(secretKeyProvider);
        session.setAttribute("hotelReceptionService", hotelReceptionService);
    }
        

    String submitAction = (String) request.getParameter("submitAction");

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
    boolean errorReadingCard = false;
    String resultmessage="door locked";
    String requestCardCode = (String) request.getParameter("cardCode");
        if (requestCardCode != null) {
            try {
                keyCard = hotelReceptionService.readCard(requestCardCode);
            } catch (Exception ex) {
                errorReadingCard = true;
            }
            if (!errorReadingCard && roomNumber.equals(keyCard.getRoomNumber())){
                resultmessage="door unlocked";
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
            <input type="text" name="cardCode" value="<%=requestCardCode%>">
            <br>
            <input type="submit" value="Unlock Door">
        </form> 
         <br>
        <div id="result"><%=resultmessage%></div>

    </body>
</html>
