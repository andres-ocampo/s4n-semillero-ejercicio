package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.archivos.servicios.ServicioManejoArchivo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.*;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Movimiento;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public static Try<String> iniciarDespachoVeinteDrones(){
        List<Try<List<Entrega>>> totalEntregas = ServicioEntrega.cargarRuta();
        List<List<Entrega>> entregas = validarEntregas(totalEntregas);
        List<List<Dron>> totalReportes = List.of();
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (List<Entrega> entregasDron : entregas) {
            List<List<Entrega>> entregasPorViajeDron = entregasDron.grouped(10).collect(List.collector());
            Future<List<Dron>> listaDronesFuturo = iniciarFuturo(service, entregasPorViajeDron);
            totalReportes = totalReportes.append(listaDronesFuturo.getOrElse(List.of(new Dron())));
            listaDronesFuturo.await();
        }
        ServicioManejoArchivo.escribirArchivo(totalReportes);
        return Try.of(()->"");
    }

    private static Future<List<Dron>> iniciarFuturo(ExecutorService service, List<List<Entrega>> entregasPorViajeDron) {
        return Future.of(service, () -> {
            List<Dron> reporte = List.of();
            Dron dron = new Dron(1, new Posicion(0, 0, Direccion.NORTE), List.empty());
            for (List<Entrega> l : entregasPorViajeDron) {
                Try<Dron> dronTry = Try.of(() -> dron);
                for (Entrega e : l) {
                    dronTry = realizarEntrega(dronTry.getOrElse(dron), e);
                    reporte = reporte.append(dronTry.getOrElse(dron));
                }
            }
            return reporte;
        });
    }

    private static List<List<Entrega>> validarEntregas(List<Try<List<Entrega>>> entregas){
        return entregas.map(a -> a.getOrElse(List.of(new Entrega())));
    }

    public static List<Try<List<Entrega>>> cargarRuta(){
        List<Try<List<String>>> archivos = ServicioManejoArchivo.leerArchivoVeinteDrones();
        List<Try<List<Entrega>>> totalEntregas = archivos.map(a -> a.flatMap(b -> Try.of(() -> b.map(c -> {
            Entrega entregaDron = new Entrega(true,
                    new Almuerzo(1, "Arroz Chino"),
                    ServicioEntrega.charToMovimiento(c.toCharArray()));
            return entregaDron;
        }))));
        return totalEntregas;
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
