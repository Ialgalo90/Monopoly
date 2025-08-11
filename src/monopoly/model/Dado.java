package monopoly.model;

public class Dado {

    public static int lanzar() {
        return (int)(Math.random()*6) + 1;
    }

    public static int lanzarDados() {
        return lanzar() + lanzar();
    }
}
