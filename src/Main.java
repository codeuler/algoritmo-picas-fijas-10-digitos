import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LogicaFijas logica = new LogicaFijas();
        Scanner sc = new Scanner(System.in);
        Juego game = new Juego();
        int fijas = 0;
        int tiros = 0;
        logica.prearranque();
        Integer[] lanzamiento;
        do {
            lanzamiento = logica.lanzar();
            System.out.println(Arrays.toString(lanzamiento));

         //   System.out.println("Ingrese la cantidad de fijas: ");
           // try {
             //              fijas = Integer.parseInt(sc.nextLine().replace(" ",""));
            fijas = game.hallarFijas(lanzamiento);
            System.out.println("\tFijas " + fijas + "\n");

//            }catch (Exception e){
//             System.out.println("Error de tipeo! ");
//             System.out.println(fijas);
//               continue;
//             }
            logica.recibirFeedback(fijas);
            tiros++;
            if(tiros == 50){
                System.out.println("****************************ERROR*******************************");
                break;
            }
        } while (fijas != 10);
        System.out.println("Tiros: " + tiros);
    }
}