package hn.grupo1.controller;

import hn.grupo1.data.Empleados;
import hn.grupo1.data.EmpleadosResponse;
import hn.grupo1.views.empleados.EmpleadosViewModel;
import hn.grupo1.repository.DatabaseRepositoryImpl;

public class EmpleadosInteractorImpl implements EmpleadosInteractor {
	

	private DatabaseRepositoryImpl modelo;
	private EmpleadosViewModel vista;
	
	public EmpleadosInteractorImpl (EmpleadosViewModel vista) {
		super();
		this.vista = vista;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 3000L);
	}
	
	@Override
	public void consultarEmpleados() {
	    try {
	        EmpleadosResponse respuesta = this.modelo.ConsultarEmpleados();
	        if(respuesta == null || respuesta.getCount() == 0 || respuesta.getItems() == null) {
	            this.vista.mostrarMensajeError("No hay Empleados disponibles");
	        } else {
	            this.vista.mostrarEmpleadosEnGrid(respuesta.getItems());
	        }
	    } catch(Exception error) {
	        error.printStackTrace();
	        this.vista.mostrarMensajeError("Error al consultar los Empleados: " + error.getMessage());
	    }
	}

	@Override
	public void agregarEmpleado(Empleados nuevo) {
		try {
			boolean creado = this.modelo.CrearEmpleados(nuevo);
			if(creado) {
				this.vista.mostrarMensajeExito("Empleado registrado exitosamente!");
			}else {
				this.vista.mostrarMensajeError("Hay un problema al Registrar el empleado");
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	@Override
	public void editarEmpleado(Empleados existente) {
		try {
			boolean actualizado = this.modelo.ActualizarEmpleados(existente);
			if(actualizado) {
				this.vista.mostrarMensajeExito("¡Empleado modificado exitosamente!");
			}else {
				this.vista.mostrarMensajeError("Hay un problema al modificar el Empleado");
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
	}
}
	
	

