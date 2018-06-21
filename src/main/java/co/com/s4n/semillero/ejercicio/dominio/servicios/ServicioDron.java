package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.archivos.servicios.ServicioManejoArchivo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.*;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;

public class ServicioDron {

    static public Try<Dron> avanzar(Dron dron, Barrio barrio){
        int x = dron.getPosicion().getX();
        int y = dron.getPosicion().getY();
        switch (dron.getPosicion().getDireccion()) {
            case NORTE:
                y += 1;
                break;
            case ESTE:
                x += 1;
                break;
            case SUR:
                y -= 1;
                break;
            case OESTE:
                x -= 1;
                break;
            default:
                return null;
        }
        Posicion posicion = new Posicion(x,y,dron.getPosicion().getDireccion());
        if(posicion.getX() > barrio.limite || posicion.getY() > barrio.limite)
            return Try.failure(new Exception());
        return Try.of(() -> new Dron(dron.getId(),posicion,dron.getEntregas()));
    }

    static public Try<Dron> girarDerecha(Dron dron){
        Direccion d = dron.getPosicion().getDireccion();
        switch (d){
            case NORTE:
                d = Direccion.ESTE;
                break;
            case ESTE:
                d = Direccion.SUR;
                break;
            case SUR:
                d = Direccion.OESTE;
                break;
            case OESTE:
                d = Direccion.NORTE;
                break;
            default:
                break;
        }
        Posicion posicion = new Posicion(dron.getPosicion().getX(),dron.getPosicion().getY(),d);
        return Try.of(() -> new Dron(dron.getId(),posicion,dron.getEntregas()));
    }

    static public Try<Dron> girarIzquierda(Dron dron){
        Direccion d = dron.getPosicion().getDireccion();
        switch (d){
            case NORTE:
                d = Direccion.OESTE;
                break;
            case ESTE:
                d = Direccion.NORTE;
                break;
            case SUR:
                d = Direccion.ESTE;
                break;
            case OESTE:
                d = Direccion.SUR;
                break;
            default:
                break;
        }
        Posicion posicion = new Posicion(dron.getPosicion().getX(),dron.getPosicion().getY(),d);
        return Try.of(() -> new Dron(dron.getId(),posicion,dron.getEntregas()));
    }

    /*public static Try<Dron> obtenerEntregasDron(Dron dron){
        List<Entrega> entregas = ServicioEntrega.cargarRuta();
        List<List<Entrega>> entregasDron = entregas.grouped(3).collect(List.collector());
        List<Dron> map = entregasDron.map(listaEntrega -> {
            return new Dron(1, new Posicion(0, 0, Direccion.NORTE), listaEntrega);
        });
        return new Dron(dron.getId(),dron.getPosicion(),);

    }*/


}
