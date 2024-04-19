import java.util.ArrayList;

public class LogicaFijas {
    private final Integer[] numeroFinal = new Integer[10];
    private final Integer[] primeraFila = new Integer[10];
    private final Integer[] aBC = new Integer[3];
    private final Integer[] indexABC = new Integer[3];
    private final boolean[][] posiciones = new boolean[10][10]; //[numero][posición]
    private boolean masUnoPrimeraFila = false;
    private int fijas = -1;
    private final Integer[]  numeroPrueba = new Integer[10];

    public void prearranque() {
        crearNumero();
    }

    /*
     * Devuelve true si hay números en el numero final, es decir el numero que se está armando
     * false si no encuentra ningún número
     */
    private boolean hayNumerosHallados() {
        for (Integer numero : numeroFinal) {
            if (numero != null) {
                return true;
            }
        }
        return false;
    }

    private void crearNumero() {
        int prueba;
        for (int i = 0; i < numeroPrueba.length; i++) {
            do {
                prueba = (int) (Math.random() * 10);
            } while (encontrarNumero(prueba, i));
            numeroPrueba[i] = prueba;
        }
    }
/*
 * Mira si en numero prueba, hasta el index j, no se ha ubicado el número "prueba"
 */
    private boolean encontrarNumero(int prueba, int j) {
        for (int i = 0; i < j; i++) {
            if (numeroPrueba[i] == prueba) {
                return true;
            }
        }
        return false;
    }
    /*
     * Cuando ya se sabe donde está ubicada una fija, si al cambiar A con B se obtiene +1 fijas, se usa este método para
     * hallar esa fija
     */
    private void usarFija(){
        for (int i = 0; i < numeroFinal.length; i++) {
            if(numeroFinal[i] != null){
                indexABC[2] = i; //En el index de C, se pone el de una fija
                aBC[2] = numeroFinal[i]; //En c, pone el valor de la fija
                break;
            }
        }
        numeroPrueba[indexABC[2]] = aBC[0]; //En la posición de C pongo A
        numeroPrueba[indexABC[1]] = aBC[2]; //En la posición de B (dónde ahora está A) pongo C
    }

    public Integer[] lanzar() {
        return numeroPrueba;
    }

    private void hallarPrimeraFila(int fijas) {
        if (this.fijas == fijas) {
            primeraFila[indexABC[1]] = 0; //En la posición de la que se hizo cambio, se anota como que no ocurrió nada
        } else if (this.fijas + 1 == fijas) {
            primeraFila[indexABC[1]] = 1; //En la posición de la que se hizo cambio, se anota como que ocurrió + 1
            //si ya se tienen los dos +1 y todas las demás fijas
            if (analizarPrimeraFilaMas() && analizarPrimeraFilaMenos1()) {
                procesarPrimeraFila();
                return;
            }
        } else if (this.fijas + 2 == fijas) {
            primeraFila[indexABC[1]] = 2;
            if (analizarPrimeraFilaMas() && analizarPrimeraFilaMenos1()) {
                procesarPrimeraFila();
                return;
            }
        } else if (this.fijas - 2 == fijas) {
            primeraFila[indexABC[1]] = -2;
            if (analizarPrimeraFilaMenos2()) {
                procesarPrimeraFila();
                return;
            }
        } else if (this.fijas - 1 == fijas) {
            primeraFila[indexABC[1]] = -1;
            if (analizarPrimeraFilaMenos1() && analizarPrimeraFilaMas()) {
                procesarPrimeraFila();
                return;
            }
        }
        cambiarAB();
    }

    /*
     * Retorna True si ya debe acabar
     */
    private boolean analizarPrimeraFilaMenos1() {
        if (this.fijas == 0){
            return true; //si es 0 nunca va a haber un -1
        }
        else if(primeraFila[0] != null && primeraFila[0].equals(-1)){
            return false; //Si la primera es fija, no vale la pena revisar los +1
        }
        else if (primeraFila[1] != null && primeraFila[2] != null) { //Si solo hay una fija y esa está en la primera posición
            if (this.fijas == 1 && primeraFila[1] < 0 && primeraFila[2] < 0) {
                primeraFila[0] = -1;
                primeraFila[1] = 0;
                primeraFila[2] = 0;
                return true;
            } else {
                int fijasContador = this.fijas;
                for (Integer integer : this.primeraFila) {
                    if (integer != null && integer == -1) {
                        fijasContador--;
                    }
                }
                return fijasContador == 0;
            }
        }
        return false;
    }

    /*
     * Si hay +1 no existe la posibilidad de que haya un -1 y viceversa
     */
    private boolean analizarPrimeraFilaMas() {
        if (primeraFila[0] != null && primeraFila[0] == -1) { //Si la primera posición es fija, no es necesario buscar las +1
            return true;
        }
        int fijasContador = 2;
        for (Integer integer : this.primeraFila) { //los + n se ignoran porque en un principio no se conocían
            if (integer != null && integer > 0) {
                fijasContador -= integer;
            }
        }
        return fijasContador == 0;
    }

