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
import java.io.IOException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author Candela
 */
public class EmpleadoORM {
    
    int opcion;
   
    public EmpleadoORM() {
        
          SessionFactory miFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Empleado.class)
                .addAnnotatedClass(Incidencia.class)
                .addAnnotatedClass(Historial.class)
                .buildSessionFactory();
        
        Session miSesion= miFactory.openSession();
        
           
        do{
            do{
            System.out.println("\n..........GESTION EMPLEADOS.............");
            System.out.println("1- Inserta nuevo empleado.");
            System.out.println("2- Valida la entrada de un empleado con usuario y contraseña .");
            System.out.println("3- Modifica el perfil de un empleado.");
            System.out.println("4- Cambia la contraseña de un empleado .");
            System.out.println("5- Elimina un empleado.");
            System.out.println("0- Salir de este menu.");
            
            opcion= Utilidades.pideEntero("Selecciona una de las opciones anteriores");
            
            if(opcion<0||opcion>5){
                System.out.println("************Debes elegir una opcion entre 0 y 5");
            }
            
            }while (opcion<0 || opcion>5);
            
              try{
             switch(opcion){
            case 1:
                 insertaEmpleado(miSesion);
                break;
            case 2:
                //le pido al usuario el usuario y la contraseña
                String nombreusuario= Utilidades.pideFrase("Usuario: ");
                String password= Utilidades.pideFrase("Contraseña: ");
                
                if( validaEmpleado(miSesion,nombreusuario, password)){
                    
                     Empleado miEmpleado= obtengoEmpleado(miSesion, nombreusuario);
              
                     HistorialORM.creoEventoInicioSesion(miEmpleado, miSesion);
                     
                     IncidenciaORM gestionIncidencia= new IncidenciaORM(miEmpleado);
                }
               
            
                //entro a otro menu
                break;
            case 3:
                modificaEmpleado(miSesion);
                break;
            case 4:
                cambiaContraseña(miSesion);
                break;
            case 5:
                eliminaEmpleado(miSesion);
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
        
    public void insertaEmpleado(Session miSesion){
        String nombreusuario= Utilidades.pideFrase("Escribe el usuario");
        String password= Utilidades.pideFrase("Escribe la contraseña");
        String nombrecompleto= Utilidades.pideFrase("Escribe el nombre completo");
        String telefono= Utilidades.pideFrase("Escribe el telefono");
       
            
        
        Empleado miEmpleadoNuevo= new Empleado(nombreusuario,password,nombrecompleto,telefono);
        //comienza la transaccion
        miSesion.beginTransaction();
            
        //guarda el empleado en la bbdd, gracias a las anotaciones en la clase Empleado, 
        //sabe donde poner cada campo
        
        miSesion.save(miEmpleadoNuevo);
            
        miSesion.getTransaction().commit();
            
        System.out.println("Empleado insertado");
        
    }    
    
    public static boolean validaEmpleado(Session miSesion,String nombreusuario, String password){
        //hago una consulta de todos los campos de la tabla empleados
        //me devuelve una lista de empleados por el mapeo que realiza Hibernate
        //con los getter y setter puedo trabajar con el objeto empleado
        List <Empleado> listaEmpleados= miSesion.createQuery("from Empleado").getResultList();
        
        //le pido al usuario el usuario y la contraseña
        //String nombreusuario= Utilidades.pideFrase("Usuario: ");
        //String password= Utilidades.pideFrase("Contraseña: ");
        boolean validado=false;
        
        //recorro la lista de empleados y obtengo los datos que necesito con los getter
        //los comparo con los datos que me dio el usuario
        for(Empleado miEmpleado: listaEmpleados){
            if(miEmpleado.getNombreusuario().equals(nombreusuario)){
                System.out.println("Usuario correcto");
                
                if(miEmpleado.getPassword().equals(password)){
                    System.out.println("Contraseña correcta");
                   
                    validado= true;
                }
            }
        }
        
        if(!validado){
         
            System.out.println("**********Los datos proporcionados no son correctos");

        }
        return validado;
    }
    
    public static Empleado obtengoEmpleado(Session miSesion){
        String nombreusuario= Utilidades.pideFrase("Escribe el usuario del perfil a obtener: ");
        Empleado miEmpleado= null;
        
        miSesion.beginTransaction();
        
        //mediante Hibernate
        //Empleado e= (Empleado)miSesion.get(Empleado.class,nombreusuario);
                
        Query<Empleado> consulta= miSesion.createQuery("SELECT EM FROM Empleado EM WHERE EM.nombreusuario=:nuser", Empleado.class);
        
        consulta.setParameter("nuser", nombreusuario);
        
        miEmpleado= consulta.getSingleResult();
        
        miSesion.getTransaction().commit();
        
        System.out.println("Emplead@: "+ miEmpleado.toString());
        
        return miEmpleado;
    }
    
       public static Empleado obtengoEmpleado(Session miSesion, String nombreusuario){
      //  String nombreusuario= Utilidades.pideFrase("Escribe el usuario del perfil a obtener: ");
        Empleado miEmpleado= null;
        
        miSesion.beginTransaction();
        
        Query<Empleado> consulta= miSesion.createQuery("SELECT EM FROM Empleado EM WHERE EM.nombreusuario=:nuser", Empleado.class);
        
        consulta.setParameter("nuser", nombreusuario);
        
        miEmpleado= consulta.getSingleResult();
        
        miSesion.getTransaction().commit();
        
        System.out.println("Emplead@: "+ miEmpleado.toString());
        
        return miEmpleado;
    }
    
    public void modificaEmpleado(Session miSesion){
               
       Empleado miEmpleado= obtengoEmpleado(miSesion);
        
        String password= Utilidades.pideFrase("Escribe la nueva contraseña: ");
        String nombrecompleto= Utilidades.pideFrase("Escribe el nuevo nombre completo");
        String telefono= Utilidades.pideFrase("Escribe el nuevo telefono");
        
        
        miEmpleado.setPassword(password);
        miEmpleado.setNombrecompleto(nombrecompleto);
        miEmpleado.setTelefono(telefono);
        
        //el update busca el empleado por el campo clave y lo actualiza
        //miSesion.update(miEmpleado);
        
        
        miSesion.beginTransaction();
        miSesion.save(miEmpleado);
        miSesion.getTransaction().commit();
        
        System.out.println("Empleado modificado.");
        System.out.println("Los nuevos datos son:"+ miEmpleado.toString());
        
              
          }  
        public void cambiaContraseña(Session miSesion){
            Empleado miEmpleado= obtengoEmpleado(miSesion);
            
            String password= Utilidades.pideFrase("Escribe la nueva contraseña: ");
            miEmpleado.setPassword(password);
            
            miSesion.beginTransaction();
            miSesion.save(miEmpleado);
            miSesion.getTransaction().commit();
            
             System.out.println("Contraseña modificada.");
            
        }
        public void eliminaEmpleado(Session miSesion){
            
            Empleado miEmpleado= obtengoEmpleado(miSesion);
            
            miSesion.beginTransaction();
            miSesion.delete(miEmpleado);
            miSesion.getTransaction().commit();
            
            System.out.println("Empleado eliminado.");
            
            
        }
        
        
        
     
        
       
    }

    
