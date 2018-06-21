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
                dronTry = ServicioDron.avanzar(dronTry.getOrElse(dron), new Barrio());
            else if (entrega.getMovimientos().get(i).equals(Movimiento.D))
                dronTry = ServicioDron.girarDerecha(dronTry.getOrElse(dron));
            else dronTry = ServicioDron.girarIzquierda(dronTry.getOrElse(dron));
        }
        return dronTry;
    }
    
    public static void iniciar(){
        List<Entrega> entregas = ServicioEntrega.cargarRuta();
        List<List<Entrega>> entregasDron = entregas.grouped(3).collect(List.collector());
        List<Dron> reporte = List.of();
        Dron dron = new Dron(1,
                new Posicion(0, 0, Direccion.NORTE),
                List.empty());
        Try<Dron> dronTry= Try.of(()->new Dron(1,
                new Posicion(0, 0, Direccion.NORTE),
                List.empty()));
        for (List<Entrega> l:entregasDron) {
            for (Entrega e:l) {
                dronTry = realizarEntrega(dronTry.getOrElse(dron),e);
                reporte = reporte.append(dronTry.getOrElse(dron));
            }
        }
        ServicioManejoArchivo.escribirArchivo(reporte);
    }

    public static List<Entrega> cargarRuta(){
        List<String> archivo = ServicioManejoArchivo.leerArchivo();
        List<Entrega> entregas = archivo.map(a -> {
            List<Movimiento> of = List.of(Movimiento.A);
            Entrega entregaDron = new Entrega(true,
                    new Almuerzo(1, "Arroz Chino"),
                    ServicioEntrega.charToMovimiento(a.toCharArray()));
            return entregaDron;
        }).toList();
        return entregas;
    }

    public static List<Movimiento> charToMovimiento(char[] movs){
        List<Movimiento> movimientos = List.of();
        for (char c:movs) {
            switch (c){
                case 'A': movimientos = movimientos.append(Movimiento.A); break;
                case 'I': movimientos = movimientos.append(Movimiento.I); break;
                case 'D': movimientos = movimientos.append(Movimiento.D); break;
            }
        }
        return movimientos;
    }

}
