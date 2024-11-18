package hn.grupo1.views.cursos;

import java.util.List;

import hn.grupo1.data.Cursos;

public interface CursosViewModel {
	void mostrarCursosEnGrid(List<Cursos> items);
	void mostrarMensajeError(String mensaje);
	void mostrarMensajeExito(String mensaje);
}
