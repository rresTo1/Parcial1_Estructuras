package java.org.example.Model;

import org.apache.commons.collections4.Bag;
import org.example.Model.Campamento;
import org.example.Model.Paciente;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CampamentoTest {

    @Test
    void testGenerarRacionesYMedicamentosRange() {
        Random rnd = new Random(42);

        Deque<String> raciones = Campamento.generarRaciones(rnd);
        assertTrue(raciones.size() >= 20 && raciones.size() <= 59,
                "Raciones debe tener entre 20 y 59 elementos");

        Bag<String> meds = Campamento.generarProvisionesMedicas(rnd);
        assertTrue(meds.size() >= 20 && meds.size() <= 59,
                "Medicamentos debe tener entre 20 y 59 dosis");
    }

    @Test
    void testEvaluarSupervivenciaSanado() throws Exception {
        Campamento camp = new Campamento(new LinkedList<>(), new Random(1));
        Paciente p = new Paciente("P1", "1", (byte) 3, new Random(2));

        // Forzar ración correcta en tope
        Deque<String> raciones = camp.getRaciones();
        raciones.clear();
        raciones.push("1");

        // Asegurar que el campamento tiene las medicinas necesarias
        Bag<String> receta = p.getRecetaMedica();
        camp.getMedicamentos().clear();
        for (String med : receta.uniqueSet()) {
            int cnt = receta.getCount(med);
            for (int i = 0; i < cnt; i++) camp.getMedicamentos().add(med);
        }

        String resultado = camp.evaluarSupervivencia(p);
        assertEquals("SANADO", resultado);
        assertEquals(0, raciones.size(), "La ración tope debe haberse consumido");
        assertEquals(0, camp.getMedicamentos().size(), "Las medicinas de la receta deben haberse consumido");
    }

    @Test
    void testEvaluarSupervivenciaMuertoWhenNoMedicines() throws Exception {
        Campamento camp = new Campamento(new LinkedList<>(), new Random(3));
        Paciente p = new Paciente("P2", "0", (byte) 1, new Random(4));

        // Sin medicinas disponibles
        camp.getMedicamentos().clear();
        camp.getRaciones().clear();
        camp.getRaciones().push("1"); // ración distinta

        String resultado = camp.evaluarSupervivencia(p);
        assertEquals("MUERTO", resultado);
    }

    @Test
    void testDetectarBloqueo() throws Exception {
        Campamento camp = new Campamento(new LinkedList<>(), new Random(5));
        Paciente p = new Paciente("P3", "1", (byte) 3, new Random(6));

        // Forzar ración tope distinta y sin medicinas
        camp.getMedicamentos().clear();
        camp.getRaciones().clear();
        camp.getRaciones().push("0");

        Queue<Paciente> fila = camp.getFilaPaciente();
        fila.clear();
        fila.add(p);

        assertTrue(camp.detectarBloqueo(), "Debe detectarse bloqueo cuando nadie puede recibir la ración tope");
    }
}
