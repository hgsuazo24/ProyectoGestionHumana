package hn.grupo1.views.empleados;

import java.util.List;

import hn.grupo1.data.Empleados;

public interface EmpleadosViewModel {
	
	void mostrarEmpleadosEnGrid(List<Empleados> items);
	void mostrarMensajeError(String mensaje);
	void mostrarMensajeExito(String mensaje);
}
