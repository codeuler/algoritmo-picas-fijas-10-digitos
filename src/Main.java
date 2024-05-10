import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LogicaFijas logica = new LogicaFijas();
        Juego game = new Juego();
        int fijas = 0;
        int tiros = 0;
        //La lógica debe crear su número base
        logica.prearranque();
        Integer[] lanzamiento;
        //Se realizan un bucle hasta que se hallen las 10 fijas
        do {
            //Logica hace su tiro
            lanzamiento = logica.lanzar();
            System.out.println(Arrays.toString(lanzamiento));
            //Se cuentan la cantidad de fijas con base al lanzamiento
            fijas = game.hallarFijas(lanzamiento);
            System.out.println("\tFijas " + fijas + "\n");
            //Se le da un feedback a la logica para que internamente se actualice
            logica.recibirFeedback(fijas);
            tiros++;
        } while (fijas != 10);
        //Se imprimen la cantidad de tiros realizados
        System.out.println("Tiros: " + tiros);
    }
}