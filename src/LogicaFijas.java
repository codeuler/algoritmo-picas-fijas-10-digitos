import java.util.ArrayList;

/**
 * La clase LogicaFijas implementa la lógica del juego para encontrar una secuencia numérica secreta.
 * Utiliza un algoritmo de búsqueda y análisis para descubrir las posiciones reales de los digitos de un
 * numero de tamaño 10
 */
public class LogicaFijas {
    // Array que almacena el número final secreto a descubrir
    private final Integer[] numeroFinal = new Integer[10];
    // Array que almacena la primera secuencia que se creó para el número secreto
    private final Integer[] primeraFila = new Integer[10];
    // Array que almacena los valores de A, B y C durante las operaciones
    private final Integer[] aBC = new Integer[3];
    // Array que almacena los índices de A, B y C durante las operaciones
    private final Integer[] indexABC = new Integer[3];
    // Matriz que registra las posiciones probadas durante el juego
    private final boolean[][] posiciones = new boolean[10][10]; // [número][posición]
    // Indica si se ha agregado uno en la primera pasada o secuencia
    private boolean masUnoPrimeraFila = false;
    // Contador de fijas obtenidas
    private int fijas = -1;
    // Array que almacena el número propuesto para adivinar
    private final Integer[] numeroPrueba = new Integer[10];

    /**
     * Método para inicializar el juego y crear un nuevo número aleatorio de prueba.
     */
    public void prearranque() {
        crearNumero();
    }

