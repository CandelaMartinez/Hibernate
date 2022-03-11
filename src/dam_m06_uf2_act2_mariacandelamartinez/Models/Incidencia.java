/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam_m06_uf2_act2_mariacandelamartinez.Models;

import dam_m06_uf2_act2_mariacandelamartinez.Models.Empleado;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Candela
 */
@Entity
@Table(name="incidencia")
public class Incidencia implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idincidencia") 
    private int idincidencia;
    
   //relacion de varios a uno desde incidencia a empleado
    //el campo origen es el que une las dos tablas, clave foranea
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn (name="origen")
    private Empleado origen;
    
     //relacion de varios a uno desde incidencia a empleado
    //el campo destino es el que une las dos tablas, clave foranea
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, 
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn (name="destino")
    private Empleado destino;
    
    @Column(name="fechahora")
    private String fechaHora;
    
    @Column(name="detalle")
    private String detalle;
    
    @Column(name="tipo")
    private String tipo;
    
    //obligatorio tener constructor sin atributos para usar Hibernte
    public Incidencia() {
    }

 
    
    public Incidencia(Empleado origen, Empleado destino, String fechaHora, String detalle, String tipo) {
        this.origen = origen;
        this.destino = destino;
        this.fechaHora = fechaHora;
        this.detalle = detalle;
        this.tipo = tipo;
    }

    public Empleado getOrigen() {
        return origen;
    }

    public void setOrigen(Empleado origen) {
        this.origen = origen;
    }

    public Empleado getDestino() {
        return destino;
    }

    public void setDestino(Empleado destino) {
        this.destino = destino;
    }

    public int getIdincidencia() {
        return idincidencia;
    }

    public void setIdincidencia(int idincidencia) {
        this.idincidencia = idincidencia;
    }

      public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Incidencia{" + "idincidencia=" + idincidencia + ", origen=" + origen + ", destino=" + destino + ", fechaHora=" + fechaHora + ", detalle=" + detalle + ", tipo=" + tipo + '}';
    }


  
    
}
