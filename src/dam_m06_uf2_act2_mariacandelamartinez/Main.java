/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam_m06_uf2_act2_mariacandelamartinez;

import ORM.*;
import ORM.IncidenciaORM;
import Utils.Utilidades;
import dam_m06_uf2_act2_mariacandelamartinez.Models.Empleado;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Candela
 */
public class Main {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         int opcion;

         String jdbcUrl="jdbc:mysql://localhost:3306/m06_incidencias?useSSL=false";
         String usuario="root";
         String contra="";
       try{
          Connection miconexion= DriverManager.getConnection(jdbcUrl,usuario,contra);
          System.out.println("exito al establecer la conexion con la DB");
          
         
         
          
         
           
       }catch(Exception e){
           e.printStackTrace();
       }
       
        
        do{
            do{
            System.out.println("\n..........GESTION GENERAL.............");
            System.out.println("1- Gestion de Empleados");
            System.out.println("2- Gestion del Historial.");
            System.out.println("0- Salir de este menu.");
            
            opcion= Utilidades.pideEntero("Selecciona una de las opciones anteriores");
            
            if(opcion<0||opcion>5){
                System.out.println("************Debes elegir una opcion entre 0 y 2");
            }
            
            }while (opcion<0 || opcion>2);
            
              try{
             switch(opcion){
            case 1:
                EmpleadoORM gestionEmpleado= new EmpleadoORM();
                break;
            case 2:
             
                HistorialORM getionHistorial= new HistorialORM();
                break;
           
            case 0:
             
                break;
                
        }
            
        }catch(Exception e){
                  System.err.println("NO SE PUDO REALIZAR LA OPERACION");
                  
            
        }
            
                     
        }while(opcion!=0);
        
     
      
       
       
       
       
    }
    
}
