package es.laarboleda.tiendaLibros.src.es.laarboleda.tiendaLibros;

import java.io.IOException;
import java.util.Formatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.*;
import javax.servlet.ServletRequest;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;


/**
 * Servlet implementation class ServerControlador
 */
@WebServlet("/ServerControlador")
public class ServerControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerControlador() {
        super();
        // TODO Auto-generated constructor stub
	    LibrosBD.cargarDatos();

    }
    public void init(ServletConfig conf) throws ServletException{
	    super.init(conf);
	    LibrosBD.cargarDatos();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupera la sesión actual o crea una nueva si no existe.
		HttpSession session = request.getSession(true);
		// Recupera el carrito de la sesión actual
		List<ElementoPedido> elCarrito = (ArrayList<ElementoPedido>) session.getAttribute("carrito");
		// Determina a que página jsp redirigirá
		String nextPage = "";
		String todo = request.getParameter("todo");
			if (todo == null) {
				// Primer acceso - prepara la redirección a order.jsp
				nextPage = "/order.jsp";
			}
				else if (todo.equals("add")) // añadir libro al carrito
			{
						// Enviado por order.jsp con los parámetros idLibro y cantidad.
						// crea un elementoPedido y lo añade al carrito
				ElementoPedido nuevoElementoPedido = new ElementoPedido(Integer.parseInt(request.getParameter("idLibro")),
				Integer.parseInt(request.getParameter("cantidad")));
				if (elCarrito == null)
					{ // el carrito está vacío
						elCarrito = new ArrayList<>();
						elCarrito.add(nuevoElementoPedido);
						// enlaza el carrito con la sesión
						session.setAttribute("carrito", elCarrito);
					}
				else {
					// Comprueba si el libro está en el carrito
					// si lo está actualiza la cantidad
					// si no lo añade
					boolean encontrado = false;
					Iterator iter = elCarrito.iterator();
						while (!encontrado && iter.hasNext())
						{
							ElementoPedido unElementoPedido = (ElementoPedido)iter.next();
								if (unElementoPedido.getIdLibro() == nuevoElementoPedido.getIdLibro())
								{
									unElementoPedido.setCantidad(unElementoPedido.getCantidad()+ nuevoElementoPedido.getCantidad());
									encontrado = true;
								}
						}
					if (!encontrado)
					{ // Lo añade al carrito
						elCarrito.add(nuevoElementoPedido);
					}
				}
			// Prepara la redirección a order.jsp para hacer más pedidos
			nextPage = "/order.jsp";
		} else if (todo.equals("remove")) // elimina libro del carrito
			{
				// Enviado por order.jsp con el parámetro indiceElemento
				// Borra el elemento indiceElemento del carrito
				int indiceCarrito = Integer.parseInt(request.getParameter("indiceElemento"));
				elCarrito.remove(indiceCarrito);
				// Prepara la redirección a order.jsp para seguir haciendo pedidos
				nextPage = "/order.jsp";
			} else if (todo.equals("checkout")) // termina de hacer la compra
				{
					// Enviado por order.jsp.
					// Calcula el precio total de todos los elementos del carrito
					float precioTotal = 0;
					int cantidadTotalOrdenada = 0;
					for (ElementoPedido item: elCarrito) {
						float precio = item.getPrecio();
						int cantidadOrdenada = item.getCantidad();
						precioTotal += precio * cantidadOrdenada;
						cantidadTotalOrdenada += cantidadOrdenada;
					}
					// Formatea el precio con dos decimales
					StringBuilder sb = new StringBuilder();
					Formatter formatter = new Formatter(sb);
					formatter.format("%.2f", precioTotal);
					// Coloca el precioTotal y la cantidadTotal en el request
					request.setAttribute("precioTotal", sb.toString());
					request.setAttribute("cantidadTotal", cantidadTotalOrdenada + "");
					// Prepara la redirección a checkout.jsp
					nextPage = "/checkout.jsp";
				}
		ServletContext servletContext = getServletContext();
		// redirige a la nextPage que corresponda
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
