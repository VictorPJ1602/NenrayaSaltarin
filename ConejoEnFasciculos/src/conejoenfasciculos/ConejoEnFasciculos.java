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

   private static String[][] nombres;
    private static char[][] simbolos;
    private static int[] turnoInicial;
    private static int[][] contador; 
    private static int[] dimensiones;
    private static char[][][] tableros;
    private static Juego[] partidas;
    private static boolean[] resultadosPartidas;
    private static int Saltator=-1;
   

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        IntroduccionNombres introduccionNombres = new IntroduccionNombres();
        
        // Solicitar el número de partidas
        System.out.print("¿Cuántas partidas deseas jugar? ");
        int numPartidas = leer.nextInt();
        leer.nextLine();
        
        // Inicializar arreglos para almacenar datos de las partidas
        nombres = new String[numPartidas][2];
        simbolos = new char[numPartidas][2];
        turnoInicial = new int[numPartidas];
        dimensiones = new int[numPartidas];
        tableros = new char[numPartidas][][];
        partidas = new Juego[numPartidas];
        resultadosPartidas = new boolean[numPartidas];
        contador = new int[numPartidas][]; 
       
        
        // Obtener nombres y configuración de las partidas
        String[] nombresPartida = introduccionNombres.obtenerNombres(leer);
        for (int i = 0; i < numPartidas; i++) {
            System.out.println("Introducid datos partida "+ (i + 1));
            nombres[i] = nombresPartida; // Establecer los mismos nombres para todas las partidas
            
            Simbolos introsimbolos = new Simbolos(); //El resto de los elementos se establece de manera manualhgbv
            simbolos[i] = introsimbolos.obtenersimbolos(leer, nombres[i]);

            Turno turno = new Turno();
            turnoInicial[i] = turno.orden(nombres[i], leer);
            contador[i] = new int[1]; 
            contador[i][0] = 0; 
            Dimension dimensionesObj = new Dimension();
            dimensiones[i] = dimensionesObj.obtenerDimension(leer);

            Tablero tableroJuego = new Tablero();
            tableroJuego.inicializarTablero(dimensiones[i]);
            tableros[i] = tableroJuego.getTablero();
        }
        
        saltitos();
        
        
        
    }

    // Saltitos es el proceso principal donde se ejecutan las partidas
    public static void saltitos() {
        while (!EstadosPartidas()) {
            if (Saltator == resultadosPartidas.length - 1) {
                Saltator = 0;
            } else {
                Saltator++;
            }
            if (!resultadosPartidas[Saltator]) {
                turnoInicial[Saltator]++;
                resultadosPartidas[Saltator] = Juego.iniciarJuego(tableros[Saltator], dimensiones[Saltator], simbolos[Saltator][0], simbolos[Saltator][1], 
                        nombres[Saltator][0], nombres[Saltator][1], turnoInicial[Saltator], contador[Saltator]); 
            }
        }
        System.out.println("Todas las partidas han terminado.");
    }

    // Verifica si todas las partidas han terminado
    private static boolean EstadosPartidas() {
        for (int i = 0; i < resultadosPartidas.length; i++) {
            if (!resultadosPartidas[i]) {
                return false;
            }
        }
        return true;
    }
    
   
}


   
       


        
        
        



    

    

  


class IntroduccionNombres {
    // Método para obtener los nombres de los jugadores
    public String[] obtenerNombres(Scanner leer) {
        String[] nombres = new String[2];
        System.out.print("Introduzca nombre del jugador 1: ");
        nombres[0] = leer.next();
        System.out.print("Introduzca nombre del jugador 2: ");
        nombres[1] = leer.next();
        
        // Verificar que los nombres sean diferentes
        while (nombres[1].equals(nombres[0])) {
            System.out.print("Nombre no válido, introduzca otro: ");
            nombres[1] = leer.next();
        }
        return nombres;
    }
}








class Simbolos {
    // Método para obtener los símbolos de los jugadores
    public char[] obtenersimbolos(Scanner leer, String[] nombres) {
        char[] Simbolos = new char[2];
        System.out.print(nombres[0] + " elige símbolo: ");
        Simbolos[0] = leer.next().charAt(0);

        System.out.print(nombres[1] + " elige símbolo: ");
        Simbolos[1] = leer.next().charAt(0);

        // Verificar que los símbolos sean diferentes
        while (Simbolos[0] == Simbolos[1]) {
            System.out.print("Símbolo ya seleccionado. Por favor, elige otro: ");
            Simbolos[1] = leer.next().charAt(0);
        }
        return Simbolos;
    }
}








class Turno {
    // Método para determinar quién tiene el primer turno
    public int orden(String[] nombres, Scanner leer) {
        System.out.print("¿Quién tendrá el turno 1? Introduzca 1 para " + nombres[0] + " o 2 para " + nombres[1] + ": ");
        int eleccion = leer.nextInt();
        while (eleccion != 1 && eleccion != 2) {
            System.out.print("Caracter no válido, introduzca uno válido: ");
            eleccion = leer.nextInt();
        }
        // Se retorna el número de jugador que inicia
        return eleccion==1 ? 1 : 2;
    }
}






class Dimension {
    // Método para obtener las dimensiones del tablero
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




