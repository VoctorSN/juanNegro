package edu.badpals.Juego;

import edu.badpals.Cartas.Carta;
import edu.badpals.Cartas.Cartas;
import edu.badpals.Jugadores.Crupier;
import edu.badpals.Jugadores.Jugador;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Juego {
    private Cartas cartas = new Cartas();
    private final Crupier crupier = new Crupier();
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private int cantidadJugadores = 0;



    public void jugar(){
        boolean isJugando = true;
        limpiarPantalla();
        if (quiereJugar()){
            esperarSegundosYLimpiar(1);
            this.initializeJugadores();
        }
        while (isJugando){
            if (!stillJugadores()){
                System.out.println("No quedan jugadores, gana la banca");
                break;
            }
            esperarSegundosYLimpiar(2);
            jugarRonda();
            if (!stillJugadores()){
                System.out.println("No quedan jugadores, gana la banca");
                break;
            }
            isJugando = quiereJugar();
        }
    }

    public void preguntarRendicion(){
        ArrayList<Jugador> jugadoresDesertores = new ArrayList<>();
        for (Jugador jugador : jugadores){
            System.out.println("Jugador " + jugador.getNombre() + " ¿quieres abandonar? (s/N)");
            if (new Scanner(System.in).nextLine().equalsIgnoreCase("S")){
                jugadoresDesertores.add(jugador);
            }
            System.out.println();
        }
        jugadoresDesertores.forEach(this::eliminarJugador);
    }

    private boolean stillJugadores() {
        return !this.jugadores.isEmpty();
    }

    public void initializeCartas(){
         this.cartas = new Cartas();
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
        return !new Scanner(System.in).nextLine().equalsIgnoreCase("n");
    }

    private static void limpiarPantalla(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n");
    }

    private void jugarRonda() {
        this.preguntarRendicion();
        this.initializeCartas();
        this.getCartas().barajar();
        this.eliminarCartasJugadores();
        this.repartir();
        this.repartir();
        this.mostrarCartasMesa();
        this.preguntarApuestaJugadores();
        this.preguntarCartaJugadores();
        this.cojerCartasCrupier();
        this.verCartas();
        this.eliminarJugadores();
    }

    public void eliminarCartasJugadores(){
        for (Jugador jugador : jugadores){
            jugador.eliminarCartas();
        }
    }

    public void eliminarJugadores(){
        ArrayList<Jugador> jugadoresEliminados = new ArrayList<>();
        for (Jugador jugador : jugadores){
            if (jugador.getDinero() < 1){
                jugadoresEliminados.add(jugador);
            }
        }
        jugadoresEliminados.forEach(this::eliminarJugador);
    }
    private void mostrarCartasMesa() {
        mostrarCartasYDineroJugadores();
        mostrarCartasCrupier();
    }

    public void preguntarApuestaJugadores(){
        for (Jugador jugador : jugadores){
            preguntarApuestaJugador(jugador);
        }
    }
    
    public void eliminarJugador(Jugador jugador){
        this.jugadores.remove(jugador);
    }

    private void preguntarApuestaJugador(Jugador jugador){
        boolean apuestaValida = false;
        while(!apuestaValida){
            try {
                System.out.println("Jugador " + jugador.getNombre() + " ¿cuanto quieres apostar? (en numeros enteros)");
                int respuesta = Integer.parseInt(new Scanner(System.in).nextLine());
                if (respuesta > jugador.getDinero()) {
                    System.out.println("No puedes apostar mas de lo que tienes");
                } else{
                    jugador.loseDinero(respuesta);
                    jugador.setDineroApostado(respuesta);
                    apuestaValida = true;
                }
            } catch (Exception as){
                System.out.println("Esa no es una apuesta correcta, prueba otra vez");
            }

        }
    }


    public void verCartas(){
        crupier.mostrarCartas();
        System.out.println(crupier.getPuntuacion());
        System.out.println();
        if (crupier.getPuntuacion() > 21){ // El crupier se pasa por tanto todos los jugadores que no hayan perdido antgeriormente ganan
            System.out.println("El crupier se paso :)");
            for (Jugador jugador : jugadores){
                System.out.println("Jugador: " + jugador);
                if (isBlackjackOnTable(jugador)){
                    mostrarHayBlackjack();
                    mostrarGanaste();
                    jugador.addDinero(jugador.getDineroApostado() * 3 / 2);
                }
                else if (jugador.isVivo()){
                    mostrarGanaste();
                    jugador.addDinero(jugador.getDineroApostado() * 2);

                } else {
                    mostrarPerdiste();

                }
                System.out.println();
            }
        } else { //En el otro caso vemos jugador por jugador si han superado al crupier
            for (Jugador jugador : jugadores){
                System.out.println("Jugador: " + jugador);
                if (isBlackjackOnTable(jugador)) {
                    calculateTableBlackjack(jugador);

                } else {
                    calculateTableNoBlackjack(jugador);

                }
                System.out.println();
            }
        }
    }

    public boolean isBlackjackOnTable(Jugador jugador){
        return jugador.gotBlackjack() || crupier.gotBlackjack();
    }

    public void calculateTableNoBlackjack(Jugador jugador){
        if (jugador.getPuntuacion() < crupier.getPuntuacion() || !jugador.isVivo()){ // tenemos peor puntuacion
            mostrarPerdiste();
        }
        else if (jugador.getPuntuacion() > crupier.getPuntuacion()){ // tenemos mejor puntuacion
            mostrarGanaste();
            jugador.addDinero(jugador.getDineroApostado() * 2);
        } else { //tenemos la misma puntuacion
            mostrarEmpataste();
            jugador.addDinero(jugador.getDineroApostado());
        }
    }

    private void calculateTableBlackjack(Jugador jugador) {
        mostrarHayBlackjack();
        if (crupier.gotBlackjack() && jugador.gotBlackjack() && jugador.isVivo()){ // ambos tienen blackjack
            mostrarEmpataste();
            jugador.addDinero(jugador.getDineroApostado());
        } else if(crupier.gotBlackjack()){
            mostrarPerdiste();

        } else if(jugador.gotBlackjack() && jugador.isVivo()) {
            mostrarGanaste();
            jugador.addDinero(jugador.getDineroApostado() * 3 / 2);
        }
    }

    private static void mostrarHayBlackjack() {
        System.out.println("Hay BlackJack en la mesa");
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

    private int preguntarCantidadJugadores(){
        int numJugadores = 5;
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

    public static void mostrarEmpataste(){
        System.out.println("EMPATASTE :|");
    }

    private static void mostrarGanaste() {
        System.out.println("¡GANASTE!");
    }

    public void apostarJugador(Jugador jugador, int apuesta){
        jugador.setDineroApostado(apuesta);
    }

    public Cartas getCartas() {
        return cartas;
    }

    public void mostrarCartasYDineroJugadores(){
        System.out.println();
        for (Jugador jugador : jugadores){
            jugador.mostrarCartas();
            jugador.mostrarDinero();
            System.out.println();
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
