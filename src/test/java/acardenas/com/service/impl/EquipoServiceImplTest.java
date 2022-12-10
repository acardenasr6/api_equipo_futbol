package acardenas.com.service.impl;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.Equipo;
import acardenas.com.repository.EquipoRepository;
import acardenas.com.repository.JugadorRepository;
import acardenas.com.util.DatosTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipoServiceImplTest {

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private JugadorRepository jugadorRepository;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    @Test
    void listar() {
        when(jugadorRepository.findJugadorByEquipo(anyInt())).thenReturn(new ArrayList<>());
        when(equipoRepository.findAll()).thenReturn(DatosTest.EQUIPO_LIST);
        List<Equipo> equipoList =equipoService.listar(new HashMap<>());
        assert(!equipoList.isEmpty());
        verify(equipoRepository).findAll();
    }

    @Test
    void obtener() {
        Integer equipoId = DatosTest.EQUIPO.getId();
        when(equipoRepository.findById(anyInt())).thenReturn(Optional.of(DatosTest.EQUIPO));
        Optional<Equipo> equipo = equipoService.obtener(equipoId);
        assertNotNull(equipo);
        assert(equipo.isPresent());
        assertEquals(equipoId, equipo.get().getId());
        verify(equipoRepository).findById(anyInt());
    }

    @Test
    void registrar() throws ValidationException {
        when(equipoRepository.findEquipoByUnico(anyString(), anyString())).thenReturn(Optional.empty());
        when(equipoRepository.save(any(Equipo.class))).thenReturn(DatosTest.EQUIPO);
        Equipo equipo = equipoService.registrar(DatosTest.EQUIPO);
        assertNotNull(equipo);
        verify(equipoRepository).save(any(Equipo.class));
    }

    @Test
    void actualizar() throws ValidationException {
        when(equipoRepository.findById(anyInt())).thenReturn(Optional.of(DatosTest.EQUIPO));
        when(equipoRepository.save(any(Equipo.class))).thenReturn(DatosTest.EQUIPO);
        Equipo equipo = equipoService.actualizar(DatosTest.EQUIPO);
        assertNotNull(equipo);
        assertEquals(DatosTest.EQUIPO.getId(), equipo.getId());
        verify(equipoRepository).findById(anyInt());
        verify(equipoRepository).save(any(Equipo.class));
    }

    @Test
    void eliminar() {
        boolean esEliminado = equipoService.eliminar(DatosTest.EQUIPO.getId());
        assert(esEliminado);
        verify(equipoRepository).deleteById(anyInt());
    }
}