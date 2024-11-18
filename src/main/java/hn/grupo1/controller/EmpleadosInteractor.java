package hn.grupo1.controller;

import hn.grupo1.data.Empleados;

public interface EmpleadosInteractor {
	
	void consultarEmpleados();
	void agregarEmpleado(Empleados nuevo);
	void editarEmpleado(Empleados existente);
}
