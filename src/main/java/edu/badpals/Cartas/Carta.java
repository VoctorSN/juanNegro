package edu.badpals.Cartas;

import java.util.HashMap;

public class Carta {
    private final String nombre;
    private final char palo;
    private Integer puntuacion;
    private final HashMap<String, Integer> numeroPuntuacion = new HashMap<>();
    public Carta(String nombre){
        this.initializer();
        this.nombre = nombre;
        this.palo = nombre.charAt(nombre.length()-1);
        this.puntuacion = getPuntuacionByNombre(nombre);

    }

    public void initializer(){
        numeroPuntuacion.put("A",11);
        numeroPuntuacion.put("2",2);
        numeroPuntuacion.put("3",3);
        numeroPuntuacion.put("4",4);
        numeroPuntuacion.put("5",5);
        numeroPuntuacion.put("6",6);
        numeroPuntuacion.put("7",7);
        numeroPuntuacion.put("8",8);
        numeroPuntuacion.put("9",9);
        numeroPuntuacion.put("10",10);
        numeroPuntuacion.put("J",10);
        numeroPuntuacion.put("Q",10);
        numeroPuntuacion.put("K",10);
    }

    public Integer getPuntuacionByNombre(String nombre) {
        String numero = nombre.substring(0, nombre.length() - 1);
        return numeroPuntuacion.get(numero);
    }

    public boolean hasAs(){
        return nombre.contains("A");
    }

    public Integer getPuntuacion () {
        return puntuacion;
    }


    public String getNombre () {
        return nombre;
    }

    public char getPalo () {
        return palo;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}