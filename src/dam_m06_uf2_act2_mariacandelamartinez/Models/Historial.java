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
@Table(name="historial")
public class Historial implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idevento")
    private int idevento;
    
    @Column(name="tipo")
    private String tipo;
     
    @Column(name="fechahora")
    private String fechahora;
    
     //relacion de varios a uno desde historial a empleado
    //el campo empleado es el que une las dos tablas, clave foranea
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn (name="empleado")
    private Empleado empleado;
    
    

    //obligatorio tener constructor sin atributos para usar Hibernte
    public Historial() {
    }

    
    public Historial( String tipo, String fechahora, Empleado empleado) {
        this.tipo = tipo;
        this.fechahora = fechahora;
        this.empleado = empleado;
    }


    public int getIdevento() {
        return idevento;
    }

     public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Historial{" + "idevento=" + idevento + ", tipo=" + tipo + ", fechahora=" + fechahora + ", empleado=" + empleado + '}';
    }

 


    
}
