package monopoly.model;

public class Carta {

    private final String texto;
    private final TipoAccion accion;

    public Carta(String texto, TipoAccion accion) {
        this.texto = texto;
        this.accion = accion;
    }

    public String getTexto() {
        return texto;
    }

    public TipoAccion getAccion() {
        return accion;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "texto='" + texto + '\'' +
                ", accion=" + accion +
                '}';
    }
}
