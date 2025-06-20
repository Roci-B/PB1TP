package ar.edu.unlam.pb1.interfaz;

import java.util.Scanner;

import ar.edu.unlam.pb1.controlador.Tareas;
import ar.edu.unlam.pb1.dominio.CanBot;
import ar.edu.unlam.pb1.dominio.enums.TipoMision;
import ar.edu.unlam.pb1.enums.MenuPrincipal;

public class GestionCanBot {

	private static Scanner teclado = new Scanner(System.in);
	

	public static void main(String[] args) {

		MenuPrincipal opcion = null;
		String nombreOperador = ingresarString("\nIngrese el nombre del operativo");

		int cantidadBots = ingresarEntero("\n Ingrese la cantidad maxima de bots");

		Tareas tarea = new Tareas(nombreOperador, cantidadBots);

		mostrarMensaje("\n\nLa contrasenia generada es: " + tarea.getContrasenia());

		boolean sesionIniciada = false;

		String contraseniaIngresada = "";

		do {
			
			contraseniaIngresada = ingresarString("\n Ingrese la contrasenia para continuar:");
			sesionIniciada = tarea.iniciarSesion(contraseniaIngresada);
		} while (!sesionIniciada);
		
		do {

			mostrarMenuPrincipal();
			opcion = ingresarOpcionElegida();

			switch (opcion) {
			case CREAR_CANBOT:
				crearCanBot(tarea);
				break;
			case ACTIVAR_CANBOT:
				activarCanBot(tarea);
				break;
			case DESACTIVAR_CANBOT:
				desactivarCanBot(tarea);
				break;
			case MODIFICAR_CANBOT:
				modificarCanBot(tarea);
				break;
			case MOSTRAR_CANBOTS:
				tarea.mostrarCanBots();
				break;
			case ASIGNAR_MISION:
				asignarMision(tarea);
				break;
			case CARGAR_CANBOT:
				cargarCanbot(tarea);
			case SALIR:
				mostrarMensaje("\n Hasta luego!");
				break;
			case TERMINAR_MISIONES:
				
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
	 * Crea el CanBot, Agregando un elemento al array.
	 *
	 */
	private static void crearCanBot(Tareas tarea) {
		int id;
		do {

			id = ingresarEntero("Ingrese ID del CanBot:");
			if (id < 1) {
				mostrarError("El ID debe ser positivo.");

				/// Validar ID existente
			}
		} while (id < 1);

		String nombre = ingresarString("Ingrese nombre del CanBot:");

		int bateria;
		do {

			bateria = ingresarEntero("Ingrese nivel de batería (0-100):");
			if (bateria < 0 || bateria > 100) {
				mostrarError("Nivel de batería inválido.");
			}
		} while (bateria < 0 || bateria > 100);

		TipoMision tipoMision = elegirTipoMision();

		CanBot nuevo = new CanBot(id, nombre, bateria, tipoMision);
		if (tarea.agregarCanBot(nuevo)) {
			mostrarMensaje("CanBot agregado correctamente.");
		} else {
			mostrarError("No se pudo agregar (ya existe o está lleno).");
		}
	}

	/**
	 * Activa el CanBot, Buscando por id dentro del array.
	 *
	 */
	private static void activarCanBot(Tareas tarea) {
		int id = ingresarEntero("Ingrese ID del CanBot a activar:");

		if (tarea.activarCanBot(id)) {
			mostrarMensaje("CanBot activado correctamente.");
		} else {
			mostrarError("No se encontró el CanBot.");
		}
	}

	/**
	 * Desactiva el CanBot, Buscando por id dentro del array.
	 *
	 */
	private static void desactivarCanBot(Tareas tarea) {

		int id = ingresarEntero("Ingrese ID del CanBot a desactivar:");

		if (tarea.desactivarCanBot(id)) {
			mostrarMensaje("CanBot eliminado correctamente.");
		} else {
			mostrarError("No se encontró el CanBot.");
		}
	}

	/**
	 * Modifica el CanBot, Buscando por id dentro del array.
	 *
	 */
	private static void modificarCanBot(Tareas tarea) {

		int id = ingresarEntero("Ingrese ID del CanBot a modificar:");

		String nombre = ingresarString("Nuevo nombre:");

		int bateria = ingresarEntero("Nueva batería (0-100):");

		if (bateria < 0 || bateria > 100) {
			mostrarError("Nivel de batería inválido.");
			return;
		}

		TipoMision tipoMision = elegirTipoMision();

		if (tarea.modificarCanBot(id, nombre, bateria, tipoMision)) {
			mostrarMensaje("CanBot modificado exitosamente.");
		} else {
			mostrarError("CanBot no encontrado.");
		}
	}

	/**
	 * Asigna mision al CanBot, Buscando por id dentro del array.
	 *
	 */
	private static void asignarMision(Tareas tarea) {

		int id = ingresarEntero("Ingrese ID del CanBot:");

		TipoMision tipoMision = elegirTipoMision();

		if (tarea.asignarMision(id, tipoMision)) {
			mostrarMensaje("Misión asignada correctamente.");
		} else {
			mostrarError("Error al asignar misión. Verifique el estado del CanBot.");
		}
	}

	// cargar el canbot

	private static void cargarCanbot(Tareas tarea) {
		int id = ingresarEntero("ingrese el id del canbot a cargar");
		tarea.cargarBot(id);
	}
	//terminar misiones asignadas
	
	private static void terminarMisiones(Tareas tarea) {
		int id = ingresarEntero("ingrese el id del canbot a cargar");
		
	}

	/**
	 * Metodos del menu principal.
	 * 
	 */

	private static MenuPrincipal ingresarOpcionElegida() {
		int opcion = 0;
		do {
			opcion = ingresarEntero("Ingrese la opcion deseada");

		} while (opcion < 1 || opcion > MenuPrincipal.values().length);

		return MenuPrincipal.values()[opcion - 1];
	}

	private static void mostrarMenuPrincipal() {
		String menu = "--- MENÚ DE GESTIÓN DE BOTS MILITARES --- \n";
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

	// Ingresar mensaje

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
