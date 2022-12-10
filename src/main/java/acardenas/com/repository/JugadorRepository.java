package acardenas.com.repository;

import acardenas.com.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    @Query("SELECT j FROM Jugador j WHERE j.equipo.id = ?1")
    List<Jugador> findJugadorByEquipo(Integer equipoId);

    @Query("SELECT j FROM Jugador j WHERE j.name = ?1 and j.equipo.id = ?2")
    Optional<Jugador> findJugadorUnico(String name, Integer equipoId);

}
