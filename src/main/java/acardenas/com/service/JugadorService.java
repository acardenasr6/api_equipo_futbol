package acardenas.com.service;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Jugador;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface JugadorService {

    List<Jugador> listar(Map<String, String> param);

    Optional<Jugador> obtener(Integer jugadorId);

    Jugador registrar(Jugador jugador) throws ValidationException;

    Jugador actualizar(Jugador jugador) throws ValidationException;

    boolean eliminar(Integer jugadorId);
}
