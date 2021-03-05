package es.laarboleda.tiendaLibros;

public class ElementoPedido {
	private int idLibro;
	private int cantidad;
	public ElementoPedido(int idLibro, int cantidad){
		this.idLibro = idLibro;
		this.cantidad = cantidad;
	}
	public int getIdLibro(){
		return idLibro;
	}
	public void setIdLibro(int idLibro){
		this.idLibro = idLibro;
	}
	public int getCantidad(){
		return cantidad;
	}
	public void setCantidad(int cantidad){
		this.cantidad = cantidad;
	}
	public String getAutor(){
		return LibrosBD.getAutor(idLibro);
	}
	public String getTitulo(){
		return LibrosBD.getTitulo(idLibro);
	}
	public float getPrecio(){
		return LibrosBD.getPrecio(idLibro);
	}
}
