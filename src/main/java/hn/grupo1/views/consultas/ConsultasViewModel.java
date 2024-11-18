package hn.grupo1.views.consultas;

import java.util.List;

import hn.grupo1.data.Empleados;



public interface ConsultasViewModel {
	void mostrarEmpleadosEnComboBox(List<Empleados> items);//Modifique list Empleados
	void mostrarMensajeError(String mensaje);

}