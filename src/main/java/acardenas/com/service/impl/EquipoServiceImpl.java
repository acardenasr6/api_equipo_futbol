package acardenas.com.service.impl;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Equipo;
import acardenas.com.model.Jugador;
import acardenas.com.repository.EquipoRepository;
import acardenas.com.repository.JugadorRepository;
import acardenas.com.service.EquipoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;

    private final JugadorRepository jugadorRepository;

    public EquipoServiceImpl(EquipoRepository equipoRepository, JugadorRepository jugadorRepository){
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
    }

    @Override
    public List<Equipo> listar(Map<String, String> param) {

        if (Optional.ofNullable(param.get("name")).isPresent()){
            return equipoRepository.findAll()
                    .stream()
                    .filter(equipo -> equipo.getName().equalsIgnoreCase(param.get("name")))
                    .peek(equipo -> equipo.setGoalsCount(totalGolesPorEquipo(equipo)))
                    .collect(Collectors.toList());
        }

        if (Optional.ofNullable(param.get("city")).isPresent()){
            return equipoRepository.findAll()
                    .stream()
                    .filter(equipo -> equipo.getCity().equalsIgnoreCase(param.get("city")))
                    .peek(equipo -> equipo.setGoalsCount(totalGolesPorEquipo(equipo)))
                    .collect(Collectors.toList());
        }

        return equipoRepository.findAll()
                .stream()
                .peek(equipo -> equipo.setGoalsCount(totalGolesPorEquipo(equipo)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Equipo> obtener(Integer equipoId) {
        return equipoRepository.findById(equipoId);
    }

    @Override
    public Equipo registrar(Equipo equipo) throws ValidationException {

        Optional.ofNullable(equipo.getName()).orElseThrow(
                () -> new ValidationException("¡El campo 'name' es requerido!"));

        Optional.ofNullable(equipo.getCity()).orElseThrow(
                () -> new ValidationException("¡El campo 'city' es requerido!"));

        Optional<Equipo> equipoRegistrado = equipoRepository
                .findEquipoUnico(equipo.getName(), equipo.getCity());

        if (equipoRegistrado.isPresent()){
            throw new ValidationException("¡El equipo ya existe!");
        }

        return equipoRepository.save(equipo);
    }

    @Override
    public Equipo actualizar(Equipo equipo) throws ValidationException {

        Optional.ofNullable(equipo.getId()).orElseThrow(
                () -> new ValidationException("¡No ha enviado el Id del Equipo!"));


        Equipo equipoBd = obtener(equipo.getId()).orElseThrow(
                () -> new ValidationException(String.format("¡No existe el equipo con el ID %s!",
                        equipo.getId())));

        Equipo equipoParaActualiza = mergeEquipo(equipo, equipoBd);
        return equipoRepository.save(equipoParaActualiza);
    }

    @Override
    public boolean eliminar(Integer equipoId) {
        try {
            equipoRepository.deleteById(equipoId);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    private Equipo mergeEquipo(Equipo equipoInput, Equipo equipoBd){

        return Equipo.builder()
                .id(equipoBd.getId())
                .name(Optional.ofNullable(equipoInput.getName())
                        .orElse(equipoBd.getName()))
                .city(Optional.ofNullable(equipoInput.getCity())
                        .orElse(equipoBd.getCity()))
                .createdAt(Optional.ofNullable(equipoInput.getCreatedAt())
                        .orElse(equipoBd.getCreatedAt()))
                .updatedAt(new Date())
                .build();
    }

    private Integer totalGolesPorEquipo(Equipo equipo) {
        List<Jugador> jugadorList = jugadorRepository.findJugadorByEquipo(equipo.getId());

        if (jugadorList.isEmpty()) {
            return 0;
        }

        return jugadorList.stream()
                .mapToInt(Jugador::getGoals)
                .sum();
    }
}
