package acardenas.com.controller;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Equipo;
import acardenas.com.service.EquipoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService){
        this.equipoService = equipoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listar(@RequestParam Map<String, String> param){
        return ResponseEntity
                .ok()
                .body(equipoService.listar(param));
    }

    @GetMapping(path = "/{equipoId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("equipoId") Integer equipoId){
        return ResponseEntity
                .ok()
                .body(equipoService.obtener(equipoId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registrar(@RequestBody Equipo equipo) throws ValidationException {
        return ResponseEntity
                .ok()
                .body(equipoService.registrar(equipo));
    }

    @PutMapping(path = "/{equipoId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@PathVariable("equipoId") Optional<Integer> equipoId,
                                        @RequestBody Equipo equipo) throws ValidationException {
        equipoId.ifPresent(equipo::setId);
        return ResponseEntity
                .ok()
                .body(equipoService.actualizar(equipo));
    }

    @DeleteMapping(path = "/{equipoId}")
    public ResponseEntity<?> eliminar(@PathVariable("equipoId") Integer equipoId){
        return ResponseEntity
                .ok()
                .body(equipoService.eliminar(equipoId));
    }
}
