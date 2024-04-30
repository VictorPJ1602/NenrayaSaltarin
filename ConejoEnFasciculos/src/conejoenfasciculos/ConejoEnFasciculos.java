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
    private static int[][] resultados;

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
        resultados = new int[numPartidas][3]; // Para almacenar el resultado de cada partida (1: jugador 1, 2: jugador 2, 3: empate)
        
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
        
        
        contarResultados();
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
                        nombres[Saltator][0], nombres[Saltator][1], turnoInicial[Saltator], contador[Saltator], resultados[Saltator]); 
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
    
    // Contabiliza los resultados de las partidas y los imprime
    private static void contarResultados() {
        int ganadasJugador1 = 0;
        int ganadasJugador2 = 0;
        int empates = 0;
        
        // Contabilizar resultados
        for (int[] resultado : resultados) {
            switch (resultado[0]) {
                case 1:
                    ganadasJugador1++;
                    break;
                case 2:
                    ganadasJugador2++;
                    break;
                default:
                    empates++;
                    break;
            }
        }
        
        // Imprimir resultados
        System.out.println("Resultados de las partidas:");
        System.out.println("Jugador 1(" + nombres[0][0] + "): " + ganadasJugador1 + " partidas ganadas.");
        System.out.println("Jugador 2(" + nombres[0][1] + "): " + ganadasJugador2 + " partidas ganadas.");
        System.out.println("Empates: " + empates);
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







class Juego {
    // Método para iniciar el juego
    public static boolean iniciarJuego(char[][] tablero, int dimension, char simbolo1, char simbolo2, String nombre1, 
            String nombre2, int turnoInicial, int[] contador, int[] resultados) {
        Scanner leer = new Scanner(System.in);
        System.out.println();
        String nombre = turnoInicial % 2 == 0 ? nombre1 : nombre2; // Determina el nombre del jugador actual
        char simbolo = turnoInicial % 2 == 0 ? simbolo1 : simbolo2; // Determina el símbolo del jugador actual
         // Incrementa el turno para el siguiente jugador
        
        ImpresorTablero impresor = new ImpresorTablero();
        impresor.imprimir(tablero, dimension); // Imprime el tablero
        int columna = coordenadasColumna(nombre, simbolo); // Obtiene la columna ingresada por el jugador
        int fila = coordenadasFila(nombre, simbolo); // Obtiene la fila ingresada por el jugador

        // Valida si la coordenada es válida
        while(!coordenadaValida(tablero, dimension, fila, columna)) {
            System.out.println("Coordenada inválida. Por favor, intenta de nuevo.");
            columna = coordenadasColumna(nombre, simbolo); // Vuelve a solicitar la columna
            fila = coordenadasFila(nombre, simbolo); // Vuelve a solicitar la fila
        }
        
        // Coloca el símbolo del jugador en el tablero y actualiza el contador de movimientos
        tablero[fila - 1][columna - 1] = simbolo;
        contador[0]++; 
        impresor.imprimir(tablero, dimension); // Imprime el tablero actualizado
        
        // Verifica si hay un ganador o si se ha alcanzado un empate
        if (HayGanador.comprobarGanador(tablero, dimension, simbolo) == simbolo) {
            resultados[0] = (nombre.equals(nombre2)) ? 2 : 1; // Asigna el resultado de la partida al arreglo de resultados
            System.out.println("¡Felicidades, " + nombre + "! Has ganado.");
            return true;
        } else if (contador[0] == dimension * dimension) { // Si se alcanza el máximo de movimientos posibles
            System.out.println("El juego ha terminado en empate.");
            resultados[0] = 3; // Asigna el resultado de empate al arreglo de resultados
            return true;
        }
        
        return false;
    }

    // Método para validar si la coordenada ingresada es válida
    private static boolean coordenadaValida(char[][] tablero, int dimension, int fila, int columna) {
        return fila >= 1 && fila <= dimension && columna >= 1 && columna <= dimension && tablero[fila - 1][columna - 1] == ' ';
    }

    // Método para solicitar la columna al jugador
    private static int coordenadasColumna(String nombre, char simbolo) {
        Scanner leer = new Scanner(System.in);
        System.out.print(nombre + " (" + simbolo + ") Introduce columna: ");
        return leer.nextInt();
    }

    // Método para solicitar la fila al jugador
    private static int coordenadasFila(String nombre, char simbolo) {
        Scanner leer = new Scanner(System.in);
        System.out.print("Introduce fila: ");
        return leer.nextInt();
    }
}







// Esta clase contiene el método para comprobar si hay un ganador en el juego de tres en raya.
class HayGanador {
    // Este método verifica si hay un ganador en el tablero para el símbolo dado.
    public static char comprobarGanador(char[][] tablero, int dimension, char simbolo) {
        int bombero = 0;
        
        // Comprueba las filas del tablero.
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

        // Comprueba las columnas del tablero.
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
        
        // Comprueba la diagonal principal del tablero.
        for (int i = 0; i < dimension && bombero!=dimension; i++){
            if (tablero[i][i] == simbolo) {
                bombero++;
            } 
        }
        
        // Si no hay un ganador en la diagonal principal, resetea el contador.
        if (bombero!=dimension){
            bombero=0;
        }
        
        // Comprueba la diagonal secundaria del tablero.
        for (int i = 0; i < dimension && bombero!=dimension; i++){
            if (tablero[i][dimension - 1 - i] == simbolo) {
                bombero++;
            } 
        }
        
        // Si el contador llega al tamaño de la dimensión del tablero, significa que hay un ganador.
        if (bombero == dimension){
            return simbolo;
        }
        
        // Si no hay un ganador, devuelve un carácter espacio.
        return ' ';
    }
}