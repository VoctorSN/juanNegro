package edu.badpals.Jugadores;

import edu.badpals.Cartas.Carta;

import java.util.ArrayList;

public class Crupier {
    ArrayList<Carta> cartas = new ArrayList<>();

    public int playerNumAses(){
        int numAs = 0;
        for (Carta carta : cartas){
            if (carta.hasAs()){
                numAs += 1;
            }
        }
        return numAs;
    }

    public int getPuntuacion(){
        int puntuacion = 0;
        for (Carta carta : cartas){
            puntuacion += carta.getPuntuacion();
        }
        int numeroAses = this.playerNumAses();
        while (puntuacion > 21 && numeroAses != 0){
            puntuacion -= 10;
            numeroAses -= 1;
        }
        return puntuacion;
    }

    public void mostrarCartas(){
        String out = "\nLas cartas del crupier son: ";
        for (Carta carta : this.getCartas()){
            out += carta + " ";
        }
        System.out.println(out);
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
