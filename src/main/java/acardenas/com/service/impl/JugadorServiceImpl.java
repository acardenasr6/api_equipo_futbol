package acardenas.com.service.impl;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Equipo;
import acardenas.com.model.Jugador;
import acardenas.com.repository.EquipoRepository;
import acardenas.com.repository.JugadorRepository;
import acardenas.com.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    @Autowired
    public JugadorServiceImpl(JugadorRepository jugadorRepository, EquipoRepository equipoRepository){
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }


    @Override
    public List<Jugador> listar(Map<String, String> param) {

        if (Optional.ofNullable(param.get("name")).isPresent()){
            return jugadorRepository.findAll()
                    .stream()
                    .filter(jugador -> jugador.getName().equalsIgnoreCase(param.get("name")))
                    .collect(Collectors.toList());
        }

        if (Optional.ofNullable(param.get("equipo")).isPresent()){
            return jugadorRepository.findAll()
                    .stream()
                    .filter(jugador -> jugador.getEquipo().getName().equalsIgnoreCase(param.get("equipo")))
                    .collect(Collectors.toList());
        }

        return jugadorRepository.findAll();
    }

    @Override
    public Optional<Jugador> obtener(Integer jugadorId) {
        return jugadorRepository.findById(jugadorId);
    }

    @Override
    public Jugador registrar(Jugador jugador) throws ValidationException {

        Optional.ofNullable(jugador.getName()).orElseThrow(
                () -> new ValidationException("¡El campo 'name' es requerido!"));

        Optional.ofNullable(jugador.getEquipo()).map(Equipo::getId).orElseThrow(
                () -> new ValidationException("¡El campo 'equipo.id' es requerido!"));

        Optional.ofNullable(jugador.getGoals()).orElseThrow(
                () -> new ValidationException("¡campo 'goals' es requerido!"));

        Optional<Jugador> jugadorRegistrado = jugadorRepository
                .findJugadorByUnico(jugador.getName(), jugador.getEquipo().getId());

        Optional<Equipo> equipo = equipoRepository.findById(jugador.getEquipo().getId());

        if (equipo.isEmpty()){
            throw new ValidationException("¡El equipo no existe!");
        }

        if (jugadorRegistrado.isPresent()){
            throw new ValidationException("¡El jugador ya existe!");
        }

        return jugadorRepository.save(jugador);
    }

    @Override
    public Jugador actualizar(Jugador jugador) throws ValidationException {

        Optional.ofNullable(jugador.getId()).orElseThrow(
                () -> new ValidationException("¡No ha enviado el Id del jugador!"));


        Jugador jugadorBd = obtener(jugador.getId()).orElseThrow(
                () -> new ValidationException(String.format("¡No existe el jugador con el ID %s!",
                        jugador.getId())));

        Jugador jugadorParaActualiza = mergeJugdor(jugador, jugadorBd);
        return jugadorRepository.save(jugadorParaActualiza);
    }

    @Override
    public boolean eliminar(Integer jugadorId) {
        try {
            jugadorRepository.deleteById(jugadorId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private Jugador mergeJugdor(Jugador jugadorInput, Jugador jugadorBd){

        return Jugador.builder()
                .id(jugadorBd.getId())
                .name(Optional.ofNullable(jugadorInput.getName())
                        .orElse(jugadorBd.getName()))
                .equipo(Optional.ofNullable(jugadorInput.getEquipo())
                        .orElse(jugadorBd.getEquipo()))
                .goals(Optional.ofNullable(jugadorInput.getGoals())
                        .orElse(jugadorBd.getGoals()))
                .createdAt(jugadorBd.getCreatedAt())
                .updatedAt(new Date())
                .build();
    }
}
