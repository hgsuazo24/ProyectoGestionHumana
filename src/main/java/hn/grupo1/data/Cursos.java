package hn.grupo1.data;



public class Cursos extends AbstractEntity {

    private Integer cursoid;
    private String nombre;
    private String descripcion;

    public Integer getCursoid() {
        return cursoid;
    }
    public void setCursoid(Integer cursoid) {
        this.cursoid = cursoid;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
