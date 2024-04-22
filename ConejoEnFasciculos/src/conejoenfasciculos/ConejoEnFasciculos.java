/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conejoenfasciculos;

import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class ConejoEnFasciculos {

  public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        IntroduccionNombres introduccionNombres = new IntroduccionNombres();
        String[] nombres = introduccionNombres.obtenerNombres(leer);
        
        
        Simbolos introsimbolos= new Simbolos();
        
        char[] simbolos = introsimbolos.obtenersimbolos(leer, nombres);
        
        Turno turno= new Turno();
        
        int turnoInicial = turno.orden(nombres, leer);

        Dimension dimensiones = new Dimension();
        
        int dimension=dimensiones.obtenerDimension(leer), contador = 0;

        char[][] tablero = new char[dimension][dimension];
        
        Tablero[] tablero2 = new Tablero[2];
                Juego[] juego = new Juego[2];
       

for (int i = 0; i < juego.length; i++) {
    juego[i] = new Juego();
}
        for (int i = 0; i < tablero2.length; i++) {
    tablero2[i] = new Tablero();
    tablero2[i].inicializarTablero(dimension);
}
        
        
        


for (int i = 0; i < juego.length; i++) {
    if (turnoInicial == 1) {
        juego[i].iniciarJuego(tablero2[i].getTablero(), dimension, simbolos[0], simbolos[1], nombres[0], nombres[1]);
    } else {
        juego[i].iniciarJuego(tablero2[i].getTablero(), dimension, simbolos[1], simbolos[0], nombres[1], nombres[0]);
    }
}
        }
        
        
        
    }

    

  


class IntroduccionNombres {
    public String[] obtenerNombres(Scanner leer) {
        String[] nombres = new String[2];
        System.out.print("Introduzca nombre de jugador: ");
        nombres[0] = leer.next();
        System.out.print("Introduzca nombre del otro jugador: ");
        nombres[1] = leer.next();
        while (nombres[1].equals(nombres[0])) {
            System.out.print("Nombre no válido, introduzca otro: ");
            nombres[1] = leer.next();
        }
        return nombres;
    }
}

class Simbolos {
    public char [] obtenersimbolos(Scanner leer, String[] nombres){
        
        char[] Simbolos = new char[2];
        System.out.print(nombres[0] + " elige símbolo: ");
        Simbolos[0] = leer.next().charAt(0);

        System.out.print(nombres[1] + " elige símbolo: ");
        Simbolos[1] = leer.next().charAt(0);

        while (Simbolos[0] == Simbolos[1]) {
            System.out.print("Símbolo ya seleccionado. Por favor, elige otro: ");
            Simbolos[1] = leer.next().charAt(0);
        }
        return Simbolos;
        
    }
}

class Turno {
    public int orden (String[] nombres, Scanner leer){
        System.out.print("¿Quién tendrá el turno 1? Introduzca 1 para " + nombres[0] + " o 2 para " + nombres[1] + ": ");
        int eleccion = leer.nextInt();
        while (eleccion != 1 && eleccion != 2) {
            System.out.print("Caracter no válido, introduzca uno válido: ");
            eleccion = leer.nextInt();
        }
return eleccion;
    }
}
    

class Dimension {
    public int obtenerDimension(Scanner leer) {
        System.out.print("¿Qué tamaño quieres que tenga el tablero (mínimo 2)? ");
        int dimension = leer.nextInt();
        while (dimension < 2) {
            System.out.print("Tamaño demasiado pequeño, introduce un número igual o superior a dos: ");
            dimension = leer.nextInt();
        }
        return dimension;
    }
}
    
class Tablero {
    private char[][] tablero;
    
    public void inicializarTablero(int dimension) {
        tablero = new char[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tablero[i][j] = ' ';
            }
        }
    }
    public char[][] getTablero() {
        return tablero;
    }
}

class ImpresorTablero {
    public void imprimir(char[][] tablero, int dimension) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension * 2 + 1; j++) {
                if (j % 2 == 0) {
                    System.out.print("|");
                } else {
                    System.out.print(tablero[i][j / 2]);
                }
            }
            System.out.println();
        }
    }
}

class Juego {
    public void iniciarJuego(char[][] tablero, int dimension, char simbolo1, char simbolo2, String nombre1, String nombre2) {
        Scanner leer = new Scanner(System.in);
        
        boolean turno = true;
        int contador = 0;
 
        String nombre = nombre1;
        char simbolo = simbolo1;

        while (contador != dimension * dimension && HayGanador.comprobarGanador(tablero, dimension, simbolo)!= simbolo) {
            ImpresorTablero impresor = new ImpresorTablero();
        impresor.imprimir(tablero, dimension);
            System.out.print(nombre + ", Introduce columna: ");
            int columna = leer.nextInt();
            System.out.print("Introduce fila: ");
            int fila = leer.nextInt();

            if (coordenadaValida(tablero, dimension, fila, columna)) {
                tablero[fila - 1][columna - 1] = simbolo;
                contador++;
                turno = !turno;
                
                
                
                
                nombre = turno ? nombre1 : nombre2;
                simbolo = turno ? simbolo1 : simbolo2;
            } else {
                System.out.println("Coordenada inválida. Por favor, intenta de nuevo.");
            }
            if(HayGanador.comprobarGanador(tablero, dimension, simbolo)!= simbolo){
            
                    }
        }
        if (HayGanador.comprobarGanador(tablero, dimension, simbolo)== simbolo) {
                    String ganadorNombre = turno ? nombre2 : nombre1;
                    System.out.println("¡Felicidades, " + ganadorNombre + "! Has ganado.");
                     
                }

        else {
            System.out.println("El juego ha terminado en empate.");
        }
        
    }

    private boolean coordenadaValida(char[][] tablero, int dimension, int fila, int columna) {
        return fila >= 1 && fila <= dimension && columna >= 1 && columna <= dimension && tablero[fila - 1][columna - 1] == ' ';
    }
}

class HayGanador {
    public static char comprobarGanador(char[][] tablero, int dimension, char simbolo) {
        int bombero = 0;
        for (int i = 0; i < dimension && bombero!=dimension; i++) {
            
            for (int j = 0; j < dimension; j++) {
                if (tablero[i][j] == simbolo) {
                    bombero++;
                } 
                
                }
                if (bombero!=dimension){
                    bombero=0;
            }
        }

        
        for (int i = 0; i < dimension && bombero!=dimension; i++) {
            
            for (int j = 0; j < dimension; j++) {
                if (tablero[j][i] == simbolo) {
                    bombero++;
                } 
                
                }
                if (bombero!=dimension){
                    bombero=0;
            }
        }
        
        for (int i = 0; i < dimension && bombero!=dimension; i++){
            if (tablero[i][i] == simbolo) {
                    bombero++;
                } 
        }
        
        if (bombero!=dimension){
                    bombero=0;
            }
        
        for (int i = 0; i < dimension && bombero!=dimension; i++){
            if (tablero[i][dimension - 1 - i] == simbolo) {
                    bombero++;
                } 
        }
        if (bombero==dimension){
            return simbolo;
        }
        
        return ' ';
    }
}