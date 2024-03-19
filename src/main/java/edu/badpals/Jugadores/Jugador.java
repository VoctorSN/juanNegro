package edu.badpals.Jugadores;

import edu.badpals.Cartas.Carta;

import java.util.ArrayList;

public class Jugador {
    private ArrayList<Carta> cartas = new ArrayList<>();
    private String nombre;
    private int dinero = 700;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void addDinero(int cantidad){
        this.dinero += cantidad;
    }

    public void loseDinero(int cantidad){
        this.dinero -= cantidad;
    }

    public void mostrarCartas(){
        String out = "Las cartas de " + this.getNombre() + " son :";
        for (Carta carta : this.getCartas()){
            out += carta + " ";
        }
        System.out.println(out);
    }

    public int getPuntuacion(){
        int puntuacion = 0;
        for (Carta carta : cartas){
            puntuacion += carta.getPuntuacion();
        }
        return puntuacion;
    }

    public void addCarta(Carta carta){
        getCartas().add(carta);
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }
}
