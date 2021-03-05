<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page session="true" import="java.util.*, es.laarboleda.tiendaLibros.*" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Libreria MVC - Checkout</h2>
<hr /><br />
<p><strong>Has comprado los siguientes libros:</strong></p>
<table border="1" cellspacing="0" cellpadding="5">
<tr>
<th>Titulo</th><th>Autor</th><th>Precio</th><th>Cantidad</th>
</tr>
<%
// Scriptlet 1: Muestra los elementos del carrito
ArrayList<ElementoPedido> cesta = (ArrayList<ElementoPedido>) session.getAttribute("carrito");
	for (ElementoPedido item : cesta) {
%>
<tr>
	<td><%= item.getTitulo()%></td>
	<td><%= item.getAutor()%></td>
	<td align="right"><%= item.getPrecio()%></td>
	<td align="right"><%= item.getCantidad()%></td>
</tr>
<%
	}
session.invalidate(); // tras la compra, invalida lo que hubiera en la sesión
%>
<tr>
	<th align="right" colspan="2">Total</th>
	<th align="right"><%= request.getAttribute("precioTotal")%></th>
	<th align="right"><%= request.getAttribute("cantidadTotal")%></th>
</tr>
</table>
<br />
<a href="shopping">Pulsa aquí para comprar más libros</a>
</body>
</html>