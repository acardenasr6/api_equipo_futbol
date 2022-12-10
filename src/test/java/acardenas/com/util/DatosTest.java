package acardenas.com.util;

import acardenas.com.model.Equipo;

import java.util.Arrays;
import java.util.List;

public class DatosTest {

    public final static List<Equipo> EQUIPO_LIST = Arrays.asList(
            Equipo.builder()
                    .id(1)
                    .name("Barcelona")
                    .goalsCount(2)
                    .city("España")
                    .build(),
            Equipo.builder()
                    .id(2).
                    name("Real Madrid")
                    .goalsCount(9)
                    .city("España")
                    .build());


    public final static Equipo EQUIPO = Equipo.builder()
            .id(1).name("Barcelona")
            .goalsCount(2)
            .city("España")
            .build();

}
