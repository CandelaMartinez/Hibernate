/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ORM;

import static ORM.EmpleadoORM.obtengoEmpleado;
import static ORM.EmpleadoORM.validaEmpleado;
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
public class HistorialORM {
    int opcion;
             SessionFactory miFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Empleado.class)
                .addAnnotatedClass(Incidencia.class)
                .addAnnotatedClass(Historial.class)
                .buildSessionFactory();
        
        Session miSesion= miFactory.openSession();
     

    public HistorialORM() {
         SessionFactory miFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Empleado.class)
                .addAnnotatedClass(Incidencia.class)
                .addAnnotatedClass(Historial.class)
                .buildSessionFactory();
        
        Session miSesion= miFactory.openSession();
        
        
       
        
        do{
            do{
            System.out.println("\n..........GESTION HISTORIAL.............");
            System.out.println("1- Obtener fecha hora del ultimo inicio de sesion para un empleado");
            System.out.println("2- Obtener ranking de empleados con mas incidencias urgentes creadas .");
            System.out.println("3- Obtener posicion dentro del ranking para un empleado.");
            System.out.println("0- Salir de este menu.");
            
            opcion= Utilidades.pideEntero("Selecciona una de las opciones anteriores");
            
            if(opcion<0||opcion>5){
                System.out.println("************Debes elegir una opcion entre 0 y 3");
            }
            
            }while (opcion<0 || opcion>3);
            
              try{
             switch(opcion){
            case 1:
                Empleado miEmpleado= EmpleadoORM.obtengoEmpleado(miSesion);
                obtenerUltimoInicioSesion(miEmpleado, miSesion);
                break;
            case 2:
              // Empleado miEmpleado2= EmpleadoORM.obtengoEmpleado(miSesion);
                obtenerListaEmpleadosIncidenciasUrgentes(miSesion);
              //  rankingIncidenciasUrgentes();
                break;
            case 3:
                 Empleado miEmpleado2= EmpleadoORM.obtengoEmpleado(miSesion);
                 posicionRankinEmpleado(miEmpleado2);
              break;
            case 0:
                miSesion.close();
	        miFactory.close();
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
    
    public static void creoEventoInicioSesion(Empleado miEmpleado, Session miSesion){
 
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
        
       // int idevento = 0;
        //el evento es del tipo I que es inicio de sesion
        Historial miEvento= new Historial("I", fechahora, miEmpleado);
        
        //agrego el evento al historial del empleado
        miEmpleado.addEventoToHistorial(miEvento);
        
        
        //comienzo la transaccion de guardar este evento en la bbdd
        miSesion.beginTransaction();
        miSesion.save(miEvento);
        miSesion.getTransaction().commit();
        
        System.out.println("Evento de inicio de sesion insertado en el historial.");
        
        
    }
    
    
        
      public static void creoEventoConsultaIncidenciasDestino(Empleado miEmpleado, Session miSesion){
 
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
        
       // int idevento = 0;
        //el evento es del tipo I que es inicio de sesion
        Historial miEvento= new Historial("C", fechahora, miEmpleado);
        
        //agrego el evento al historial del empleado
        miEmpleado.addEventoToHistorial(miEvento);
        
        
        //comienzo la transaccion de guardar este evento en la bbdd
        miSesion.beginTransaction();
        miSesion.save(miEvento);
        miSesion.getTransaction().commit();
        
        System.out.println("Evento de consulta de incidencias destino insertado en el historial.");
        
        
    }
      
       public static void creoEventoCreacionIncidenciasUrgentes(Empleado miEmpleado, Session miSesion){
 
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
        
       // int idevento = 0;
        //el evento es del tipo I que es inicio de sesion
        Historial miEvento= new Historial("U", fechahora, miEmpleado);
        
        //agrego el evento al historial del empleado
        miEmpleado.addEventoToHistorial(miEvento);
        
        
        //comienzo la transaccion de guardar este evento en la bbdd
        miSesion.beginTransaction();
        miSesion.save(miEvento);
        miSesion.getTransaction().commit();
        
        System.out.println("Evento de creacion de incidencias urgentes insertado en el historial.");
        
        
    }
    

       
       public void obtenerUltimoInicioSesion(Empleado miEmpleado, Session miSesion){
        
           miSesion.beginTransaction();
           //Query<Historial> consulta= miSesion.createQuery("SELECT HIS FROM Historial HIS JOIN FETCH HIS.empleado WHERE HIS.empleado=:emp ORDER BY HIS.fechahora DESC", Historial.class);
           
           Query<Historial> consulta= miSesion.createQuery("SELECT HIS FROM Historial HIS JOIN FETCH HIS.empleado WHERE HIS.empleado=:emp and HIS.tipo='I'ORDER BY HIS.fechahora DESC", Historial.class);
           
           
           consulta.setParameter("emp",miEmpleado );
           
           List<Historial> miLista= consulta.getResultList();
           miSesion.getTransaction().commit();
           
            System.out.println("............."+miLista.get(0).toString());
            System.out.println("............."+miLista.get(0).getFechahora().toString());
           
         /*  for(Historial h: miLista){
               System.out.println(h.toString());
           }*/
    
          
           
             
       }
       
       public void obtenerListaEmpleadosIncidenciasUrgentes(Session miSesion){
           miSesion.beginTransaction();
           Query<Historial> consulta= miSesion.createQuery("SELECT  HIS FROM Historial HIS JOIN FETCH HIS.empleado WHERE HIS.tipo='U'ORDER BY HIS.empleado", Historial.class);
          
            //Query<Historial> consulta= miSesion.createQuery("SELECT HIS FROM Historial HIS JOIN FETCH HIS.empleado WHERE HIS.tipo='U'", Historial.class);
            
            //Query<Historial> consulta= miSesion.createQuery("SELECT HIS FROM Historial HIS JOIN FETCH HIS.empleado WHERE HIS.tipo='U' GROUP BY HIS.empleado  ", Historial.class);

             //Query<Historial> consulta= miSesion.createQuery("SELECT COUNT * FROM Historial HI JOIN FETCH HI.empleado WHERE HIS.tipo='U' ", Historial.class);

           
           List<Historial> miLista= consulta.getResultList();
           miSesion.getTransaction().commit();
           
           for( Historial h: miLista){
           
               System.out.println(h);
               
           }
       
       }
       
       
    private void rankingIncidenciasUrgentes() {
        try {
            miSesion.beginTransaction();
            String q = "select h.empleado, count(h.empleado) as contador from Historial h where tipo = 'U' group by h.empleado order by contador desc";
            Query query = miSesion.createQuery(q);

            Object[] listResult = (Object[]) query.list().get(0);

            String empleado = (String) listResult[0];
            Number count = (Number) listResult[1];
            System.out.println("Empleado con mas incidencias urgentes '" + empleado + "' tiene '" + count + "' incidencias");
        } catch (Exception e) {
        } finally {
            miSesion.close();
        }

    }
       
    private void posicionRankinEmpleado(Empleado miempleado) {
        try {
            miSesion.beginTransaction();
            Empleado e = (Empleado) miSesion.get(Empleado.class,miempleado);
            if (e != null) {
                String q = "select h.empleado, count(h.empleado) as contador from Historial h where tipo = 'U' group by h.empleado order by contador desc";
                Query query = miSesion.createQuery(q);

                List<Object[]> listResult = (List<Object[]>) query.list();
                int i = 0;
                for (Object[] row : listResult) {
                    i++;
                    String empleado = (String) row[0];
                    Number count = (Number) row[1];
                    if (empleado==empleado) {
                        System.out.println("El ranking del empleado es " + miempleado.getNombreusuario() + " " + i);
                        break;
                    }
                }
            } else {
                System.out.println("el empleado no existe");
            }
        } catch (Exception e) {
            System.out.println("el empleado no existe");
        } finally {
            miSesion.close();
        }
    }

       
       

}    
    
    

