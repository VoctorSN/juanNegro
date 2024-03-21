package edu.badpals.Juego;

import edu.badpals.Cartas.Carta;
import edu.badpals.Cartas.Cartas;
import edu.badpals.Jugadores.Crupier;
import edu.badpals.Jugadores.Jugador;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
        boolean isJugando = true;
        while (isJugando){
            isJugando = quiereJugar();
            esperarSegundosYLimpiar(1);
            this.initialize();
            this.initializeJugadores();
            esperarSegundosYLimpiar(5);
            jugarRonda();

        }
    }

    private static void esperarSegundosYLimpiar(int segundos){
        try {
            TimeUnit.SECONDS.sleep(segundos);
            limpiarPantalla();
        } catch (InterruptedException e){
            System.out.println("Algo ha ocurrido con el metodo sleep");
        }
    }

    private static boolean quiereJugar() {
        System.out.println("\n¿Quieres Jugar?(S/n)");
        if (new Scanner(System.in).nextLine().equalsIgnoreCase("n"))
            return false;
        return true;
    }

    private static void limpiarPantalla(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
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
        System.out.println();
        if (crupier.getPuntuacion() > 21){
            for (Jugador jugador : jugadores){
                System.out.println("Jugador: " + jugador);
                if (jugador.isVivo()){
                    mostrarGanaste();
                } else {
                    mostrarPerdiste();
                }
                System.out.println();
            }
        } else {
            for (Jugador jugador : jugadores){
                System.out.println("Jugador: " + jugador);
                if (jugador.getPuntuacion()>crupier.getPuntuacion()){
                    mostrarPerdiste();
                }
                else {
                    mostrarGanaste();
                }
                System.out.println();
            }
        }
        /*
        for (Jugador jugador : jugadores){
            if (jugador.getPuntuacion() > crupier.getPuntuacion() && jugador.isVivo()){
                System.out.println( + jugador.getNombre() + "\n");
                mostrarGanaste();
            } else if(jugador.getPuntuacion() < crupier.getPuntuacion()){
                System.out.println("Jugador " + jugador.getNombre() + "\n");
                mostrarPerdiste();
            }
        } */
    }
    public void cojerCartasCrupier(){
        while (crupier.getPuntuacion() < 17){
            crupier.addCarta(this.cartas.primeraCarta());
        }
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
            preguntarCartaJugadorInformar(jugador);

        }
    }

    private void preguntarCartaJugadorInformar(Jugador jugador) {
        System.out.println();
        jugador.mostrarCartas();
        jugador.mostrarPuntuacion();
        preguntarCartaJugador(jugador);
    }

    private void preguntarCartaJugador(Jugador jugador) {
        System.out.println("¿Quieres Carta o quieres parar?(CARTA/parar)\n");
        String respuesta = new Scanner(System.in).nextLine();
        if (!respuesta.equalsIgnoreCase("parar")) {
            Carta siguienteCarta = cartas.primeraCarta();
            jugador.addCarta(siguienteCarta);
            jugador.mostrarCartas();
            jugador.mostrarPuntuacion();
            if (jugador.getPuntuacion()>21){
                mostrarPerdiste();
                jugador.setVivo(false);
            } else preguntarCartaJugador(jugador);
        }
    }

    public static void mostrarPerdiste(){
        System.out.println("PERDISTE :(");
    }

    private static void mostrarGanaste() {
        System.out.println("¡GANASTE!");
    }

    public Cartas getCartas() {
        return cartas;
    }

    public void mostrarCartasJugadores(){
        System.out.println();
        for (Jugador jugador : jugadores){
            jugador.mostrarCartas();
        }
    }


    public void mostrarCartasCrupier(){
        System.out.println("Carta Crupier: " + crupier.getCarta() + " ¿?");
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
