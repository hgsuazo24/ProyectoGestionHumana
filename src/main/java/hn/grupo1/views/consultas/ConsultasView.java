


package hn.grupo1.views.consultas;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import hn.grupo1.data.Empleados;
import hn.grupo1.controller.ConsultasInteractor;
import hn.grupo1.controller.ConsultasInteractorImpl;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@PageTitle("Consultas")
@Route("consultas")
@Menu(order = 2, icon = "line-awesome/svg/search-solid.svg")
public class ConsultasView extends Composite<VerticalLayout>  implements ConsultasViewModel {

	  
	
	private ComboBox<Empleados> empleado;
	private List<Empleados> empleados;
    private TextField txtNombre;
    private TextField txtApellido;
    private TextField txtEmail;
   
    private ConsultasInteractor controlador; 
    public ConsultasView() {
    	
    	controlador=new ConsultasInteractorImpl(this);
    	empleados=new ArrayList<>();
    	
    	
    	VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3("Información de Empleados");
        empleado = new ComboBox<>("Empleado");
        txtNombre = new TextField("Nombre");
        txtApellido = new TextField("Apellido");
        txtEmail = new TextField("Correo");
        txtNombre.setReadOnly(true);
        txtApellido.setReadOnly(true);
        txtEmail.setReadOnly(true);
        
        FormLayout formLayout2Col = new FormLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button("BUSCAR");
        Button buttonSecondary = new Button("LIMPIAR");

        // Configuración del layout principal
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");

        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
   
        // Configuración del formulario
        
        formLayout2Col.setWidth("100%");
        formLayout2Col.add(txtNombre, txtApellido, txtEmail);

        // Configuración de botones
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(e -> {
            Empleados selected = empleado.getValue();
            if (selected != null) {
              Notification.show("Empleado seleccionado: " + selected.getNombre());
            } else {
                Notification.show("Por favor seleccione un empleado.");
            }
        });

        buttonSecondary.addClickListener(e -> {
            empleado.clear();
            txtNombre.clear();
            txtApellido.clear();
            txtEmail.clear();
        });

        layoutRow.add(buttonPrimary, buttonSecondary);

        // Agregar componentes al layout
        layoutColumn2.add(h3, empleado, formLayout2Col, layoutRow);
        getContent().add(layoutColumn2);
        
        controlador.ConsultarEmpleados();
        
    }


	@Override
	public void mostrarEmpleadosEnComboBox(List<Empleados> items) {
		Collection<Empleados> itemsCollection = items;
		this.empleados = items;
		empleado.setItems(itemsCollection);
		empleado.setItemLabelGenerator(
		        person -> person.getNombre() + " " + person.getApellido());
		//Prueba para cargar
		
		empleado.addValueChangeListener(event -> { Empleados selected =
				  empleado.getValue(); if (selected != null) {
				  txtNombre.setValue(selected.getNombre());
				  txtApellido.setValue(selected.getApellido()); //
				  txtEmail.setValue(selected.getEmail()); 
				  }
				  else { txtNombre.clear();
				  txtApellido.clear(); txtEmail.clear(); } });
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		// TODO Auto-generated method stub
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

		Div text = new Div(new Text(mensaje));

		Button closeButton = new Button(new Icon("lumo", "cross"));
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		closeButton.setAriaLabel("Close");
		closeButton.addClickListener(event -> {
		    notification.close();
		});

		HorizontalLayout layout = new HorizontalLayout(text, closeButton);
		layout.setAlignItems(Alignment.CENTER);

		notification.add(layout);
		notification.open();
		
	}	
	

}