    /*
     * Al llamar a este método se sabe que hubo un -2, por ende la primera posición es una fija, y de esa manera, todos
     * los -1, no nos van a servir
     * Return true, si ya se puede acabar con la primera fila
     */
    private boolean analizarPrimeraFilaMenos2() {
        int fijasContador = this.fijas;
        primeraFila[0] = -1;
        for (int i = 0; i < this.primeraFila.length; i++) {
            if (this.primeraFila[i] == null || this.primeraFila[i] == 0) {
                continue;
            } else if (this.primeraFila[i] == -1 && (i != 0)) {
                primeraFila[i] = 0;
            } else {
                fijasContador--; //áca solo puede haber -1 no +1 | el +1 indica la fija que ya conocemos
            }
        }

        return fijasContador == 0;
    }

    private void procesarPrimeraFila() {
        alistarNumero(true);
        boolean masUno = false;
        // index 0 (primer +1) 1 (posición) 2 (segundo +1) 3 (posición)
        ArrayList<Integer> numeroPosicionMasuno = new ArrayList<>(2);
        for (int i = 0; i < this.primeraFila.length; i++) {
            switch (primeraFila[i]) {
                case 1:
                    masUno = true;
                    numeroPosicionMasuno.add(numeroPrueba[i]);
                    numeroPosicionMasuno.add(i);
                    break;
                case 2:
                    numeroFinal[i] = numeroPrueba[0];
                    numeroFinal[0] = numeroPrueba[i];
                    numeroPrueba[i] = numeroFinal[i];
                    numeroPrueba[0] = numeroFinal[0];
                    this.fijas += 2;

                    break;
                case -1:
                    numeroFinal[i] = numeroPrueba[i];
                    break;
                case -2:
                    numeroFinal[i] = numeroPrueba[i];
                    numeroFinal[0] = numeroPrueba[0]; //
                    break;
                case null:
                case 0:
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + primeraFila[i]);
            }
        }
        if (masUno) {
            masUnoPrimeraFila = true;
            aBC[0] = numeroPrueba[0];
            aBC[1] = numeroPosicionMasuno.get(0);
            aBC[2] = numeroPosicionMasuno.get(2);
            indexABC[0] = 0;
            indexABC[1] = numeroPosicionMasuno.get(1);
            indexABC[2] = numeroPosicionMasuno.get(3);
            //b c a
            //al index de A le pongo b
            numeroPrueba[indexABC[0]] = aBC[1];
            //al index de B le pongo c
            numeroPrueba[indexABC[1]] = aBC[2];
            //al index de C le pongo a
            numeroPrueba[indexABC[2]] = aBC[0];

        } else {
            iniciarNuevoAB();
        }
    }

    private void iniciarNuevoAB() {
        CambiarNuevoAB();
        alistarNumero(false);
    }

    private void alistarNumero(boolean revertir) {
        if (!revertir) {
            numeroPrueba[indexABC[1]] = aBC[0]; //pongo a a en b
            numeroPrueba[indexABC[0]] = aBC[1]; //pongo a b en a
        } else {
            numeroPrueba[indexABC[0]] = aBC[0]; //pongo a A en su posición inicial
            numeroPrueba[indexABC[1]] = aBC[1]; //pongo a B en su posición inicial
        }

    }

    public void recibirFeedback(int fijas) {
        if(fijas == 10){
            return;
        }
        else if (indexABC[0] == null) { //Primer tiro
            tiroInicial(fijas);
        } else if (!hayNumerosHallados() && !masUnoPrimeraFila) { //Hallar la primera fila
            hallarPrimeraFila(fijas);
        } else if (masUnoPrimeraFila) { //Hallar los +1 de la primera fila
            hallarMasunos(fijas);
        } else { //Hallar el resto de fijas
            hallarTodasFijas(fijas);
        }
    }

    private void hallarMasunos(int fijas) {
        if (this.fijas < fijas) {
            masUnoPrimeraFila = false;
            if(numeroPrueba[0].equals(aBC[2])){
                numeroFinal[indexABC[0]] = aBC[2]; // en A ponga a c
                numeroFinal[indexABC[1]] = aBC[0]; // en B ponga a A
                this.fijas += 2;
                if (this.fijas + 1 == fijas) {
                    numeroFinal[indexABC[2]] = aBC[1]; // en c ponga a b
                   // numeroPrueba[indexABC[1]] = aBC[2];
                    this.fijas += 1;
                }
                else {
                    posiciones[aBC[1]][indexABC[2]] = true; // en c ponga a b - indica que en esa posición ya ha estado
                }
                iniciarNuevoAB();
            }
            else {
                numeroFinal[indexABC[0]] = aBC[1]; // en A ponga a b
                numeroFinal[indexABC[2]] = aBC[0]; // en C ponga a A
                this.fijas += 2;
                if (this.fijas + 1 == fijas) {
                    numeroFinal[indexABC[1]] = aBC[2]; // en b ponga a c
                 //   numeroPrueba[indexABC[1]] = aBC[2];
                    this.fijas += 1;
                }
                else {
                    posiciones[aBC[2]][indexABC[1]] = true; // en b ponga a c - indica que en esa posición ya ha estado
                }
                iniciarNuevoAB();
            }

        } else {
            // b c a -> c a b
            //al index de a le pongo c
            numeroPrueba[indexABC[0]] = aBC[2];
            //al index de b le pongo a
            numeroPrueba[indexABC[1]] = aBC[0];
            //al index de c le pongo b
            numeroPrueba[indexABC[2]] = aBC[1];
        }
    }
    private void hallarTodasFijas(int fijas) {
        if(this.fijas == fijas){
            posiciones[aBC[0]][indexABC[1]] = true; //A ya estuvo en B ----------------
            posiciones[aBC[1]][indexABC[0]] = true; //A ya estuvo en B ------------
            cambiarAB();
        }else if(this.fijas + 1 == fijas){ // alguno de los dos está bien
            if(posiciones[aBC[0]][indexABC[1]]){ //Si a ya estuvo en b
                numeroFinal[indexABC[0]] = aBC[1]; //En el index de a pongo b
                CambiarNuevoAB();
            }else if(posiciones[aBC[1]][indexABC[0]]){ //Si B ya estuvo en A
                numeroFinal[indexABC[1]] = aBC[0]; //En el index de b pongo A
                CambiarNuevoAB();
            }else {
                usarFija();
            }
            this.fijas++;
        } else if (this.fijas + 2 == fijas) { // al lugar al que cambiaron están bien
            numeroFinal[indexABC[1]] = aBC[0];
            numeroFinal[indexABC[0]] = aBC[1];
            CambiarNuevoAB();
            this.fijas += 2;
        } else if (this.fijas - 1 == fijas) {
            numeroPrueba[indexABC[2]] = aBC[2]; //C lo pone en su verdadera posición
            numeroFinal[indexABC[0]] = aBC[1]; //En el número final ubica B en el index de A
            numeroPrueba[indexABC[1]] = aBC[0];
            posiciones[aBC[0]][indexABC[1]] = true; //En el index de B pone A como que ya se lanzó -----------
            CambiarNuevoAB();

        } else if (this.fijas - 2 == fijas) {
            numeroPrueba[indexABC[2]] = aBC[2]; //C lo pone en su verdadera posición
            numeroFinal[indexABC[1]] = aBC[0]; //En el número final ubica A en el index de b
            numeroPrueba[indexABC[1]] = aBC[0];
            posiciones[aBC[1]][indexABC[0]] = true; //En el index de A pone B como que ya se lanzó -----------
            CambiarNuevoAB();
        }
    }

    private void CambiarNuevoAB() {
        Integer[] nuevoIndex = buscarNuevoIndexIJ(indexABC[0]);
        indexABC[0] = nuevoIndex[0];
        indexABC[1] = nuevoIndex[1];
        aBC[0] = numeroPrueba[indexABC[0]];
        aBC[1] = numeroPrueba[indexABC[1]];
        numeroPrueba[indexABC[1]] = aBC[0]; //En el index de b pongo a
        numeroPrueba[indexABC[0]] = aBC[1]; //En el index de a pongo b

    }
    /*
     * Método usado únicamente cuando se halle primera fila, usado para darle un nuevo valor a A y B
     */
    private void cambiarAB() {
        numeroPrueba[indexABC[1]] = aBC[1]; //reubico a b en donde estaba antes
        for (int i = indexABC[1] + 1; i < numeroFinal.length; i++) { //
            if (numeroFinal[i] == null) {
                indexABC[1] = i;
                aBC[1] = numeroPrueba[i];
                break;
            }
        }
        alistarNumero(false);
    }

    private void tiroInicial(int fijas) {
        this.fijas = fijas;
        indexABC[0] = 0;
        indexABC[1] = 1;
        aBC[0] = numeroPrueba[0];
        aBC[1] = numeroPrueba[1];
        alistarNumero(false);
    }
/*
 * Este método es usado para hallarle un nuevo valor a A y B
 */
    public Integer[] buscarNuevoIndexIJ(int i) {
        Integer[] nuevoIndex = new Integer[2];
        for (int k = i; k < numeroFinal.length; k++) {
            if (numeroFinal[k] == null) {
                nuevoIndex[0] = k;
                for (int l = k + 1; l < numeroFinal.length; l++) {
                    if (numeroFinal[l] == null) {
                        nuevoIndex[1] = l;
                        return nuevoIndex;
                    }
                }
            }
        }
        return null;
    }
}
