package ar.edu.unlam.pb1.enums;

public enum MenuPrincipal {

	CREAR_CANBOT("Crear nuevo Canbot"), ACTIVAR_CANBOT("Activar Canbot"), DESACTIVAR_CANBOT("Desactivar Canbot"),
	MODIFICAR_CANBOT("Modificar Canbot"), MOSTRAR_CANBOTS("Mostrar Canbots"), ASIGNAR_MISION("Asignar mision"),
	CARGAR_CANBOT("cargar bot"), TERMINAR_MISIONES("realizar misiones asignadas"), SALIR("Salir");

	private String descripcion;

	MenuPrincipal(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
}