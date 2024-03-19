package edu.badpals.Cartas;

import java.util.ArrayList;
import java.util.Collections;

public class Cartas {
    public ArrayList<Carta> cartas;
    public final char[] palos = {'P','C','T','D'};
    public final String[] numeros = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    public Cartas(){
        this.initialize();
    }
    public void initialize(){
        cartas = new ArrayList<>();
        for (char palo : palos)
            for (String numero : numeros)
                cartas.add(new Carta(numero + palo));
    }

    public void barajar(){
        this.initialize();
        Collections.shuffle(cartas);
    }

    public Carta primeraCarta(){
        Carta primeraCarta = cartas.getFirst();
        cartas.remove(primeraCarta);
        return primeraCarta;
    }

    @Override
    public String toString() {
        String out = "";
        for (Carta i : cartas){
            out += "Carta: " + i.getNombre() + "\n";
        }
        return out;
    }
}

