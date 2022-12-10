package acardenas.com.service;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Equipo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EquipoService {

    List<Equipo> listar(Map<String, String> param);

    Optional<Equipo> obtener(Integer equipoId);

    Equipo registrar(Equipo equipo) throws ValidationException;

    Equipo actualizar(Equipo equipo) throws ValidationException;

    boolean eliminar(Integer equipoId);

}