    /**
     * Método que verifica si hay números hallados en el número final.
     *
     * @return true si hay números hallados, false de lo contrario.
     */
    private boolean hayNumerosHallados() {
        for (Integer numero : numeroFinal) {
            if (numero != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para crear un nuevo número aleatorio de prueba.
     */
    private void crearNumero() {
        int prueba;
        for (int i = 0; i < numeroPrueba.length; i++) {
            do {
                prueba = (int) (Math.random() * 10);
            } while (encontrarNumero(prueba, i));
            numeroPrueba[i] = prueba;
        }
    }

    /**
     * Método que verifica si un número ya ha sido probado hasta el índice indicado.
     *
     * @param prueba El número a verificar.
     * @param j      El índice límite.
     * @return true si el número ya ha sido probado, false de lo contrario.
     */
    private boolean encontrarNumero(int prueba, int j) {
        for (int i = 0; i < j; i++) {
            if (numeroPrueba[i] == prueba) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cuando ya se sabe donde está ubicada una fija, si al cambiar A con B se obtiene +1 fijas, se usa este método para
     * hallar esa fija
     */
    private void usarFija() {
        for (int i = 0; i < numeroFinal.length; i++) {
            if (numeroFinal[i] != null) {
                indexABC[2] = i; //En el index de C, se pone el de una fija
                aBC[2] = numeroFinal[i]; //En c, pone el valor de la fija
                break;
            }
        }
        numeroPrueba[indexABC[2]] = aBC[0]; //En la posición de C pongo A
        numeroPrueba[indexABC[1]] = aBC[2]; //En la posición de B (dónde ahora está A) pongo C
    }

    /**
     * Método para lanzar el número de prueba actual.
     *
     * @return El número de prueba actual.
     */
    public Integer[] lanzar() {
        return numeroPrueba;
    }

    /**
     * Método para analizar y determinar la primera secuencia de digitos para el número a adivinar.
     *
     * @param fijas El número de fijas obtenidas.
     */
    private void hallarPrimeraFila(int fijas) {
        //Despues de hacer un tiro, al recibir la retroalimentacion
        //Cuando las fijas no hayan variado
        if (this.fijas == fijas) {
            primeraFila[indexABC[1]] = 0; //En la posición de la que se hizo cambio, se anota como que no ocurrió nada

        }
        //Al aumentar en 1 la cantidad de fijas
        else if (this.fijas + 1 == fijas) {
            primeraFila[indexABC[1]] = 1; //En la posición de la que se hizo cambio, se anota como que ocurrió + 1
            //si ya se tienen los dos +1 y todas las demás fijas
            if (analizarPrimeraFilaMas() && analizarPrimeraFilaMenos1()) {
                procesarPrimeraFila();
                return;
            }
        }
        //Al aumentar en 2 la cantidad de fijas
        else if (this.fijas + 2 == fijas) {
            primeraFila[indexABC[1]] = 2;
            if (analizarPrimeraFilaMas() && analizarPrimeraFilaMenos1()) {
                procesarPrimeraFila();
                return;
            }

        }
        //Al disminuir en 2 la cantidad de fijas
        else if (this.fijas - 2 == fijas) {
            primeraFila[indexABC[1]] = -2;
            if (analizarPrimeraFilaMenos2()) {
                procesarPrimeraFila();
                return;
            }
        }
        //Al disminuir en 1 la cantidad de fijas
        else if (this.fijas - 1 == fijas) {
            primeraFila[indexABC[1]] = -1;
            if (analizarPrimeraFilaMenos1() && analizarPrimeraFilaMas()) {
                procesarPrimeraFila();
                return;
            }
        }
        cambiarAB();
    }

    /**
     * Analiza si ya se puede finalizar la primera fila, considerando las fijas y los valores de los resultados.
     * @return true si ya se puede finalizar la primera fila, false de lo contrario.
     */
    private boolean analizarPrimeraFilaMenos1() {
        if (this.fijas == 0) {
            return true; //si es 0 nunca va a haber un -1
        } else if (primeraFila[0] != null && primeraFila[0].equals(-1)) {
            return false; //Si la primera es fija, no vale la pena revisar los +1
        } else if (primeraFila[1] != null && primeraFila[2] != null) { //Si solo hay una fija y esa está en la primera posición
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

    /**
     * Analiza si se pueden descartar los valores +1 en la primera fila, ya que hay un valor -1.
     * @return true si se pueden descartar los valores +1, false de lo contrario.
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

    /**
     * Al llamar a este método se sabe que hubo un -2, por ende la primera posición es una fija, y de esa manera, todos
     * los -1, no nos van a servir
     * @return true si se puede finalizar la primera fila con -2, false de lo contrario.
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
    /**
     * Procesa la primera fila de resultados obtenidos durante el juego.
     */
    private void procesarPrimeraFila() {
        alistarNumero(true);
        boolean masUno = false;
        // Listas para almacenar los valores y posiciones de los números con resultado +1
        ArrayList<Integer> numeroPosicionMasuno = new ArrayList<>(2);
        for (int i = 0; i < this.primeraFila.length; i++) {
            switch (primeraFila[i]) {
                case 1:
                    // Si hay un +1, se registra la posición y el valor en las listas
                    masUno = true;
                    numeroPosicionMasuno.add(numeroPrueba[i]);
                    numeroPosicionMasuno.add(i);
                    break;
                case 2:
                    // Si hay un +2, se intercambian los valores entre las posiciones correspondientes
                    numeroFinal[i] = numeroPrueba[0];
                    numeroFinal[0] = numeroPrueba[i];
                    numeroPrueba[i] = numeroFinal[i];
                    numeroPrueba[0] = numeroFinal[0];
                    this.fijas += 2;
                    break;
                case -1:
                    // Si hay un -1, se coloca el valor correspondiente en la posición final
                    numeroFinal[i] = numeroPrueba[i];
                    break;
                case -2:
                    // Si hay un -2, se coloca el valor correspondiente en la posición final y en la posición 0
                    numeroFinal[i] = numeroPrueba[i];
                    numeroFinal[0] = numeroPrueba[0];
                    break;
                case null:
                case 0:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + primeraFila[i]);
            }
        }
        // Si hay algún +1, se ajusta la secuencia de prueba
        if (masUno) {
            masUnoPrimeraFila = true;
            // Se asignan los valores de A, B y C según los registros obtenidos
            aBC[0] = numeroPrueba[0];
            aBC[1] = numeroPosicionMasuno.get(0);
            aBC[2] = numeroPosicionMasuno.get(2);
            indexABC[0] = 0;
            indexABC[1] = numeroPosicionMasuno.get(1);
            indexABC[2] = numeroPosicionMasuno.get(3);
            // Se intercambian los valores en la secuencia de prueba según los registros de A, B y C
            numeroPrueba[indexABC[0]] = aBC[1];
            numeroPrueba[indexABC[1]] = aBC[2];
            numeroPrueba[indexABC[2]] = aBC[0];
        } else {
            // Si no hay +1, se inicia un nuevo ajuste de A y B
            iniciarNuevoAB();
        }
    }
    /**
     * Inicia un nuevo ajuste de A y B.
     * Este método llama a CambiarNuevoAB() y alistarNumero() para realizar el ajuste inicial.
     */
    private void iniciarNuevoAB() {
        cambiarNuevoAB();
        alistarNumero(false);
    }
    /**
     * Ajusta los valores de la secuencia de prueba según la configuración.
     * Si revertir es verdadero, restaura los valores de A y B a sus posiciones iniciales.
     * Si revertir es falso, coloca el valor de A en la posición B y el valor de B en la posición A.
     */
    private void alistarNumero(boolean revertir) {
        if (!revertir) {
            numeroPrueba[indexABC[1]] = aBC[0]; //pongo a en b
            numeroPrueba[indexABC[0]] = aBC[1]; //pongo b en a
        } else {
            numeroPrueba[indexABC[0]] = aBC[0]; //pongo A en su posición inicial
            numeroPrueba[indexABC[1]] = aBC[1]; //pongo B en su posición inicial
        }

    }
    /**
     * Recibe el número de fijas como retroalimentación del juego.
     * Llama a diferentes métodos según la fase del juego y la información obtenida.
     */
    public void recibirFeedback(int fijas) {
        if (fijas == 10) {
            return;
        } else if (indexABC[0] == null) { //Primer tiro
            tiroInicial(fijas);
        } else if (!hayNumerosHallados() && !masUnoPrimeraFila) { //Hallar la primera fila
            hallarPrimeraFila(fijas);
        } else if (masUnoPrimeraFila) { //Hallar los +1 de la primera fila
            hallarMasunos(fijas);
        } else { //Hallar el resto de fijas
            hallarTodasFijas(fijas);
        }
    }
    /**
     * Halla los números con resultado +1 en la primera fila y ajusta la secuencia de prueba en consecuencia.
     */
    private void hallarMasunos(int fijas) {
        if (this.fijas < fijas) {
            masUnoPrimeraFila = false;
            if (numeroPrueba[0].equals(aBC[2])) {
                numeroFinal[indexABC[0]] = aBC[2]; // en A ponga a c
                numeroFinal[indexABC[1]] = aBC[0]; // en B ponga a A
                this.fijas += 2;
                if (this.fijas + 1 == fijas) {
                    numeroFinal[indexABC[2]] = aBC[1]; // en c ponga a b
                    // numeroPrueba[indexABC[1]] = aBC[2];
                    this.fijas += 1;
                } else {
                    posiciones[aBC[1]][indexABC[2]] = true; // en c ponga a b - indica que en esa posición ya ha estado
                }
                iniciarNuevoAB();
            } else {
                numeroFinal[indexABC[0]] = aBC[1]; // en A ponga a b
                numeroFinal[indexABC[2]] = aBC[0]; // en C ponga a A
                this.fijas += 2;
                if (this.fijas + 1 == fijas) {
                    numeroFinal[indexABC[1]] = aBC[2]; // en b ponga a c
                    //   numeroPrueba[indexABC[1]] = aBC[2];
                    this.fijas += 1;
                } else {
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
    /**
     * Halla el resto de fijas en la secuencia de prueba y ajusta la secuencia en consecuencia.
     */
    private void hallarTodasFijas(int fijas) {
        if (this.fijas == fijas) {
            posiciones[aBC[0]][indexABC[1]] = true; //A ya estuvo en B
            posiciones[aBC[1]][indexABC[0]] = true; //A ya estuvo en B
            cambiarAB();
        } else if (this.fijas + 1 == fijas) { // alguno de los dos está bien
            if (posiciones[aBC[0]][indexABC[1]]) { //Si a ya estuvo en b
                numeroFinal[indexABC[0]] = aBC[1]; //En el index de a pongo b
                cambiarNuevoAB();
            } else if (posiciones[aBC[1]][indexABC[0]]) { //Si B ya estuvo en A
                numeroFinal[indexABC[1]] = aBC[0]; //En el index de b pongo A
                cambiarNuevoAB();
            } else {
                usarFija();
            }
            this.fijas++;
        } else if (this.fijas + 2 == fijas) { // el lugar al que cambiaron están bien
            numeroFinal[indexABC[1]] = aBC[0];
            numeroFinal[indexABC[0]] = aBC[1];
            cambiarNuevoAB();
            this.fijas += 2;
        } else if (this.fijas - 1 == fijas) {
            numeroPrueba[indexABC[2]] = aBC[2]; //C lo pone en su verdadera posición
            numeroFinal[indexABC[0]] = aBC[1]; //En el número final ubica B en el index de A
            numeroPrueba[indexABC[1]] = aBC[0];
            posiciones[aBC[0]][indexABC[1]] = true; //En el index de B pone A como que ya se lanzó
            cambiarNuevoAB();

        } else if (this.fijas - 2 == fijas) {
            numeroPrueba[indexABC[2]] = aBC[2]; //C lo pone en su verdadera posición
            numeroFinal[indexABC[1]] = aBC[0]; //En el número final ubica A en el index de b
            numeroPrueba[indexABC[1]] = aBC[0];
            posiciones[aBC[1]][indexABC[0]] = true; //En el index de A pone B como que ya se lanzó
            cambiarNuevoAB();
        }
    }
    /**
     * Cambia los valores de A y B según las nuevas posiciones obtenidas y actualiza la secuencia de prueba.
     */
    private void cambiarNuevoAB() {
        Integer[] nuevoIndex = buscarNuevoIndexIJ(indexABC[0]);
        indexABC[0] = nuevoIndex[0];
        indexABC[1] = nuevoIndex[1];
        aBC[0] = numeroPrueba[indexABC[0]];
        aBC[1] = numeroPrueba[indexABC[1]];
        numeroPrueba[indexABC[1]] = aBC[0]; //En el index de b pongo a
        numeroPrueba[indexABC[0]] = aBC[1]; //En el index de a pongo b

    }

    /**
     * Reubica los valores de A y B en la secuencia de prueba según la nueva configuración.
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
    /**
     * Reubica los valores de A y B en la secuencia de prueba según la nueva configuración.
     */
    private void tiroInicial(int fijas) {
        this.fijas = fijas;
        indexABC[0] = 0;
        indexABC[1] = 1;
        aBC[0] = numeroPrueba[0];
        aBC[1] = numeroPrueba[1];
        alistarNumero(false);
    }

    /**
     * Realiza el primer tiro inicial, estableciendo los valores de A y B.
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
