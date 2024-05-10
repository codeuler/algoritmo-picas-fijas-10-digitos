import java.util.Arrays;

/**
 * La clase Juego representa un validador para los tiros que realiza LogicaFijas.
 * El juego consiste en adivinar un número de 10 dígitos generados aleatoriamente.
 */
public class Juego {
    // El número generado aleatoriamente para adivinar
    private final Integer[] numero = new Integer[10];

    /**
     * Constructor de la clase Juego.
     * Genera un nuevo número aleatorio de 10 dígitos y lo muestra por consola.
     */
    public Juego() {
        crearNumero();
        System.out.println(Arrays.toString(numero));
    }

    /**
     * Genera un nuevo número aleatorio de 10 dígitos sin repeticiones.
     */
    private void crearNumero() {
        int prueba;
        for (int i = 0; i < numero.length; i++) {
            do {
                prueba = (int) (Math.random() * 10);
            } while (encontrarNumero(prueba, i));
            numero[i] = prueba;
        }
    }

    /**
     * Verifica si un número está presente en el array hasta la posición indicada.
     * @param prueba El número a verificar.
     * @param j La posición límite del array.
     * @return true si el número está presente, false de lo contrario.
     */
    private boolean encontrarNumero(int prueba, int j) {
        for (int i = 0; i < j; i++) {
            if (numero[i] == prueba) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compara un número propuesto con el número generado y devuelve la cantidad de dígitos fijos.
     * @param numeroAProbar El número propuesto a comparar.
     * @return La cantidad de dígitos fijos.
     */
    public int hallarFijas(Integer[] numeroAProbar){
        int fijas = 0;
        for (int i = 0; i < numero.length; i++) {
            fijas += (numero[i].equals(numeroAProbar[i])) ? 1 : 0;
        }
        return fijas;
    }
}
