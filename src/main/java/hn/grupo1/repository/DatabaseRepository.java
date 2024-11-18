package hn.grupo1.repository;


import hn.grupo1.data.EmpleadosResponse;
import hn.grupo1.data.Cursos;
import hn.grupo1.data.CursosResponse;
import hn.grupo1.data.Empleados;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DatabaseRepository {
	@Headers({
	    "Accept: application/vnd.github.v3.full+json",
	    "User-Agent: Sistema gestion de desarrollo humano"
	})
	@GET("pls/apex/gestionuth/appgestion/empleados")//https://apex.oracle.com/
	Call<EmpleadosResponse> ObtenerEmpleados();
	
	@Headers({
		"Accept: application/vnd.github.v3.full+json",
	    "User-Agent: Sistema gestion de desarrollo humano"
	})
	@POST("pls/apex/gestionuth/appgestion/empleados")
	Call<ResponseBody> CrearEmpleados(@Body Empleados nuevo);
	
	@Headers({
		"Accept: application/vnd.github.v3.full+json",
	    "User-Agent: Sistema gestion de desarrollo humano"
	})
	@PUT("pls/apex/gestionuth/appgestion/empleados")
	Call<ResponseBody> ActualizarEmpleados(@Body Empleados nuevo);
	
	//CURSOS
	@Headers({
	    "Accept: application/vnd.github.v3.full+json",
	    "User-Agent: Sistema gestion de desarrollo humano"
	})
	  @GET("/pls/apex/gestionuth/appgestion/cursos")
	    Call<CursosResponse> obtenerCursos();
	
	    @Headers({
	        "Accept: application/json",
	        "Content-Type: application/json",
	        "User-Agent:Sistema gestion de desarrollo humano"
	    })
	    @POST("/pls/apex/gestionuth/appgestion/cursos")
	    Call<ResponseBody> agregarCurso(@Body Cursos nuevo);
	    
	    @Headers({
	        "Accept: application/json",
	        "Content-Type: application/json",
	        "User-Agent: Sistema gestion de desarrollo humano"
	    })
	    @POST("/pls/apex/gestionuth/appgestion/cursos")
	    Call<ResponseBody> editarCurso(@Body Cursos curso);
}
