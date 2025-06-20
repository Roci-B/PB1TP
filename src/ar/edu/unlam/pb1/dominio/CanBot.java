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

	/**
	 * Constructs a new Rectangle with the specified length and width.
	 *
	 * @param id         El ID del CanBot.
	 * @param nombre     El nombre del CanBot.
	 * @param bateria    La beteria del CanBot.
	 * @param tipoMision El tipo de mision a asignar.
	 */
	public CanBot(int id, String nombre, int bateria, TipoMision tipoMision) {
		this.id = id;
		this.nombre = nombre;
		this.bateria = bateria;
		this.tipoMision = tipoMision;
		this.estado = Estado.DISPONIBLE;
		this.misionesCompletadas = 0;
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
				+ "\nEstado: " + estado + "\nMisiones completadas: " + misionesCompletadas
				+ "\n----------------------------";
	}
	
	public String recargarBateria() {
        String mensaje="El CanBot " + nombre + " fue recargado al 100%.";
        this.bateria = 100;
        return mensaje;
    }
    public int ConsumoBateriaAleatorio() {
        Random random = new Random();
        return random.nextInt(this.bateria + 1); 
    }
    public void descargarBateria(int consumo) {
        this.bateria -= consumo;
    }

    public String terminarMision() {
        if (this.estado == Estado.EN_MISION) {
            int consumo = ConsumoBateriaAleatorio();
            descargarBateria(consumo);

            this.estado = Estado.DISPONIBLE;
            this.tipoMision = null;
            this.misionesCompletadas++;

            return "Misión finalizada. Se consumieron " + consumo + "% de batería.\n"
                 + "CanBot " + nombre + " ahora está disponible con " + bateria + "% de batería.";
        } else {
            return "Este CanBot no está en una misión actualmente.";
        }
    }
	// misiones y bateria

}
