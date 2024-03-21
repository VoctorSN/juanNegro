package edu.badpals.Jugadores;

import edu.badpals.Cartas.Carta;

import java.util.ArrayList;

public class Jugador {
    private boolean vivo = true;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private String nombre;
    private int dinero = 700;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.capitalizarNombre();
    }

    public void mostrarPuntuacion(){
        System.out.println("Tu puntuacion es: " + getPuntuacion());
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

    public void capitalizarNombre(){
        String nombre = getNombre();
        if (nombre == null || nombre.isEmpty()) {
            setNombre(nombre);
        }else{
            setNombre(nombre.substring(0, 1).toUpperCase() + nombre.substring(1));
        }

    }

    public void mostrarCartas(){
        String out = "Jugador " + this.getNombre() + " tus cartas son: ";
        for (Carta carta : this.getCartas()){
            out += carta + " ";
        }
        System.out.println(out);
    }

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

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
