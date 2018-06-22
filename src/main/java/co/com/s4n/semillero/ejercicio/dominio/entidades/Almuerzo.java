package co.com.s4n.semillero.ejercicio.dominio.entidades;
import lombok.Getter;

public class Almuerzo {

    @Getter
    private int id;
    @Getter
    private String nombre;

    public Almuerzo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
