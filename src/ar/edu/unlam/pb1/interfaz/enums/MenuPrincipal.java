package ar.edu.unlam.pb1.interfaz.enums;

public enum MenuPrincipal {

	CREAR_CANBOT("Crear nuevo Canbot"), RESETEAR_CANBOT("Resetear Canbot"), DESACTIVAR_CANBOT("Desactivar Canbot"),
	MODIFICAR_CANBOT("Modificar Canbot"), MOSTRAR_CANBOTS("Mostrar Canbots"), ASIGNAR_MISION("Asignar mision"),
	CARGAR_CANBOT("Recargar Bateria de Canbot"), TERMINAR_MISIONES("Finalizar misiones asignadas"),
	ESTADISTICAS("Ver estadísticas de los Canbots"), MOSTRAR_RECONOCIMIENTO("Mostrar Canbots de Reconocimiento"),
	MOSTRAR_TRANSPORTE("Mostrar CanBots de Transporte"), SALIR("Salir");

	private String descripcion;

	MenuPrincipal(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
}