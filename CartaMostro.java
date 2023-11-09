// Anteriormente "mon"
public class CartaMostro {
    private String  nombre; 
    private String  atributo;
    private Integer poder;
    private Integer nivel;

    public CartaMostro(String nombre, Integer nivel, String atributo, Integer poder) {

        if (nivel < 1 || nivel > 12){
            throw new IllegalArgumentException("nivel fuera del rango [1, 12]");
        }

        // Creo que el poder tambien deberia ser mayor p igual a cero, no estoy segua
        if(poder % 50 != 0){
            throw new IllegalArgumentException("poder debe ser un numero entero multiplo de 50");
        }

        String elementos[] = {"AGUA", "FUEGO", "VIENTO", "TIERRA", "LUZ", "OSCURIDAD", "DIVINO"};
        Boolean atributoValido = false;
        for (String elemento: elementos) {
            if (atributo.equals(elemento)) {
                atributoValido = true;
                break;
            }
        }
        if(!atributoValido){
            throw new IllegalArgumentException("atributo no permitido");
        }

        this.nombre = nombre;
        this.atributo = atributo;
        this.poder = poder;
        this.nivel = nivel;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    public Integer getPoder(){
        return this.poder;
    }
    public String getAtributo(){
        return this.atributo;
    }
    public Integer getNivel(){
        return this.nivel;
    }

}
