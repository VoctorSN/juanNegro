package edu.badpals.Jugadores;

import edu.badpals.Cartas.Carta;

import java.util.ArrayList;

public interface Persona {

    ArrayList<Carta> getCartas();
    int getPuntuacion();
    void addCarta(Carta carta);
    boolean gotBlackjack();


}
