package ar.edu.unlam.pb1.dominio;

import java.util.Random;

import ar.edu.unlam.pb1.dominio.enums.Estado;
import ar.edu.unlam.pb1.dominio.enums.TipoMision;

/**
 * La clase que representa un CanBot.
 * 
 */

public class CanBot {

	private int id;
	private String nombre;
	private int bateria;
	private TipoMision tipoMision;
	private Estado estado;
	private int misionesCompletadas;
	private int enemigosEncontrados;
	private double kilometrosRecorridos;
	private TipoMision ultimoTipoMision;

	/**
	 * Constructor de un CanBot.
	 *
	 * @param id         El ID del CanBot.
	 * @param nombre     El nombre del CanBot.
	 * @param bateria    La beteria del CanBot.
	 * @param tipoMision El tipo de mision a asignar.
	 */
	public CanBot(int id, String nombre, int bateria) {
		this.id = id;
		this.nombre = nombre;
		this.bateria = bateria;
		this.tipoMision = null;
		this.estado = Estado.DISPONIBLE;
		this.misionesCompletadas = 0;
		this.enemigosEncontrados = 0;
		this.kilometrosRecorridos = 0;
	}

	/**
	 * Muestra el ID del CanBot.
	 * 
	 */
	public int getId() {
		return id;
	}

	/**
	 * Muestra el nombre del CanBot.
	 * 
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Muestra la bateria del CanBot.
	 * 
	 */
	public int getBateria() {
		return bateria;
	}

	/**
	 * Muestra la mision del CanBot.
	 * 
	 */
	public TipoMision getTipoMision() {
		return tipoMision;
	}

	/**
	 * Muestra el estado del CanBot.
	 * 
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Muestra las misiones completas del CanBot.
	 * 
	 */
	public int getMisionesCompletadas() {
		return misionesCompletadas;
	}

	/**
	 * Muestra los enemigos encontrados por CanBot.
	 * 
	 */
	public int getEnemigosEncontrados() {
		return enemigosEncontrados;
	}

	/**
	 * Muestra los KM recorridos por CanBot.
	 * 
	 */
	public double getKilometrosRecorridos() {
		return kilometrosRecorridos;
	}

	/**
	 * Muestra la ultima mision del CanBot.
	 * 
	 */
	public TipoMision getUltimoTipoMision() {
		return ultimoTipoMision;
	}

	/**
	 * Establece la cantidad de enemigos encontrados por el CanBot.
	 * 
	 */
	public void setEnemigosEncontrados(int enemigosEncontrados) {
		this.enemigosEncontrados = enemigosEncontrados;
	}

	/**
	 * Establece la cantidad de KM que relizo el CanBot.
	 * 
	 */
	public void setKilometrosRecorridos(double kilometrosRecorridos) {
		this.kilometrosRecorridos = kilometrosRecorridos;
	}

	/**
	 * Establece el nombre del CanBot.
	 * 
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Establece el nivel de bateria del CanBot.
	 * 
	 */
	public void setBateria(int bateria) {
		this.bateria = bateria;
	}

	/**
	 * Establece el tipo de mision del CanBot.
	 * 
	 */
	public void setTipoMision(TipoMision tipoMision) {
		this.tipoMision = tipoMision;
	}

	/**
	 * Establece el estado del CanBot.
	 * 
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/**
	 * Establece las misiones completas del CanBot.
	 * 
	 */
	public void setMisionesCompletadas(int misionesCompletadas) {
		this.misionesCompletadas = misionesCompletadas;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\nNombre: " + nombre + "\nBatería: " + bateria + "%" + "\nMisión: " + tipoMision
				+ "\nEstado: " + estado + "\nMisiones completadas: " + misionesCompletadas + "\nEnemigos encontrados: "
				+ enemigosEncontrados + "\nKilómetros recorridos: " + String.format("%.2f", kilometrosRecorridos)
				+ "\n----------------------------";
	}

	/**
	 * Recarga la bateria del CanBot.
	 * 
	 */
	public String recargarBateria() {
		String mensaje = "El CanBot " + nombre + " fue recargado al 100%.";
		this.bateria = 100;
		return mensaje;
	}

	/**
	 * Genera un cosumo aleatorio de bateria del CanBot.
	 * 
	 */
	public int ConsumoBateriaAleatorio() {
		Random random = new Random();
		return random.nextInt(this.bateria + 1);
	}

	/**
	 * Descarga la bateria del CanBot.
	 * 
	 */
	public void descargarBateria(int consumo) {
		this.bateria -= consumo;
	}

	/**
	 * Termina la mision del CanBot.
	 * 
	 */
	public String terminarMision() {
	    if (this.estado == Estado.EN_MISION) {
	        this.ultimoTipoMision = this.tipoMision;

	        int consumo = ConsumoBateriaAleatorio();
	        descargarBateria(consumo);

	        this.tipoMision = null;
	        this.misionesCompletadas++;

	        String mensaje = "Mision finalizada. Se consumieron " + consumo + "% de bateria.\n"
	                       + "CanBot " + nombre + " tiene " + bateria + "% de bateria restante.";

	        if (this.bateria < 50) {
	            this.estado = Estado.EN_REPARACION;
	            mensaje += "\n Bateria critica. Canbot enviado automaticamente a reparacion.";
	        } else {
	            this.estado = Estado.DISPONIBLE;
	            mensaje += "\n CanBot " + nombre + " esta disponible para una nueva mision.";
	        }

	        return mensaje;
	    } else {
	        return "Este Canbot no esta en una mision actualmente.";
	    }
	}


}
