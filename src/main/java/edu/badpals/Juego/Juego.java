package edu.badpals.Juego;

import edu.badpals.Cartas.Carta;
import edu.badpals.Cartas.Cartas;
import edu.badpals.Jugadores.Crupier;
import edu.badpals.Jugadores.Jugador;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego {
    private Cartas cartas;
    private Crupier crupier;
    private ArrayList<Jugador> jugadores;
    private int cantidadJugadores = 0;

    private void initialize(){
        this.jugadores = new ArrayList<>();
        cartas = new Cartas();
        crupier = new Crupier();
    }

    public void jugar(){
        while (true){
            this.initialize();
            System.out.println("\n¿Quieres Jugar?(S/n)\n");
            if (new Scanner(System.in).nextLine().equalsIgnoreCase("n"))
                break;
            this.initializeJugadores();
            jugarRonda();

        }
    }

    private void jugarRonda() {
        getCartas().barajar();
        this.repartir();
        this.repartir();
        mostrarCartasJugadores();
        mostrarCartasCrupier();
        this.preguntarCartaJugadores();
        this.cojerCartasCrupier();
        this.verCartas();
    }


    public void verCartas(){
        crupier.mostrarCartas();
        if (crupier.getPuntuacion() > 21){
            System.out.println("Todos Ganasteis, el crupier se paso");
            return;
        }
        for (Jugador jugador : jugadores){
            if (jugador.getPuntuacion() > crupier.getPuntuacion() && jugador.getPuntuacion() < 21){
                System.out.println("Jugador: " + jugador.getNombre() + "\n");
                mostrarGanaste();
            } else if(jugador.getPuntuacion() < crupier.getPuntuacion()){
                System.out.println("Jugador " + jugador.getNombre() + "\n");
                mostrarPerdiste();
            }
        }
    }
    public void cojerCartasCrupier(){
        while (this.maximaPuntuacion() > crupier.getPuntuacion()){
            crupier.addCarta(this.cartas.primeraCarta());
        }
    }

    public int maximaPuntuacion(){
        int maximaPuntuacion = 0;
        for (Jugador jugador:jugadores){
            if (jugador.getPuntuacion() > maximaPuntuacion && jugador.getPuntuacion() <= 21){
                maximaPuntuacion = jugador.getPuntuacion();
            }
        }
        return maximaPuntuacion;
    }

    public void initializeJugadores(){

        Integer numJugadores = preguntarCantidadJugadores();
        cantidadJugadores += numJugadores;
        for (int i=0 ; i<cantidadJugadores ; i++) {
            System.out.println("¿Como te llamas?");
            jugadores.add(new Jugador(new Scanner(System.in).nextLine()));
        }
    }

    private Integer preguntarCantidadJugadores(){
        Integer numJugadores = 5;
        while (numJugadores > 4) {
            try {
                System.out.println("¿Cuantos vais a jugar?, maximo 4");
                String respuesta = new Scanner(System.in).nextLine();
                numJugadores = Integer.parseInt(respuesta);
                if (numJugadores > 4) {
                    System.out.println("No pueden jugar mas de 4");
                }
            } catch (Exception as ){
                System.out.println("No es un numero correcto");
            }
        }
        return numJugadores;
    }


    public void preguntarCartaJugadores() {
        for (Jugador jugador : jugadores) {
            preguntarCartaJugador(jugador);

        }
    }

    private void preguntarCartaJugador(Jugador jugador) {
        System.out.println("\n\nJugador " + jugador.getNombre());
        jugador.mostrarCartas();
        jugador.mostrarPuntuacion();
        System.out.println("¿Quieres Carta o quieres mirar?(CARTA/parar)\n");
        String respuesta = new Scanner(System.in).nextLine();
        if (!respuesta.equalsIgnoreCase("parar")) {
            Carta siguienteCarta = cartas.primeraCarta();
            jugador.addCarta(siguienteCarta);
            jugador.mostrarCartas();
            if (jugador.getPuntuacion()>21){
                mostrarPerdiste();
                jugador.setVivo(false);
            } else preguntarCartaJugador(jugador);
        }
    }

    public static void mostrarPerdiste(){
        System.out.println("PERDISTE :(");
    }

    private void mirarCartas(Jugador jugador){
        if (jugador.getPuntuacion() > crupier.getPuntuacion() && crupier.getPuntuacion()>16 && crupier.getPuntuacion()<21){
            mostrarGanaste();
        } else if (crupier.getPuntuacion()>21){
            this.mostrarGanaste();
            System.out.println("El crupier se paso");
        } else if (crupier.getPuntuacion() > jugador.getPuntuacion()) {
            mostrarPerdiste();
            System.out.println("El crupier te gano");
        } else {
            crupier.addCarta(cartas.primeraCarta());
            this.mirarCartas(jugador);
        }
    }

    private static void mostrarGanaste() {
        System.out.println("¡GANASTE!");
    }

    public Cartas getCartas() {
        return cartas;
    }

    public void mostrarCartasJugadores(){
        for (Jugador jugador : jugadores){
            jugador.mostrarCartas();
        }
    }


    public void mostrarCartasCrupier(){
        System.out.println("Carta Crupier: " + crupier.getCarta() + "¿?");
    }


    public void repartir() {
            for (Jugador jugador : jugadores){
                Carta nextCarta = getCartas().primeraCarta();
                jugador.addCarta(nextCarta);
            }
            Carta nextCarta = getCartas().primeraCarta();
            crupier.addCarta(nextCarta);
    }
}
