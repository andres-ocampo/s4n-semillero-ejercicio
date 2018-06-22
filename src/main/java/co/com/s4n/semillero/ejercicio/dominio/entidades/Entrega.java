package co.com.s4n.semillero.ejercicio.dominio.entidades;

import co.com.s4n.semillero.ejercicio.dominio.vo.Movimiento;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Entrega {

    @Getter
    private boolean activa;
    @Getter
    private Almuerzo almuerzo;
    @Getter
    private List<Movimiento> movimientos;

}
