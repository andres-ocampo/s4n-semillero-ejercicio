package co.com.s4n.semillero.ejercicio.dominio.entidades;

import lombok.Getter;

public class Entrega {

    @Getter
    private boolean activa;
    @Getter
    private Almuerzo almuerzo;
    @Getter
    private char[] ruta;

    public Entrega(boolean activa, Almuerzo almuerzo, char[] movimientos) {
        this.activa = activa;
        this.almuerzo = almuerzo;
        this.ruta = movimientos;
    }
}
