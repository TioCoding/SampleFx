package pe.com.sample.bean.entity;

import java.util.List;

public class Entidad {

    private String nroDocumento;
    private String desicion;
    private List<String> variableList;

    public Entidad(){

    }

    public Entidad(String nroDocumento, String desicion) {
        this.nroDocumento = nroDocumento;
        this.desicion = desicion;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getDesicion() {
        return desicion;
    }

    public void setDesicion(String desicion) {
        this.desicion = desicion;
    }

    public List<String> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<String> variableList) {
        this.variableList = variableList;
    }
}
