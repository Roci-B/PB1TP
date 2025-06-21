package ar.edu.unlam.pb1.controlador;

import java.util.Random;

import ar.edu.unlam.pb1.dominio.CanBot;
import ar.edu.unlam.pb1.dominio.enums.Estado;
import ar.edu.unlam.pb1.dominio.enums.TipoMision;

public class Tareas {

	private CanBot[] canbots;
	private int cantidadActual;
	private int MAX_CANBOTS = 0;

	private String contrasenia = "";
	private String nombre = "";

	/**
	 * Constructor de un Asignador de Tareas.
	 *
	 * @param nombre      El nombre del usuario.
	 * @param MAX_CANBOTS El maximo de CanBot a crear.
	 */
	public Tareas(String nombre, int MAX_CANBOTS) {
		this.nombre = nombre;
		this.MAX_CANBOTS = MAX_CANBOTS;
		canbots = new CanBot[this.MAX_CANBOTS];
		cantidadActual = 0;
		this.contrasenia = generarContrasenia();
	}

	/**
	 * Metodos para generar, obtener y validar la contrasenia.
	 * 
	 */
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

	/**
	 * Inicia la sesion.
	 *
	 * @param contraseniaIngresada la contrasenia ingresada
	 * @return confirma si la sesion ha sido iniciada.
	 */
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
	public CanBot obtenerPorId(int id) {
		for (int i = 0; i < cantidadActual; i++) {
			if (canbots[i] != null && canbots[i].getId() == id) {
				return canbots[i];
			}
		}
		return null;
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
		String mensaje = " ";
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
	 * @return confirma si el ID fue encontrado y modifica la mision.
	 */
	public Boolean asignarMision(int id, TipoMision mision) {
		boolean misionAsignada = false;
		int i = 0;
		do {
			if (canbots[i].getId() == id && canbots[i].getBateria() > 70
					&& canbots[i].getEstado().equals(Estado.DISPONIBLE)) {
				canbots[i].setTipoMision(mision);
				canbots[i].setEstado(Estado.EN_MISION);
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

	/**
	 * Carga la bateria del CanBot.
	 *
	 * @param id ID del CanBot a modificar
	 */
	public void cargarBot(int id) {
		int i = 0;
		boolean cargado = false;
		do {
			if (canbots[i] != null && canbots[i].getId() == id) {
				if (canbots[i].getBateria() < 100) {
					canbots[i].recargarBateria();
					System.out.println("Bateria recargada correctamente.");
					canbots[i].setEstado(Estado.DISPONIBLE);
				} else {
					System.out.println("La bateria ya esta al 100%.");
				}
				cargado = true;
			}
			i++;
		} while (i < canbots.length && !cargado);
		
		if (!cargado) {
			System.err.println("No se encontro un CanBot con ese ID.");
		}
	}


	public String mostrarEstadisticasGenerales() {
		int totalEnemigos = 0, maxEnemigos = Integer.MIN_VALUE, minEnemigos = Integer.MAX_VALUE;
		double totalKm = 0, maxKm = Double.MIN_VALUE, minKm = Double.MAX_VALUE;
		int totalMisiones = 0;
		int totalBateria = 0;
		int totalRecon = 0, totalTransporte = 0;

		for (int i = 0; i < cantidadActual; i++) {
			CanBot bot = canbots[i];
			if (bot != null) {
				totalBateria += bot.getBateria();
				totalMisiones += bot.getMisionesCompletadas();

				if (bot.getTipoMision() == TipoMision.RECONOCIMIENTO) {
					int enemigos = bot.getEnemigosEncontrados();
					totalEnemigos += enemigos;
					maxEnemigos = Math.max(maxEnemigos, enemigos);
					minEnemigos = Math.min(minEnemigos, enemigos);
					totalRecon++;
				}

				if (bot.getTipoMision() == TipoMision.TRANSPORTE) {
					double km = bot.getKilometrosRecorridos();
					totalKm += km;
					maxKm = Math.max(maxKm, km);
					minKm = Math.min(minKm, km);
					totalTransporte++;
				}
			}
		}

		String mensaje = "\n--- ESTADÍSTICAS GENERALES ---";

		if (totalRecon > 0) {
			mensaje += "\n> Enemigos encontrados (Reconocimiento):" + "\n   Máximo: " + maxEnemigos + "\n   Mínimo: "
					+ minEnemigos + "\n   Promedio: " + (totalEnemigos / totalRecon);
		}

		if (totalTransporte > 0) {
			mensaje += "\n> Kilómetros recorridos (Transporte):" + "\n   Máximo: " + String.format("%.2f", maxKm)
					+ "\n   Mínimo: " + String.format("%.2f", minKm) + "\n   Promedio: "
					+ String.format("%.2f", totalKm / totalTransporte);
		}

		if (cantidadActual > 0) {
			mensaje += "\n> Promedio de misiones completadas por bot: " + (totalMisiones / cantidadActual);
			mensaje += "\n> Promedio de batería restante por bot: " + (totalBateria / cantidadActual) + "%";
		}

		return mensaje;
	}

	/**
	 * Termina la mision del CanBot.
	 *
	 * @param id ID del CanBot a modificar
	 */
	public String terminarMisionDeCanBot(int id) {
		for (int i = 0; i < cantidadActual; i++) {
			if (canbots[i] != null && canbots[i].getId() == id) {
				String mensaje = canbots[i].terminarMision();
				if (canbots[i].getTipoMision() == null && canbots[i].getEstado() == Estado.DISPONIBLE) {
					if (canbots[i].getUltimoTipoMision() == TipoMision.RECONOCIMIENTO) {
						int enemigos = new Random().nextInt(51);
						canbots[i].setEnemigosEncontrados(enemigos);
					}
					if (canbots[i].getUltimoTipoMision() == TipoMision.TRANSPORTE) {
						double km = 10 + (Math.random() * 90);
						canbots[i].setKilometrosRecorridos(km);
					}
				}

				return mensaje;
			}
		}
		return "CanBot no encontrado.";
	}
	
	/**
	 * Metodos para ordenar los canbots por topo de mision.
	 *
	 */
	public String mostrarCanBotsDeReconocimiento() {
		String mensaje = "--- Canbots de RECONOCIMIENTO ---";
		boolean hayCoincidencias = false;

		for (int i = 0; i < cantidadActual; i++) {
			if (canbots[i] != null && TipoMision.RECONOCIMIENTO.equals(canbots[i].getTipoMision())) {
				mensaje += "\n" + canbots[i].toString();
				hayCoincidencias = true;
			}
		}

		if (!hayCoincidencias) {
			mensaje += "\n No hay Canbots con mision de reconocimiento.";
		}
		return mensaje;
	}

	public String mostrarCanBotsDeTransporte() {
		String mensaje = "--- Canbots de TRANSPORTE ---";
		boolean hayCoincidencias = false;

		for (int i = 0; i < cantidadActual; i++) {
			if (canbots[i] != null && TipoMision.TRANSPORTE.equals(canbots[i].getTipoMision())) {
				mensaje += "\n" + canbots[i].toString();
				hayCoincidencias = true;
			}
		}

		if (!hayCoincidencias) {
			mensaje += "\n No hay Canbots con mision de transporte.";
		}
		return mensaje;
	}

}
