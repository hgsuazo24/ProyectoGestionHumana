package hn.grupo1.controller;

import hn.grupo1.data.EmpleadosResponse;
import hn.grupo1.repository.DatabaseRepositoryImpl;
import hn.grupo1.views.consultas.ConsultasViewModel;


public class ConsultasInteractorImpl implements ConsultasInteractor {
	
	private DatabaseRepositoryImpl modelo;
	private ConsultasViewModel vista;
	
	public ConsultasInteractorImpl(ConsultasViewModel vista) {
		super();
		this.vista = vista;
		this.modelo=DatabaseRepositoryImpl.getInstance("https://apex.oracle.com/",3000L);					
	}
	

		@Override
		public void ConsultarEmpleados() {
			try {
				EmpleadosResponse respuesta =this.modelo.ConsultarEmpleados();
				if(respuesta==null|| respuesta.getCount()==0||respuesta.getItems()==null) {
					this.vista.mostrarMensajeError("No hay empleados");
									
				}else {
					this.vista.mostrarEmpleadosEnComboBox(respuesta.getItems());
					
				}
				
			}catch(Exception error){
				error.printStackTrace();
				
			}
			
		}
		
	}




