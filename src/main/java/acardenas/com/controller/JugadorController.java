package acardenas.com.controller;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Jugador;
import acardenas.com.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    @Autowired
    public JugadorController(JugadorService jugadorService){
        this.jugadorService = jugadorService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listar(@RequestParam Map<String, String> param){
        return ResponseEntity
                .ok()
                .body(jugadorService.listar(param));
    }

    @GetMapping(path = "/{jugadorId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("jugadorId") Integer jugadorId){
        return ResponseEntity
                .ok()
                .body(jugadorService.obtener(jugadorId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registrar(@RequestBody Jugador jugador) throws ValidationException {
        return ResponseEntity
                .ok()
                .body(jugadorService.registrar(jugador));
    }

    @PutMapping(path = "/{jugadorId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@PathVariable("jugadorId") Optional<Integer> jugadorId,
                                        @RequestBody Jugador jugador) throws ValidationException {
        jugadorId.ifPresent(jugador::setId);
        return ResponseEntity
                .ok()
                .body(jugadorService.actualizar(jugador));
    }

    @DeleteMapping(path = "/{jugadorId}")
    public ResponseEntity<?> eliminar(@PathVariable("jugadorId") Integer jugadorId){
        return ResponseEntity
                .ok()
                .body(jugadorService.eliminar(jugadorId));
    }
}
