package hn.grupo1.data;


public class Empleados extends AbstractEntity {

    private Integer empleadoID;
    private String nombre;
    private String apellido;
    private String email;
    
    public Integer getEmpleadoID() {
        return empleadoID;
    }
    public void setEmpleadoID(Integer empleadoID) {
        this.empleadoID = empleadoID;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
