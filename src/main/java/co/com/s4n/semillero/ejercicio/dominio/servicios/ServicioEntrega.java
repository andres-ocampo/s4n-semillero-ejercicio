package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.archivos.servicios.ServicioManejoArchivo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.*;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Movimiento;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class ServicioEntrega {

    public static Try<Dron> realizarEntrega(Dron dron, Entrega entrega){

        Try<Dron> dronTry = Try.of(()->dron);
        for (int i = 0; i < entrega.getMovimientos().size(); i++) {
            if (entrega.getMovimientos().get(i).equals(Movimiento.A))
                dronTry = ServicioDron.avanzar(dronTry.getOrElse(new Dron()), new Barrio());
            else if (entrega.getMovimientos().get(i).equals(Movimiento.D))
                dronTry = ServicioDron.girarDerecha(dronTry.getOrElse(new Dron()));
            else dronTry = ServicioDron.girarIzquierda(dronTry.getOrElse(new Dron()));
        }
        return dronTry;
    }
    
    public static Try<String> iniciarDespacho(){
        Try<List<Entrega>> entregasTry = ServicioEntrega.cargarRuta();
        List<Entrega> entregas = validarEntregas(entregasTry);
        List<List<Entrega>> entregasDron = entregas.grouped(3).collect(List.collector());
        List<Dron> reporte = List.of();
        Dron dron = new Dron(1,
                new Posicion(0, 0, Direccion.NORTE),
                List.empty());
        for (List<Entrega> l:entregasDron) {
            Try<Dron> dronTry= Try.of(()->dron);
            for (Entrega e:l) {
                dronTry = realizarEntrega(dronTry.getOrElse(dron),e);
                reporte = reporte.append(dronTry.getOrElse(dron));
            }
        }
        return ServicioManejoArchivo.escribirArchivo(reporte);
    }

    private static List<Entrega> validarEntregas(Try<List<Entrega>> entregas){
        return entregas.isSuccess() ? entregas.getOrElse(List.of()) : List.of(new Entrega());
    }

    public static Try<List<Entrega>> cargarRuta(){
        Try<List<String>> archivo = ServicioManejoArchivo.leerArchivo();
        Try<List<Entrega>> entregas = archivo.flatMap(a -> Try.of(() -> a.map(b -> {
            Entrega entregaDron = new Entrega(true,
                    new Almuerzo(1, "Arroz Chino"),
                    ServicioEntrega.charToMovimiento(b.toCharArray()));
            return entregaDron;
        })));
        return entregas;
    }

    public static List<Movimiento> charToMovimiento(char[] movs){
        List<Movimiento> movimientos = List.of();
        for (char c:movs) {
            switch (c){
                case 'A': movimientos = movimientos.append(Movimiento.A); break;
                case 'I': movimientos = movimientos.append(Movimiento.I); break;
                case 'D': movimientos = movimientos.append(Movimiento.D); break;
                default:  return List.of();
            }
        }
        return movimientos;
    }

}
