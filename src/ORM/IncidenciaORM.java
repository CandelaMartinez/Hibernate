/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ORM;

import Utils.Utilidades;
import dam_m06_uf2_act2_mariacandelamartinez.Models.Empleado;
import dam_m06_uf2_act2_mariacandelamartinez.Models.Historial;
import dam_m06_uf2_act2_mariacandelamartinez.Models.Incidencia;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author Candela
 */
public class IncidenciaORM {
     int opcion;

    public IncidenciaORM(Empleado miEmpleado) {
             SessionFactory miFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Empleado.class)
                .addAnnotatedClass(Incidencia.class)
                .addAnnotatedClass(Historial.class)
                .buildSessionFactory();
        
        Session miSesion= miFactory.openSession();
        
         do{
            do{
            System.out.println("\n..........GESTION INCIDENCIAS.............");
            System.out.println("1- Obtener una incidencia desde su id.");
            System.out.println("2- Listar todas las incidencias .");
            System.out.println("3- Insertar nueva incidencia.");
            System.out.println("4- Obtener las incidencias donde el empleado registrado es destino .");
            System.out.println("5- Obtener las incidencias creadas por el usuario registrado.");
            System.out.println("0- Volver al menu anterior.");
            
            opcion= Utilidades.pideEntero("Selecciona una de las opciones anteriores");
            
            if(opcion<0||opcion>5){
                System.out.println("************Debes elegir una opcion entre 0 y 5");
            }
            
            }while (opcion<0 || opcion>5);
            
              try{
             switch(opcion){
            case 1:
                 obtengoIncidencia(miSesion);
                break;
            case 2:
                 muestroIncidencias(miSesion);
                
                break;
            case 3:
                insertoIncidencia(miSesion);
                break;
            case 4:
                obtenerIncidenciasDestino(miSesion, miEmpleado);
                break;
            case 5:
                obtenerIncidenciasOrigen(miSesion, miEmpleado);
                break;
            case 0:
                
                
                break;
                
        }
            
        }catch(Exception e){
                  System.err.println("NO SE PUDO REALIZAR LA OPERACION");
            
        }finally{
            //miSesion.close();
	    //miFactory.close();
        }
            
                     
        }while(opcion!=0);
         
           
    }

    public  void obtengoIncidencia(Session miSesion) {
		
		int idIncidencia = Utilidades.pideEntero("id de Incidencia: ");
		try {
			Incidencia miIncidencia = (Incidencia) miSesion
					.createQuery("from Incidencia INCI where INCI.idincidencia = " + idIncidencia)
					.getSingleResult();
                        
			
				System.out.println("\n"+miIncidencia.toString());
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
                        System.out.println("No se encontro esa incidencia.");
		}
	}
    
    public void muestroIncidencias(Session miSesion){
        List<Incidencia> listaIncidencias= miSesion.createQuery("from Incidencia").getResultList();
        
        for(Incidencia inci: listaIncidencias){
            System.out.println("\n Incidencia: "+inci.toString());
        }
    }
    
    public void insertoIncidencia(Session miSesion){
        Empleado miEmpleadoOrigen= null;
        Empleado miEmpleadoDestino= null;
       
        //obtengo el usuario de origen
        String nombreusuarioorigen= Utilidades.pideFrase("Usuario origen: ");
        miEmpleadoOrigen= EmpleadoORM.obtengoEmpleado(miSesion,nombreusuarioorigen);
        
        //obtengo el usuario de destino
        String nombreusuariodestino= Utilidades.pideFrase("Usuario destino: ");
        miEmpleadoDestino= EmpleadoORM.obtengoEmpleado(miSesion,nombreusuarioorigen);
         
        //obtengo la fecha actual
        Calendar fecha= new GregorianCalendar();
        
        int ano= fecha.get(Calendar.YEAR);
        int mes= fecha.get(Calendar.MONTH);
        int dia= fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        
        String fechahora= ano+"/"+(mes+1)+"/"+dia+" "+ hora+ ":"+ minuto+":"+ segundo ;
        //System.out.println(fechahora);
        
        //obtengo el campo detalle
        String detalle= Utilidades.pideFrase("Detalle: ");
        
        //obtengo el campo tipo
        int tipoint;
        String tipo="";
        do{
            
        tipoint= Utilidades.pideEntero("Tipo: \n 1- Normal \n 2-Urgente");
        
        if(tipoint==1){
            tipo="Normal";
        }else if(tipoint==2){
            tipo="Urgente";
        }else{
            System.out.println("Debes insertar 1 o 2");
        }
            
        }while(tipoint<1 || tipoint>2);
       
        //creo la incidencia con el contructor usando todos sus campos
        Incidencia miIncidencia= new Incidencia(miEmpleadoOrigen, miEmpleadoDestino, fechahora, detalle, tipo);
       
        //agrego la incidencia a los empleados
        miEmpleadoOrigen.setIncidenciaOrigen(miIncidencia);
        miEmpleadoDestino.setIncidenciaDestino(miIncidencia);
        
        if(miIncidencia.getTipo().equals("Urgente")){
            HistorialORM.creoEventoCreacionIncidenciasUrgentes(miEmpleadoOrigen, miSesion);
        }
        
        //comienzo la transaccion de guardar esta incidencia en la bbdd
        miSesion.beginTransaction();
        miSesion.save(miIncidencia);
        miSesion.getTransaction().commit();
        
        System.out.println("Incidencia insertada.");
        
        
    }
    
    public void obtenerIncidenciasDestino(Session miSesion, Empleado miEmpleado){
        
        List<Incidencia>misIncidenciasDestino= miEmpleado.getIncidenciasDestino();
        for(Incidencia inci: misIncidenciasDestino){
            System.out.println("\n Incidencia: "+ inci.toString());
        }
        
        HistorialORM.creoEventoConsultaIncidenciasDestino(miEmpleado, miSesion);
        
    }

      public void obtenerIncidenciasOrigen(Session miSesion, Empleado miEmpleado){
        
        List<Incidencia>misIncidenciasOrigen= miEmpleado.getIncidenciasOrigen();
        for(Incidencia inci: misIncidenciasOrigen){
            System.out.println("\n Incidencia: "+ inci.toString());
        }
        
    }
    
}
