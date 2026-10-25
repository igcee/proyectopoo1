package cr.ac.tec.ecohydro.modelo;



public class OperadorHumano {
    private String ficha;
    private String nombreCompleto;
    private String correo;

    public OperadorHumano(String ficha, String nombreCompleto, String correo){
        setFicha(ficha);
        setNombreCompleto(nombreCompleto);
        setCorreo(correo);

    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        if (ficha == null || ficha.trim() .isEmpty()) {
            throw new IllegalArgumentException("La ficha no puede ser nula o vacía.");
        }
        this.ficha = ficha.trim();
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede ser nulo o vacío.");
        }
        this.nombreCompleto = nombreCompleto.trim();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || !correo.trim().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new IllegalArgumentException("El correo no tiene un formato valido.");
        }
        this.correo = correo.trim();
    }


    @Override 
    public String toString(){
        return "OperadorHumano{ficha=" + ficha + ", nombreCompleto=" + nombreCompleto + ", correo=" + correo + "}";
    }

}
    

    
    



