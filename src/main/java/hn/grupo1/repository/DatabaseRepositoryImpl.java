package hn.grupo1.repository;

import java.io.IOException;

import hn.grupo1.data.EmpleadosResponse;
import hn.grupo1.data.Empleados;
import hn.grupo1.data.Cursos;
import hn.grupo1.data.CursosResponse;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Call;

public class DatabaseRepositoryImpl {
	
	 private static DatabaseRepositoryImpl INSTANCE;
	 private DatabaseClient client; 
	 
	
	 private DatabaseRepositoryImpl(String url, Long timeout) {
		 client =new DatabaseClient(url,timeout);
		 }
	 
	  
	    public static DatabaseRepositoryImpl getInstance(String url, Long timeout) {
	    	if (INSTANCE==null) {
	    		synchronized(DatabaseRepositoryImpl.class) {
	    			if(INSTANCE==null) {
	    				INSTANCE=new DatabaseRepositoryImpl(url,timeout);
	    			}
	    		}
	    	}
	   
	  
	        return INSTANCE;
	    }
	    
	public EmpleadosResponse ConsultarEmpleados() throws IOException {
		/*
		 * Call<EmpleadosResponse> call=client.getDatabase().ObtenerEmpleados();
		 * Response<EmpleadosResponse> response =call.execute();//Aqui se produce
		 * lallamada
		 * 
		 * if(response.isSuccessful()) {
		 * 
		 * return response.body(); }else {
		 * 
		 * return null; }
		 */
		try {
		Call<EmpleadosResponse> call = client.getDatabase().ObtenerEmpleados();
	     Response<EmpleadosResponse> response = call.execute();//Aqui se produce la llamada
	     if(response.isSuccessful()) {
	         return response.body();
	     } else {
	         System.err.println("Error en consulta: " + response.code() + " - " + response.message());
	         if (response.errorBody() != null) {
	             String errorBody = response.errorBody().string();
	             System.err.println("Error body: " + errorBody);
	         }
	         return null;
	     }
	 } catch (Exception e) {
	     System.err.println("Excepción al consultar cursos: " + e.getMessage());
	     e.printStackTrace();
	     throw e;
	 }
		
	
	}
	


     
	public boolean CrearEmpleados(Empleados nuevo) throws IOException {
		Call<ResponseBody> call = client.getDatabase().CrearEmpleados(nuevo);
		Response<ResponseBody> response = call.execute();//AQUI SE PRODUCE LA LLAMADA
		return response.isSuccessful();
	}
	
	public boolean ActualizarEmpleados(Empleados existente) throws IOException {
		Call<ResponseBody> call = client.getDatabase().ActualizarEmpleados(existente);
		Response<ResponseBody> response = call.execute();//AQUI SE PRODUCE LA LLAMADA
		return response.isSuccessful();
	}
	
	//CURSOS

    public CursosResponse obtenerCursos() throws IOException {
        try {
            Call<CursosResponse> call = client.getDatabase().obtenerCursos();
            Response<CursosResponse> response = call.execute();
            if(response.isSuccessful()) {
                return response.body();
            } else {
                System.err.println("Error en consulta: " + response.code() + " - " + response.message());
                if (response.errorBody() != null) {
                    String errorBody = response.errorBody().string();
                    System.err.println("Error body: " + errorBody);
                }
                return null;
            }
        } catch (Exception e) {
            System.err.println("Excepción al consultar cursos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public boolean agregarCurso(Cursos nuevo) throws IOException {
        try {
            Call<ResponseBody> call = client.getDatabase().agregarCurso(nuevo);
            Response<ResponseBody> response = call.execute();
            if (!response.isSuccessful()) {
                System.err.println("Error al agregar curso: " + response.code() + " - " + response.message());
                if (response.errorBody() != null) {
                    String errorBody = response.errorBody().string();
                    System.err.println("Error body: " + errorBody);
                }
            }
            return response.isSuccessful();
        } catch (Exception e) {
            System.err.println("Excepción al agregar curso: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public boolean editarCurso(Cursos existente) throws IOException {
        try {
            Call<ResponseBody> call = client.getDatabase().editarCurso(existente);
            Response<ResponseBody> response = call.execute();
            if (!response.isSuccessful()) {
                System.err.println("Error al editar curso: " + response.code() + " - " + response.message());
                if (response.errorBody() != null) {
                    String errorBody = response.errorBody().string();
                    System.err.println("Error body: " + errorBody);
                }
            }
            return response.isSuccessful();
        } catch (Exception e) {
            System.err.println("Excepción al editar curso: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}