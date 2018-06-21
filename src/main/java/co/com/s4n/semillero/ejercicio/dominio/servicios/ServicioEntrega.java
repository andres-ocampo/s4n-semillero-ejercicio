package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.archivos.servicios.ServicioManejoArchivo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Almuerzo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Entrega;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Posicion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;

public class ServicioEntrega {

    public static Try<Dron> realizarEntrega(Dron dron, Entrega entrega){

        Try<Dron> dronTry = Try.of(()->dron);
        for (int i = 0; i < entrega.getRuta().length; i++) {
            if (entrega.getRuta()[i] == 'A') dronTry = ServicioDron.avanzar(dronTry.getOrElse(dron));
            else if (entrega.getRuta()[i] == 'D')
                dronTry = ServicioDron.girarDerecha(dronTry.getOrElse(dron));
            else dronTry = ServicioDron.girarIzquierda(dronTry.getOrElse(dron));
        }
        return dronTry;
    }
    
    public static void iniciar(){
        List<Entrega> entregas = ServicioEntrega.cargarRuta();
        List<List<Entrega>> entregasDron = entregas.grouped(3).collect(List.collector());
        List<Try<Dron>> map = entregas.map(entrega -> {
            return realizarEntrega(new Dron(1,
                            new Posicion(0, 0, Direccion.NORTE),
                            List.empty()),
                    entrega);
        });
    }

    public static List<Entrega> cargarRuta(){
        List<String> archivo = ServicioManejoArchivo.leerArchivo();
        List<Entrega> entregas = archivo.map(a -> {
            Entrega entregaDron = new Entrega(true,
                    new Almuerzo(1, "Arroz Chino"),
                    a.toCharArray());
            return entregaDron;
        }).toList();
        return entregas;
    }

}
