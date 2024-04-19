import java.util.Arrays;

public class Juego {
    private final Integer[] numero = new Integer[10];
    //private final Integer[] numero = {9,8,7,6,5,4,3,2,1,0};

    public Juego() {
        crearNumero();
        System.out.println(Arrays.toString(numero));
    }

    private void crearNumero() {
        int prueba;
        for (int i = 0; i < numero.length; i++) {
            do {
                prueba = (int) (Math.random() * 10);
            } while (encontrarNumero(prueba, i));
            numero[i] = prueba;
        }
    }

    private boolean encontrarNumero(int prueba, int j) {
        for (int i = 0; i < j; i++) {
            if (numero[i] == prueba) {
                return true;
            }
        }
        return false;
    }

    public int hallarFijas(Integer[] numeroAProbar){
        int fijas = 0;
        for (int i = 0; i < numero.length; i++) {
            fijas += (numero[i].equals(numeroAProbar[i])) ? 1 : 0;
        }
        return fijas;
    }
}
