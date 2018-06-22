package co.com.s4n.semillero.ejercicio.dominio.entidades;

import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Posicion {
    @Getter
    private int x;
    @Getter
    private int y;
    @Getter
    private Direccion direccion;

}
