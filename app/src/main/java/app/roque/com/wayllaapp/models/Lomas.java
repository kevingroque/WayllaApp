package app.roque.com.wayllaapp.models;

public class Lomas {

    private String nombre;
    private String descripcion;
    private Float elevacion;
    private String imagen;
    private Float latitud;
    private Float longitud;
    private String ubicacion;

    public Lomas() {
    }

    public Lomas(String nombre, String descripcion, Float elevacion, String imagen, Float latitud, Float longitud, String ubicacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.elevacion = elevacion;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubicacion = ubicacion;
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

    public Float getElevacion() {
        return elevacion;
    }

    public void setElevacion(Float elevacion) {
        this.elevacion = elevacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Lomas{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", elevacion=" + elevacion +
                ", imagen='" + imagen + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
