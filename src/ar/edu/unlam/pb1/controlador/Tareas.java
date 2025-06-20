package ar.edu.unlam.pb1.controlador;

import ar.edu.unlam.pb1.dominio.CanBot;
import ar.edu.unlam.pb1.dominio.enums.Estado;
import ar.edu.unlam.pb1.dominio.enums.TipoMision;

public class Tareas {

	private CanBot[] canbots;
	private int cantidadActual;
	private int MAX_CANBOTS = 0;

	private String contrasenia = "";
	private String nombre = "";

	

	public Tareas(String nombre, int MAX_CANBOTS) {
		this.nombre = nombre;
		this.MAX_CANBOTS = MAX_CANBOTS;
		canbots = new CanBot[this.MAX_CANBOTS];
		cantidadActual = 0;
		this.contrasenia = generarContrasenia();
	}

	// generar y obtener la contraseña
	public String getContrasenia() {
		return contrasenia;
	}

	public String getNombre() {
		return nombre;
	}

	private String generarContrasenia() {
		char[] contraseniaGenerada = new char[4];
		contraseniaGenerada[0] = obtenerCaracterAleatorio(48, 57);
		contraseniaGenerada[1] = obtenerCaracterAleatorio(97, 122);
		contraseniaGenerada[2] = obtenerCaracterAleatorio(65, 90);
		contraseniaGenerada[3] = obtenerCaracterAleatorio(97, 122);

		return Contraseniastring(contraseniaGenerada);
	}

	private char obtenerCaracterAleatorio(int posicionInicial, int posicionFinal) {

		int randomInt = (int) (Math.random() * (posicionFinal - posicionInicial + 1)) + posicionInicial;
		char randomchar = (char) randomInt;

		return randomchar;
	}

	public static String Contraseniastring(char[] claves) {
		String contrasenia = "";
		for (int i = 0; i < claves.length; i++) {
			contrasenia += claves[i];
		}
		return contrasenia;

	}

	// log-in

	public boolean iniciarSesion(String contraseniaIngresada) {
		boolean sesionIniciada = false;
		if (this.contrasenia.equals(contraseniaIngresada)) {
			sesionIniciada = true;
		}
		return sesionIniciada;
	}

	/**
	 * Agrega un elemento al array CanBots.
	 *
	 * @param nuevoBot Objeto de CanBot
	 * @return confirma si el CanBot fue agregado.
	 */
	public boolean agregarCanBot(CanBot nuevoBot) {

		boolean registrado = false;
		int posicion = 0;
		do {

			if (this.canbots[posicion] == null) {
				this.canbots[posicion] = nuevoBot;
				cantidadActual++;
				registrado = true;

			} else {
				posicion++;
			}

		} while (posicion < canbots.length && !registrado);

		return registrado;
	};

	/**
	 * Busca por ID.
	 *
	 * @param id ID del CanBot
	 * @return confirma si el ID fue encontrado y lo muestra.
	 */
	public CanBot obtenerPorId(long id) {
		CanBot encontrado = null;
		int posicion = 0;

		while (posicion < this.canbots.length && encontrado == null) {
			if (this.canbots[posicion] != null && this.canbots[posicion].getId() == id) {
				encontrado = this.canbots[posicion];
			}
			posicion++;
		}
		return encontrado;
	}

	/**
	 * Activa el CanBot.
	 *
	 * @param id ID del CanBot
	 * @return confirma si el ID fue encontrado y lo activa.
	 */
	public boolean activarCanBot(int id) {
		boolean activado = false;
		for (int i = 0; i < cantidadActual; i++) {
			if (canbots[i].getId() == id) {
				canbots[i].setEstado(Estado.DISPONIBLE);
				activado = true;
			}
		}
		return activado;
	}

	/**
	 * Desactiva el CanBot.
	 *
	 * @param id ID del CanBot
	 * @return confirma si el ID fue encontrado y lo desactiva.
	 */
	public boolean desactivarCanBot(int id) {
		boolean desactivado = false;
		for (int i = 0; i < cantidadActual; i++) {
			if (canbots[i].getId() == id) {
				canbots[i].setEstado(Estado.EN_REPARACION);
				desactivado = true;
			}
		}
		return desactivado;
	}

	/**
	 * Modifica la informacion del CanBot.
	 *
	 * @param id           ID del CanBot a modificar
	 * @param nuevoNombre  Es el nuevo nombre del CanBot
	 * @param nuevaBateria Es el nuevo nivel de bateria del CanBot
	 * @param nuevaMision  Es la nueva la mision del CanBot
	 * @return Confirma si el ID fue encontrado y modifica los valores.
	 */
	public boolean modificarCanBot(int id, String nuevoNombre, int nuevaBateria, TipoMision nuevaMision) {
		boolean modificado = false;
		for (int i = 0; i < cantidadActual; i++) {
			if (canbots[i].getId() == id) {
				canbots[i].setNombre(nuevoNombre);
				canbots[i].setBateria(nuevaBateria);
				canbots[i].setTipoMision(nuevaMision);
				modificado = true;
			}
		}
		return modificado;
	}

	/**
	 * Muestra los CanBots.
	 *
	 */
	public String mostrarCanBots() {
		String mensaje = "";
		for (int i = 0; i < cantidadActual; i++) {
			if (canbots[i] != null) {
				mensaje += "\n" + canbots[i].toString();
			}
		}
		return mensaje;
	}

	/**
	 * Modifica la mision del CanBot.
	 *
	 * @param id     ID del CanBot a modificar
	 * @param mision las misiones posibles
	 * @return confirma si el ID fue encontrado y lo modifica.
	 */

	public Boolean asignarMision(int id, TipoMision mision) {
		boolean misionAsignada = false;
		int i = 0;
		do {

			if (canbots[i].getId() == id && canbots[i].getBateria() > 70
					&& canbots[i].getEstado().equals(Estado.DISPONIBLE)) {
				canbots[i].setTipoMision(mision);

				misionAsignada = true;
			} else if (canbots[i].getId() == id && canbots[i].getBateria() < 70
					&& canbots[i].getEstado().equals(Estado.DISPONIBLE)) {

				canbots[i].setEstado(Estado.EN_REPARACION);
			} else {
				i++;
			}

		} while (!misionAsignada && i < canbots.length);

		return misionAsignada;
	}

	public void cargarBot(int id) {
		int i = 0;
		boolean cargado = false;
		do {
			if (canbots[i].getId() == id && canbots[i].getBateria() < 70) {
				canbots[i].recargarBateria();
				cargado = true;
			}

		} while (i < canbots.length && !cargado);
	}

	// revisar las misiones completadas

	// realizar misiones ( bajar bateria y cambiar estado)

}
