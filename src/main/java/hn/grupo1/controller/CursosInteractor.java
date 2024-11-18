package hn.grupo1.controller;

import hn.grupo1.data.Cursos;

public interface CursosInteractor {
	void consultarCursos();
	void agregarCurso(Cursos nuevo);
	void editarCurso(Cursos existente);
}
