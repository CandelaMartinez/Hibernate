/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam_m06_uf2_act2_mariacandelamartinez.Models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Candela
 */
@Entity
@Table(name="empleado")
public class Empleado implements Serializable {
    
    @Id
    @Column(name="nombreusuario")
    private String nombreusuario;
    
    @Column(name="password")
    private String password;
    
    @Column(name="nombrecompleto")
    private String nombrecompleto;
    
    @Column(name="telefono")
    private String telefono;
    
    //relacion uno a varios desde empleado a incidencia
    //un empleado puede estar en varias incidencias
    //tanto de origen como destino
    //el campo por el que mapea en la clase incidencia es: origen y destino
    //fetch lazy me dara la informacion ondemand
    @OneToMany(mappedBy="origen",
            cascade={CascadeType.PERSIST,CascadeType.MERGE, 
                CascadeType.DETACH,CascadeType.REFRESH })
    private List <Incidencia> incidenciasOrigen;
    
      @OneToMany(mappedBy="destino",
            cascade={CascadeType.PERSIST,CascadeType.MERGE, 
                CascadeType.DETACH,CascadeType.REFRESH })
    private List <Incidencia> incidenciasDestino;
      
    //relacion uno a varios desde empleado a historial
    //un empleado puede estar en varias entradas al historial
    //el campo por el que mapea en la clase historial es empleado
   @OneToMany(mappedBy="empleado", cascade= {CascadeType.PERSIST, 
       CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Historial> listaHistorial;

    public List<Incidencia> getIncidenciasOrigen() {
        return incidenciasOrigen;
    }
    
   

    public void setIncidenciasOrigen(List<Incidencia> incidenciasOrigen) {
        this.incidenciasOrigen = incidenciasOrigen;
    }

    public void setIncidenciaOrigen(Incidencia incidenciaOrigen){
        this.incidenciasOrigen.add(incidenciaOrigen);
    }
    
    public List<Incidencia> getIncidenciasDestino() {
        return incidenciasDestino;
    }

    public void setIncidenciasDestino(List<Incidencia> incidenciasDestino) {
        this.incidenciasDestino = incidenciasDestino;
    }

    public void setIncidenciaDestino(Incidencia incidenciaDestino){
        this.incidenciasDestino.add(incidenciaDestino);
    }
    public List<Historial> getListaHistorial() {
        return listaHistorial;
    }

    public void setListaHistorial(List<Historial> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }
      public void addEventoToHistorial(Historial miHistorial){
        this.listaHistorial.add(miHistorial);
    }
    

   //obligatorio tener constructor sin atributos para usar Hibernte
    public Empleado() {
    }

    public Empleado(String nombreusuario, String password, String nombrecompleto, String telefono) {
        this.nombreusuario = nombreusuario;
        this.password = password;
        this.nombrecompleto = nombrecompleto;
        this.telefono = telefono;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Empleado{" + "nombreusuario=" + nombreusuario + ", password=" + password + ", nombrecompleto=" + nombrecompleto + ", telefono=" + telefono + '}';
    }
    
}
