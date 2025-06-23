package ar.edu.unlam.pb1.interfaz;

import java.util.Scanner;

import ar.edu.unlam.pb1.controlador.Tareas;
import ar.edu.unlam.pb1.dominio.CanBot;
import ar.edu.unlam.pb1.dominio.enums.Estado;
import ar.edu.unlam.pb1.dominio.enums.TipoMision;
import ar.edu.unlam.pb1.interfaz.enums.MenuPrincipal;

public class GestionCanBot {

	// Cybercanes Team: Rocio Bossio, Santiago Lavalle, Aixa Oviedo, Thiago Garcia.

	// Un sistema diseñado para gestionar una unidad de bots de asalto programables
	// en operaciones militares. Cada canino automatizado es un objeto con atributos
	// como nivel de batería, tipo de misión (reconocimiento, transporte de
	// suministros) y otros indicadores. El sistema permite la programación de
	// tareas específicas y la asignación de misiones, optimizando la colaboración
	// entre los canes y las fuerzas humanas

	private static Scanner teclado = new Scanner(System.in);
	public static final String RESET = "\u001B[0m";
	public static final String VERDE = "\u001B[32m";
	public static final String AMARILLO = "\u001B[33m";
	public static final String AZUL = "\u001B[34m";
	public static final String MORADO = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String NEGRITA = "\u001B[1m";

	public static void main(String[] args) {

		mostrarMensaje(MORADO + "--- SISTEMA DE CANBOTS MILITARES --- \n");

		MenuPrincipal opcion = null;
		String nombreOperador = ingresarString(AZUL + "\n Ingrese el nombre del operativo: \n");
		int cantidadBots = ingresarEntero("\n Ingrese la cantidad maxima de bots: \n");
		Tareas tarea = new Tareas(nombreOperador, cantidadBots);

		mostrarMensaje(VERDE + "\n Su contrasenia es: " + tarea.getContrasenia() + RESET);

		boolean sesionIniciada = false;
		int intentosRestantes = 3;

		while (!sesionIniciada && intentosRestantes > 0) {
			String contraseniaIngresada = ingresarString(
					"\n Ingrese la contraseña para continuar (" + intentosRestantes + " intento(s) restante(s)):");
			sesionIniciada = tarea.iniciarSesion(contraseniaIngresada);

			if (!sesionIniciada) {
				intentosRestantes--;
				mostrarError("Contraseña incorrecta.");
			}
		}

		if (!sesionIniciada) {
			mostrarError("Demasiados intentos fallidos. El sistema ha sido bloqueado.");
			return;
		}

		do {

			mostrarMenuPrincipal();
			opcion = ingresarOpcionElegida();

			switch (opcion) {
			case CREAR_CANBOT:
				crearCanBot(tarea);
				break;
			case RESETEAR_CANBOT:
				resetearCanbot(tarea);
				break;
			case DESACTIVAR_CANBOT:
				desactivarCanBot(tarea);
				break;
			case MODIFICAR_CANBOT:
				modificarCanBot(tarea);
				break;
			case MOSTRAR_CANBOTS:
				mostrarMensaje(tarea.mostrarCanBots());
				break;
			case ASIGNAR_MISION:
				asignarMision(tarea);
				break;
			case CARGAR_CANBOT:
				cargarCanbot(tarea);
				break;
			case TERMINAR_MISIONES:
				terminarMisiones(tarea);
				break;
			case ESTADISTICAS:
				mostrarMensaje(tarea.mostrarEstadisticasGenerales());
				break;
			case MOSTRAR_RECONOCIMIENTO:
				mostrarMensaje(tarea.mostrarCanBotsDeReconocimiento());
				break;
			case MOSTRAR_TRANSPORTE:
				mostrarMensaje(tarea.mostrarCanBotsDeTransporte());
				break;
			case SALIR:
				mostrarMensaje("\n Hasta luego!");
				break;
			default:
				mostrarError("Opción inválida.");
			}
		} while (opcion != MenuPrincipal.SALIR);
	}

	/**
	 * Opciones del Menu.
	 */

	/**
	 * Elige el tipo de mision.
	 * 
	 * @return Tipo de mision elegida.
	 */
	private static TipoMision elegirTipoMision() {

		TipoMision[] tipos = TipoMision.values();

		for (int i = 0; i < tipos.length; i++) {
			mostrarMensaje((i + 1) + ". " + tipos[i]);
		}
		int opcion = ingresarEntero("Seleccione tipo de misión:");

		while (opcion < 1 || opcion > tipos.length) {

			opcion = ingresarEntero("Opción inválida. Intente de nuevo:");
		}

		return tipos[opcion - 1];
	}

