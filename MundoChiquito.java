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
        // Nos saltamos la linea del header
        sc.nextLine();
        while(sc.hasNextLine()) {
            String nombre = sc.next();
            String nivel = sc.next();
            String atributo = sc.next();
            String poder = sc.next();

            try {
                int n = Integer.parseInt(nivel);
                int p = Integer.parseInt(poder);
                CartaMostro monstruo = new CartaMostro(nombre, n, atributo, p);
                cartas.add(monstruo);
            } catch (IllegalArgumentException e) {
                System.out.println("Ha ocurrido un error leyendo los atributos de la carta" + nombre);
                e.printStackTrace();
                System.exit(1);
            }
        }
        return cartas;
    }
    public static class mazo {

        private HashMap<CartaMostro, ArrayList<CartaMostro>> grafo;

        public mazo(Scanner sc) {
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
          
        private static boolean estanConectadas(CartaMostro carta1, CartaMostro carta2) {
            boolean conectadas = false;
            boolean mismoNivel = carta1.getNivel().equals(carta2.getNivel());
            boolean mismoPoder = carta1.getPoder().equals(carta2.getPoder());
            boolean mismoAtributo = carta1.getAtributo().equals(carta2.getAtributo());

            // esto se podria resumir en un solo If, sin embargo tendria una condicion un poco larga
            if (mismoNivel && !mismoPoder && !mismoAtributo) {
                conectadas = true;
            } else if (mismoPoder && !mismoNivel && !mismoAtributo){
                conectadas = true;
            } else if ((mismoAtributo && !mismoPoder && !mismoNivel)){
                conectadas = true;
            } 

            return conectadas;
        }


        public ArrayList<ArrayList<String>> chiquito() {

            ArrayList<ArrayList<String>> ternas = new ArrayList<ArrayList<String>>();

            for(CartaMostro carta: grafo.keySet()) {
                for(CartaMostro vecino: grafo.get(carta)){
                    for(CartaMostro vecinoDelVecino: grafo.get(vecino)){
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
    }

    public static void main(String[] args) {

        try {

            File file = new File("deck.csv");
            Scanner sc = new Scanner(file); 
            mazo mazo = new mazo(sc);
            ArrayList<ArrayList<String>> ternas = mazo.chiquito();
            sc.close();

            for (ArrayList<String> terna: ternas){
                System.out.printf("%s %s %s \n", terna.get(0), terna.get(1), terna.get(2));  
            }

        } catch (FileNotFoundException e) {
            System.out.println("Hubo un error al encontrar el documento deck.csv");
            System.exit(1);
        }
    }
}

