
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class test {

    public static boolean mismoConjuntoSol(ArrayList<ArrayList<String>> ternasProyecto, 
                                    ArrayList<ArrayList<String>> ternasSolucion){

        HashSet<ArrayList<String>> conjSol = new HashSet<ArrayList<String>>();
        
        for (int i = 0; i < ternasSolucion.size(); i++){
            conjSol.add(ternasSolucion.get(i));
        }

        boolean sonElMismoConj = true;

        for (int i = 0; i < ternasProyecto.size(); i++) {

            if(!conjSol.contains(ternasProyecto.get(i))){
                System.out.println("Error: i = " + i);
                System.err.println(ternasProyecto.get(i));
                sonElMismoConj = false;
            } 
        }

        return sonElMismoConj;

    }
    public static void main(String[] args){

        ArrayList<ArrayList<String>> ternasProyecto = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> ternasSolucion = new ArrayList<ArrayList<String>>();

        try {

            File file = new File("test.txt");
            Scanner reader = new Scanner(file); 
            while (reader.hasNextLine()) {
                
                String data = reader.nextLine();
                String[] elementos = data.split(" ");

                ArrayList<String> nuevaTerna = new ArrayList<String>();

                nuevaTerna.add(elementos[0]);
                nuevaTerna.add(elementos[1]);
                nuevaTerna.add(elementos[2]);

                ternasProyecto.add(nuevaTerna);

            }
            reader.close();

            file = new File("output.txt");
            reader = new Scanner(file); 

            while (reader.hasNextLine()) {
                
                String data = reader.nextLine();
                String[] elementos = data.split(" ");

                ArrayList<String> nuevaTerna = new ArrayList<String>();

                nuevaTerna.add(elementos[0]);
                nuevaTerna.add(elementos[1]);
                nuevaTerna.add(elementos[2]);

                ternasSolucion.add(nuevaTerna);
            }
            reader.close();

            boolean esElMismoConj = mismoConjuntoSol(ternasProyecto, ternasSolucion);
            if (esElMismoConj){
                System.err.println("Se paso exitosamente las pruebas");
            } else {
                System.out.println("No se pasaron las pruebas");
            }
            


        } catch (FileNotFoundException e) {
            System.out.println("Hubo un error al encontrar los documentos");
        }

    }

}
