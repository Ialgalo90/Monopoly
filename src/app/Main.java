package app;

import controlador.Juego;
import controlador.JuegoGUI;

import javax.swing.*;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {

        // Preguntamos al usuario que modo prefiere
        String[] options = {"\uD83C\uDFAE Interfaz Gráfica\", \"\uD83D\uDCBB Modo Consola\", \"❌ Salir"};

        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¡Bienvenido a Monopoly!\n¿Cómo quieres jugar?",
                "\uD83C\uDFB2 Monopoly - Selecciona Modo de Juego",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (seleccion) {
            case 0:
                iniciarModoGrafico();
                break;
            case 1:
                iniciarModoConsola();
                break;
            default:
                System.out.println("¡Gracias por jugar!");
                System.exit(0);
                break;
        }
    }

    private static void iniciarModoGrafico() {
        System.out.println("🎮 Iniciando modo gráfico...");

        // Crear controlador especializado para GUI
        JuegoGUI juegoGUI = new JuegoGUI();
        juegoGUI.iniciar();
    }

    private static void iniciarModoConsola() {
        System.out.println("💻 Iniciando modo consola...");

        // Usar el controlador existente
        Juego juego = new Juego();
        juego.iniciar();
    }
}
