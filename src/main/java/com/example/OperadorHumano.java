package com.example;



public class OperadorHumano {
    private String ficha;
    private String nombreCompleto;
    private String correo;

    public OperadorHumano(String ficha, String nombreCompleto, String correo){
        this.ficha = ficha;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;

    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    @Override 
    public String toString(){
        return "OperadorHumano{ficha=" + ficha + ", nombreCompleto=" + nombreCompleto + ", correo=" + correo + "}";
    }

}
    

    
    



