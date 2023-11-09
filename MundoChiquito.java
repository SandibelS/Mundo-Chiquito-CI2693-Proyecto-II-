import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MundoChiquito {
    
    public static LinkedList<CartaMostro> lecturaCSV(Scanner sc) {
        LinkedList<CartaMostro> cartas = new LinkedList<CartaMostro>();
        sc.useDelimiter(",|(\\n)|(\r\n)");

        // Creo que el archivo de pruebas del profesor va a contener
        // el header de "nombre, nivel, ..." que se debe evitar
        while(sc.hasNextLine()) {
            String nombre = sc.next();
            String nivel = sc.next();
            String atributo = sc.next();
            String poder = sc.next();

            // Creo que este if no deberia ir, 
            // se supone que el mazo incluye cartas repetidas
            // y esto es importante para poder devolver ternas que si sean 
            // posibles, aunque al final resulta que el archivo no 
            // tendra estas cartas repetidas 
            // if (!contains(nombre, lista)) {

                // La comprobacion de los atributos por lo general se hace en el 
                // propio constructor de la clase y con un try/catch se capturan los errores

                // if (elements(atributo) && level(nivel) && power(poder)) {
                //     int n = Integer.parseInt(nivel);
                //     int p = Integer.parseInt(poder);
                //     CartaMostro monstruo = new CartaMostro(nombre, n, atributo, p);
                //     lista.add(monstruo);
                // } else {
                //     System.out.println("Alguno de los valores es incorrecto");
                // }               
            // } 

            try {
                int n = Integer.parseInt(nivel);
                int p = Integer.parseInt(poder);
                CartaMostro monstruo = new CartaMostro(nombre, n, atributo, p);
                cartas.add(monstruo);
            } catch (IllegalArgumentException e) {
                System.out.println("Ha ocurrido un error leyendo los atributos de la carta" + nombre);
                e.printStackTrace();
            }
        }
        sc.close();
        return cartas;
    }
    
    //  No es necesario por ahora
    // static boolean contains(String nombre , LinkedList<CartaMostro> lista) {
    //     for(CartaMostro i: lista) {
    //         if (i.getNombre().equals(nombre)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // Estas funciones de comprobacion no deberian ir aqui, ensucian el codigo
    // static boolean elements(String elemento) {
    //     String elementos[] = {"AGUA", "FUEGO", "VIENTO", "TIERRA", "LUZ", "OSCURIDAD", "DIVINO"};
    //     for (String i: elementos) {
    //         if (i.equals(elemento)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // static boolean level(String nivel) {
    //     int n = Integer.parseInt(nivel);
    //     if (n <= 12 && 1 <= n) {
    //         return true;
    //     }
    //     return false;
    // }

    // static boolean power(String poder) {
    //     int p = Integer.parseInt(poder) % 50;
    //     if (p == 0) {
    //         return true;
    //     } else {
    //        return false; 
    //     }
    // }

    public static class mazo {

        // En el primer proyecto estaba bien usar la estructura LinkedList
        // dado que estabamos implementado la version de Meza, pero como 
        // tenemos a eleccion la repreesentacion del grafo, es mala idea 
        // usarlo, es preferible usar ArrayList
        // private LinkedList<CartaMostro> vertices;

        // En vez de hacer una implementacion de lista de listas
        // una implementacion de hashMap a la lista de sucesores 
        // es preferible para evitar el uso de nodos.
        private HashMap<CartaMostro, ArrayList<CartaMostro>> grafo;

        public mazo(Scanner sc) {
            // this.vertices = lecturaCSV(sc);

            // Agregamos los vertices
            grafo = new HashMap<CartaMostro, ArrayList<CartaMostro>>();
            LinkedList<CartaMostro> cartas = lecturaCSV(sc);
            for (CartaMostro carta: cartas) {
                grafo.put(carta, new ArrayList<CartaMostro>());
            }

            // Ahora agregamos los vecinos para cada carta, es importante notar
            // que se trata de un grafo no dirigido 
            for (CartaMostro carta1: grafo.keySet()){
                ArrayList<CartaMostro> vecinos = grafo.get(carta1);
                for (CartaMostro carta2: grafo.keySet()){
                    if (!carta1.equals(carta2) && estanConectadas(carta1, carta2)) {
                        if(!vecinos.contains(carta2)){
                            vecinos.add(carta2);
                        } 
                    }
                }
            }
        }
            
        // Esta funcion podria generar errores por como Java maneja las referencias
        // y no veo que se use en otra parte
        // public LinkedList<CartaMostro> getBaraja() {
            // return this.vertices;
        // }

        // No es conveniente estar construyendo la lista de vecinos todo el tiempo
        // esto va en el constructor y luego cada vez que queramos los vecinos
        // basta con buscarlo en el grafo mismo
        // public LinkedList<CartaMostro> sucesores(CartaMostro vertice) {
        //     LinkedList<CartaMostro> sucesores = new LinkedList<CartaMostro>();
        //     for(CartaMostro i: this.vertices) {
        //         if (!i.equals(vertice) && estanConectadas(vertice, i)) {
        //             sucesores.add(i);
        //         }
        //     }
        //     return sucesores;
        // }


        // Esta funcion esta muy buena como shortcut y como solo la
        // usamos en ese sentido la volvemos privada
        private static boolean estanConectadas(CartaMostro carta1, CartaMostro carta2) {
            if (carta1.getNivel().equals(carta2.getNivel()) ||
                carta1.getPoder().equals(carta2.getPoder()) || 
                carta1.getAtributo().equals(carta2.getAtributo())) {
                return true;
            } else {
                return false;
            }
        }

        // Por lo general la generacion de una solucion y la impresion de la misma
        // se hacen por separado. Por eso vamos a hacer que esto retorne la lista
        // de ternas
        public ArrayList<ArrayList<String>> chiquito() {
            // Necesitamos una variable en donde ir guardando las ternas construidas
            ArrayList<ArrayList<String>> ternas = new ArrayList<ArrayList<String>>();

            // Por como es la implementacion del Stack por parte de Java es posible 
            // que no sea la mejor estructura para llevar estas ternas que solo son 
            // de tamaño 3. Por otro lado, es mejor tener la inicializacion dentro del
            // for
            // Stack<String> terna = new Stack<String>();
        
            // for(CartaMostro i: this.vertices) {
            //     terna.push(i.getNombre());
            //     chiquitoR(1, i, terna);
            //     terna.pop();
            // }

            // Realmente no necesitamos dos funciones, con tres for anidados funciona
            // Esto no cambia la complejidad de la solucion y seguimos haciendo un
            // backtracking

            for(CartaMostro carta: grafo.keySet()) {
                for(CartaMostro vecino: grafo.get(carta)){
                    for(CartaMostro vecinoDelVecino: grafo.get(vecino)){
                        // no deberiamos crear una terna en la que la carta que 
                        // te quedas del mazo sea la que tenias en la mano, ya 
                        // que esto no tiene sentido
                        // Pero como el archivo del mazo no tendra las 
                        // cartas repetidas, lo obviamos
                        // if(vecinoDelVecino.equals(carta)){    
                        //     continue;
                        // }
                        ArrayList<String> terna = new ArrayList<String>();
                        terna.add(carta.getNombre());
                        terna.add(vecino.getNombre());
                        terna.add(vecinoDelVecino.getNombre());
                        ternas.add(terna);
                    }
                }
            }

            return ternas;
        }

        // public void chiquitoR(int indicador, CartaMostro vertice, Stack<String> terna) {
        //     if (indicador != 2) {
        //         for(CartaMostro i: sucesores(vertice)) {
        //             terna.push(i.getNombre());
        //             chiquitoR(2, i, terna); 
        //             terna.pop();
        //         }
        //         } else {
        //         for(CartaMostro i: sucesores(vertice)) {
        //             terna.push(i.getNombre());
        //             System.out.println(terna.toString());
        //             terna.pop();
        //         }
        //     }            
        // }


    }

    public static void main(String[] args) {

        try {

            File file = new File("deck.csv");
            Scanner sc = new Scanner(file); 
            mazo mazo = new mazo(sc);
            ArrayList<ArrayList<String>> ternas = mazo.chiquito();
            for (ArrayList<String> terna: ternas){
                System.out.printf("%s %s %s \n", terna.get(0), terna.get(1), terna.get(2));  
            }

        } catch (FileNotFoundException e) {
            System.out.println("Hubo un error al encontrar el documento deck.csv");
        }
    }
}

