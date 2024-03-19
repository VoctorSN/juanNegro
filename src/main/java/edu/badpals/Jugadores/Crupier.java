package edu.badpals.Jugadores;

import edu.badpals.Cartas.Carta;

import java.util.ArrayList;

public class Crupier {
    ArrayList<Carta> cartas = new ArrayList<>();

    public int getPuntuacion(){
        int puntuacion = 0;
        for (Carta carta : cartas){
            puntuacion += carta.getPuntuacion();
        }
        return puntuacion;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    public void addCarta(Carta carta){
        getCartas().add(carta);
    }

    public Carta getCarta(){
        return cartas.getFirst();
    }
}
