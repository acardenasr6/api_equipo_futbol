package acardenas.com.repository;


import acardenas.com.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    @Query("SELECT e FROM Equipo e WHERE e.name = ?1 and e.city = ?2")
    Optional<Equipo> findEquipoUnico(String name, String city);
}
