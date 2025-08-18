package model;

import java.util.List;

public abstract class CartaSorpresa {
        private String descripcion;
    public CartaSorpresa(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "CartaSorpresa{" +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public abstract void ejecutarAccion(Jugador jugador, Tablero tablero);
}
