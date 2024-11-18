package hn.grupo1.views.empleados;

import com.vaadin.flow.component.Text;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import hn.grupo1.controller.EmpleadosInteractor;
import hn.grupo1.controller.EmpleadosInteractorImpl;
import hn.grupo1.data.Cursos;
import hn.grupo1.data.Empleados;
import hn.grupo1.views.cursos.CursosView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Empleados")
@Route("master-detail-empleados/:empleadosID?/:action?(edit)")
@RouteAlias("Empleados")  
@Menu(order = 1, icon = "line-awesome/svg/user-nurse-solid.svg")



public class EmpleadosView extends Div implements BeforeEnterObserver, EmpleadosViewModel {

    private final String EMPLEADOS_ID = "empleadosID";
    private final String EMPLEADOS_EDIT_ROUTE_TEMPLATE = "/%s/edit";

    private final Grid<Empleados> grid = new Grid<>(Empleados.class, false);

    //private TextField empleadoID;
    private TextField nombre;
    private TextField apellido;
    private TextField email;
    //private TextField departamentoID;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    private Empleados empleado;
    private List<Empleados> empleados;
    private EmpleadosInteractor controlador;

    public EmpleadosView() {
        addClassNames("empleados-view");

        controlador = new EmpleadosInteractorImpl(this);
        empleados = new ArrayList<>();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        // Configure Grid
        //grid.addColumn(Empleados::getEmpleadoID).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Empleados::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Empleados::getApellido).setHeader("Apellido").setAutoWidth(true);
        grid.addColumn(Empleados::getEmail).setHeader("Correo").setAutoWidth(true);
       // grid.addColumn(Empleados::getDepartamentoID).setHeader("Departamento ID").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // When a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            Empleados selectedEmpleado = event.getValue();
            if (selectedEmpleado != null) {
                System.out.println("ID seleccionado: " + selectedEmpleado.getEmpleadoID());
                populateForm(selectedEmpleado);
                UI.getCurrent().navigate(String.format(EMPLEADOS_EDIT_ROUTE_TEMPLATE, selectedEmpleado.getEmpleadoID()));
            } else {
                System.out.println("No se seleccionó ningún empleado");
                clearForm();
                UI.getCurrent().navigate(EmpleadosView.class);
            }
        });

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.empleado == null) {
                    this.empleado = new Empleados();
                }
                this.empleado.setNombre(nombre.getValue());
                this.empleado.setApellido(apellido.getValue());
                this.empleado.setEmail(email.getValue());
                //this.empleado.setDepartamentoID(Integer.parseInt(departamentoID.getValue()));

                if (empleado.getEmpleadoID() == null) {
                    this.controlador.agregarEmpleado(empleado);
                } else {
                    this.controlador.editarEmpleado(empleado);
                }

                clearForm();
                refreshGrid();
                UI.getCurrent().navigate(EmpleadosView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                    "Error al actualizar los datos. Otra persona modificó el registro mientras estabas realizando cambios.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        
        controlador.consultarEmpleados();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> empleadosIdparam = event.getRouteParameters().get(EMPLEADOS_ID);
        if (empleadosIdparam.isPresent()) {
            try {
                Integer empleadoId = Integer.parseInt(empleadosIdparam.get());
                Empleados empleado = obtenerEmpleado(empleadoId);
                if (empleado != null) {
                    populateForm(empleado);
                } else {
                    Notification.show("No se encontró el Empleado con ID " + empleadoId, 
                        3000, Position.MIDDLE);
                    clearForm();
                    refreshGrid();
                    event.forwardTo(EmpleadosView.class);
                }
            } catch (NumberFormatException e) {
                Notification.show("ID de Empleado inválido", 
                    3000, Position.MIDDLE);
                clearForm();
                refreshGrid();
                event.forwardTo(EmpleadosView.class);
            }
        }
    }

    private Empleados obtenerEmpleado(Integer id) {
        for (Empleados al : empleados) {
            if (al.getEmpleadoID().equals(id)) {
                return al;
            }
        }
        return null;
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();

        nombre = new TextField("Nombre");
        nombre.setClearButtonVisible(true);
        nombre.setPrefixComponent(VaadinIcon.CLIPBOARD_USER.create());

        apellido = new TextField("Apellido");
        apellido.setClearButtonVisible(true);
        apellido.setPrefixComponent(VaadinIcon.CLIPBOARD_USER.create());

        email = new TextField("Email");
        email.setClearButtonVisible(true);
        email.setPrefixComponent(VaadinIcon.CLIPBOARD_USER.create());


        formLayout.add( nombre, apellido, email);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Empleados value) {
        this.empleado = value;
        if (value == null) {
            nombre.setValue("");
            apellido.setValue("");
            email.setValue("");
            // Deshabilitar campos cuando no hay selección
            nombre.setReadOnly(true);
            apellido.setReadOnly(true);
            email.setReadOnly(true);
            save.setEnabled(false);
        } else {
            nombre.setValue(value.getNombre());
            apellido.setValue(value.getApellido());
            email.setValue(value.getEmail());
            // Habilitar campos cuando hay selección
            nombre.setReadOnly(false);
            apellido.setReadOnly(false);
            email.setReadOnly(false);
            save.setEnabled(true);
        }
       // empleadoID.setReadOnly(true); // El ID siempre será de solo lectura
    }

    @Override
    public void mostrarEmpleadosEnGrid(List<Empleados> items) {
        //Collection<Empleados> itemsCollection = items;
        //this.empleados = items;
       // grid.setItems(itemsCollection);
        if (items != null) {
            System.out.println("Empleados cargados: " + items.size());
        }
        this.empleados = items;
        grid.setItems(items);
    }

    public void mostrarMensajeError(String mensaje) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(mensaje));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }
    
    @Override
	public void mostrarMensajeExito(String mensaje) {
		Notification notification = Notification.show(mensaje);
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		notification.open();
	}
}
