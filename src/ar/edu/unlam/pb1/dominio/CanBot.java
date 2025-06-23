package ar.edu.unlam.pb1.dominio;

import java.util.Random;

import ar.edu.unlam.pb1.dominio.enums.Estado;
import ar.edu.unlam.pb1.dominio.enums.TipoMision;

/**
 * La clase que representa un Canbot.
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
	 * Constructor de un Canbot.
	 *
	 * @param id         El ID del Canbot.
	 * @param nombre     El nombre del Canbot.
	 * @param bateria    La beteria del Canbot.
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
	 * Muestra el ID del Canbot.
	 * 
	 */
	public int getId() {
		return id;
	}

	/**
	 * Muestra el nombre del Canbot.
	 * 
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Muestra la bateria del Canbot.
	 * 
	 */
	public int getBateria() {
		return bateria;
	}

	/**
	 * Muestra la mision del Canbot.
	 * 
	 */
	public TipoMision getTipoMision() {
		return tipoMision;
	}

	/**
	 * Muestra el estado del Canbot.
	 * 
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Muestra las misiones completas del Canbot.
	 * 
	 */
	public int getMisionesCompletadas() {
		return misionesCompletadas;
	}

	/**
	 * Muestra los enemigos encontrados por Canbot.
	 * 
	 */
	public int getEnemigosEncontrados() {
		return enemigosEncontrados;
	}

	/**
	 * Muestra los KM recorridos por Canbot.
	 * 
	 */
	public double getKilometrosRecorridos() {
		return kilometrosRecorridos;
	}

	/**
	 * Muestra la ultima mision del Canbot.
	 * 
	 */
	public TipoMision getUltimoTipoMision() {
		return ultimoTipoMision;
	}

	/**
	 * Establece la cantidad de enemigos encontrados por el Canbot.
	 * 
	 */
	public void setEnemigosEncontrados(int enemigosEncontrados) {
		this.enemigosEncontrados = enemigosEncontrados;
	}

	/**
	 * Establece la cantidad de KM que relizo el Canbot.
	 * 
	 */
	public void setKilometrosRecorridos(double kilometrosRecorridos) {
		this.kilometrosRecorridos = kilometrosRecorridos;
	}

	/**
	 * Establece el nombre del Canbot.
	 * 
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Establece el nivel de bateria del Canbot.
	 * 
	 */
	public void setBateria(int bateria) {
		this.bateria = bateria;
	}

	/**
	 * Establece el tipo de mision del Canbot.
	 * 
	 */
	public void setTipoMision(TipoMision tipoMision) {
		this.tipoMision = tipoMision;
	}

	/**
	 * Establece el estado del Canbot.
	 * 
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/**
	 * Establece las misiones completas del Canbot.
	 * 
	 */
	public void setMisionesCompletadas(int misionesCompletadas) {
		this.misionesCompletadas = misionesCompletadas;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\nNombre: " + nombre + "\nBateria: " + bateria + "%" + "\nMision: " + tipoMision
				+ "\nEstado: " + estado + "\nMisiones completadas: " + misionesCompletadas + "\nEnemigos encontrados: "
				+ enemigosEncontrados + "\nKilometros recorridos: " + String.format("%.2f", kilometrosRecorridos)
				+ "\n----------------------------";
	}

	/**
	 * Recarga la bateria del Canbot.
	 * 
	 */
	public String recargarBateria() {
		String mensaje = "El CanBot " + nombre + " fue recargado al 100%.";
		this.bateria = 100;
		return mensaje;
	}

	/**
	 * Genera un cosumo aleatorio de bateria del Canbot.
	 * 
	 */
	public int ConsumoBateriaAleatorio() {
		Random random = new Random();
		return random.nextInt(this.bateria + 1);
	}

	/**
	 * Descarga la bateria del Canbot.
	 * 
	 */
	public void descargarBateria(int consumo) {
		this.bateria -= consumo;
	}

	/**
	 * Termina la mision del Canbot.
	 * 
	 */
	public String terminarMision() {
		if (this.estado == Estado.EN_MISION) {
			this.ultimoTipoMision = this.tipoMision;

			int consumo = ConsumoBateriaAleatorio();
			descargarBateria(consumo);

			this.tipoMision = null;
			this.misionesCompletadas++;

			String mensaje = "Mision finalizada. Se consumieron " + consumo + "% de bateria.\n" + "Canbot " + nombre
					+ " tiene " + bateria + "% de bateria restante.";

			if (this.bateria < 50) {
				this.estado = Estado.EN_REPARACION;
				mensaje += "\n Bateria critica. Canbot enviado automaticamente a reparacion.";
			} else {
				this.estado = Estado.DISPONIBLE;
				mensaje += "\n Canbot " + nombre + " esta disponible para una nueva mision.";
			}

			return mensaje;
		} else {
			return "Este Canbot no esta en una mision actualmente.";
		}
	}

}
