# Juego de Picas y Fijas (10 dígitos)
El juego de Picas y Fijas es un desafío de lógica en el que los jugadores intentan adivinar un número secreto de 10 dígitos. El objetivo es descubrir el número oculto realizando intentos sucesivos y recibiendo pistas en forma de "picas" y "fijas".

* Una "pica" indica que un dígito está presente en el número secreto pero en una posición incorrecta.
* Una "fija" indica que un dígito está presente en el número secreto y en la posición correcta.
## Estructura del Proyecto
El proyecto consta de tres clases principales:

1. **Juego:** Esta clase es responsable de determinar la cantidad de picas y fijas en un intento de adivinanza y de generar un número de 10 dígitos sin repeticiones.
2. **Main:** Aquí se gestiona una ronda del juego de Picas y Fijas, que finaliza únicamente cuando se alcanzan 10 fijas.
3. **LogicaFijas:** Contiene el algoritmo diseñado para adivinar el número secreto.

## Algoritmo de Adivinanza
El algoritmo de adivinanza se basa en un enfoque de intercambio de dígitos, comenzando con el primer dígito y probando todas las combinaciones posibles. Si el primer dígito no es una fija, se intercambia con los otros dígitos hasta que se encuentre una fija. Este proceso se repite hasta que se hallen todas las fijas.

Si el primer dígito resulta ser una fija, entonces el algoritmo continúa probando las combinaciones de los otros dígitos hasta encontrar todas las fijas.

## Resultados y Desafíos
El proyecto ha logrado alcanzar resultados notables en términos de eficiencia de adivinanza. La cantidad mínima de intentos necesarios para adivinar un número de 10 dígitos fue de 13, mientras que el máximo fue de 33. Este proyecto, aunque no hace uso de una amplia gama de tecnologías, demuestra la capacidad de implementar lógica compleja para resolver desafíos de juego.

## Ejecución
1. Clonar el repositorio de manera local:
    ```bash
   git clone <url-repositorio>
   cd <nombre-repositorio>
   ```
2. Compilar la aplicación haciendo uso de `javac`:
    ```bash
   cd src/
   javac Main.java
   ```
3. Ejecutar el juego, en donde se puede evidenciar como el algoritmo es capaz de adivinar un número random después de _n_ intentos
    ```bash
   java Main
   ```