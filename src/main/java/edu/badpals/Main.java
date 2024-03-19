package edu.badpals;

import edu.badpals.Cartas.Carta;
import edu.badpals.Cartas.Cartas;
import edu.badpals.Juego.Juego;

public class Main {
    public static void main(String[] args) {
        Carta carta = new Carta("QP");
        System.out.println(carta);

        Cartas baraja = new Cartas();
        System.out.println(baraja);
        baraja.barajar();
        System.out.println(baraja);

        Juego partida = new Juego();
        partida.jugar();
    }
}