	/**
	 * Crea el Canbot, Agregando un elemento al array.
	 *
	 */
	private static void crearCanBot(Tareas tarea) {
		int id;
		CanBot existente;
		do {
			id = ingresarEntero(MORADO + "Ingrese ID del CanBot:");

			if (id < 1) {
				mostrarError("El ID debe ser positivo.");
			}

			existente = tarea.obtenerPorId(id);
			if (existente != null) {
				mostrarError("El ID ya existe. Ingrese uno diferente.");
			}

		} while (id < 1 || existente != null);

		String nombre = ingresarString("Ingrese nombre del CanBot:");

		int bateria;
		do {

			bateria = ingresarEntero("Ingrese nivel de batería (0-100):");
			if (bateria < 0 || bateria > 100) {
				mostrarError("Nivel de batería inválido.");
			}
		} while (bateria < 0 || bateria > 100);

		CanBot nuevo = new CanBot(id, nombre, bateria);
		if (tarea.agregarCanBot(nuevo)) {
			mostrarMensaje("CanBot agregado correctamente.");
		} else {
			mostrarError("No se pudo agregar (ya existe o está lleno).");
		}
	}

	/**
	 * Activa el Canbot, Buscando por id dentro del array.
	 *
	 */
	private static void resetearCanbot(Tareas tarea) {
		int id = ingresarEntero(CYAN + "Ingrese ID del CanBot:");

		CanBot bot = tarea.obtenerPorId(id);

		if (bot != null) {
			if (bot.getEstado() == Estado.EN_REPARACION || bot.getEstado() == Estado.EN_MISION) {
				bot.setEstado(Estado.DISPONIBLE);
				bot.setTipoMision(null);
				mostrarMensaje(VERDE + "Canbot disponible nuevamente. ");
			} else if (bot.getEstado() == Estado.DISPONIBLE) {
				mostrarMensaje(AMARILLO + "El Canbot ya esta activo y disponible.");
			} else {
				mostrarError("El Canbot no esta en reparacion. Estado actual: " + bot.getEstado());
			}
		} else {
			mostrarError("No se encontro el Canbot.");
		}
	}

	/**
	 * Desactiva el Canbot, Buscando por id dentro del array.
	 *
	 */
	private static void desactivarCanBot(Tareas tarea) {

		int id = ingresarEntero("Ingrese ID del Canbot:");

		if (tarea.desactivarCanBot(id)) {
			mostrarMensaje("Canbot ha sido desactivado correctamente.");
		} else {
			mostrarError("No se encontro el Canbot.");
		}
	}

	/**
	 * Modifica el Canbot, Buscando por id dentro del array.
	 *
	 */
	private static void modificarCanBot(Tareas tarea) {

		int id = ingresarEntero("Ingrese ID del CanBot:");

		String nombre = ingresarString("Nuevo nombre:");

		int bateria = ingresarEntero("Nueva bateria (0-100):");

		if (bateria < 0 || bateria > 100) {
			mostrarError("Nivel de bateria invalido.");
			return;
		}

		if (tarea.modificarCanBot(id, nombre, bateria)) {
			mostrarMensaje("Canbot modificado exitosamente.");
		} else {
			mostrarError("Canbot no encontrado.");
		}
	}

	/**
	 * Asigna mision al Canbot, Buscando por id dentro del array.
	 *
	 */
	private static void asignarMision(Tareas tarea) {

		int id = ingresarEntero(VERDE + "Ingrese ID del CanBot:");

		TipoMision tipoMision = elegirTipoMision();

		if (tarea.asignarMision(id, tipoMision)) {
			mostrarMensaje("Misión asignada correctamente." + RESET);
		} else {
			mostrarError("Error al asignar misión. Verifique el estado del CanBot.");
		}
	}

	/**
	 * Carga la bateria del canbot.
	 *
	 */
	private static void cargarCanbot(Tareas tarea) {
		int id = ingresarEntero("Ingrese ID del CanBot:");
		mostrarMensaje(VERDE + tarea.cargarBot(id));
	}

	/**
	 * Termina la mision del canbot.
	 *
	 */
	private static void terminarMisiones(Tareas tarea) {
		int id = ingresarEntero(AZUL + "Ingrese el ID del CanBot:");

		String resultado = tarea.terminarMisionDeCanBot(id);

		mostrarMensaje(resultado);
	}

	/**
	 * Metodos del menu principal.
	 * 
	 */

	private static MenuPrincipal ingresarOpcionElegida() {
		int opcion = 0;
		do {
			opcion = ingresarEntero("Ingrese la opcion deseada" + RESET);

		} while (opcion < 1 || opcion > MenuPrincipal.values().length);

		return MenuPrincipal.values()[opcion - 1];
	}

	private static void mostrarMenuPrincipal() {
		String menu = AMARILLO + "--- MENÚ DE GESTIÓN DE BOTS MILITARES --- \n";
		for (int i = 0; i < MenuPrincipal.values().length; i++) {
			menu += (i + 1) + ". " + MenuPrincipal.values()[i].getDescripcion() + "\n";
		}
		mostrarMensaje(menu);
	}

	/**
	 * Metodos de Utilidad.
	 * 
	 */

	public static int ingresarEntero(String mensaje) {
		System.out.println(mensaje);
		return teclado.nextInt();
	}

	public static String ingresarString(String mensaje) {
		mostrarMensaje(mensaje);
		return teclado.next().trim();
	}

	public static void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}

	public static void mostrarError(String mensaje) {
		System.err.println(mensaje);
	}

}
