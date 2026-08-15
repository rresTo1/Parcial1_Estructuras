package org.example.Service;

import org.example.Model.Campamento;
import org.example.Model.Paciente;

import java.util.LinkedList;
import java.util.Queue;

public class SimulacionService {

    private int totalSanados = 0;
    private int totalMuertos = 0;

    public int getTotalSanados() {
        return totalSanados;
    }

    public int getTotalMuertos() {
        return totalMuertos;
    }

    public String procesarCampamento(Campamento campamento, Queue<Paciente> filaActual, Queue<Paciente> filaSiguiente, String nombreCampamento) {

        String mensaje = System.lineSeparator() + "          " + nombreCampamento + System.lineSeparator();

        boolean esUltimoCampamento = filaActual == filaSiguiente;

        while (!filaActual.isEmpty()) {
            Paciente paciente = filaActual.poll();
            String resultado = campamento.evaluarSupervivencia(paciente);

            mensaje += "Paciente " + paciente.getCC() + " | Preferencia: " + paciente.getPreferenciaRacion() + " | Resultado: " + resultado + " | Intentos: " + paciente.getIntentosRestantes() + System.lineSeparator();

            if (resultado.equals("SANADO")) {
                totalSanados++;
                mensaje += "Paciente sanado." + System.lineSeparator();
            } else if (resultado.equals("ESPERA")) {
                filaActual.add(paciente);
                mensaje += " Paciente vuelve al final de la cola." + System.lineSeparator();
            } else if (resultado.equals("TRASLADO")) {
                if (esUltimoCampamento) {
                    filaActual.add(paciente);
                    mensaje += "Último campamento; " + paciente.getCC() + " reintenta en " + nombreCampamento + "." + System.lineSeparator();
                } else {
                    filaSiguiente.add(paciente);
                    mensaje += "Paciente trasladado a " + siguienteNombre(nombreCampamento) + "." + System.lineSeparator();
                }
                mensaje += "Intentos restantes: " + paciente.getIntentosRestantes() + System.lineSeparator();
            } else if (resultado.equals("MUERTO")) {
                totalMuertos++;
                mensaje += "Paciente falleció." + System.lineSeparator();
            }

            if (!filaActual.isEmpty() && campamento.detectarBloqueo()) {
                mensaje += System.lineSeparator() + "!!! BLOQUEO LOGÍSTICO EN " + nombreCampamento + " !!!" + System.lineSeparator();
                mensaje += trasladarPorBloqueo(filaActual, filaSiguiente, esUltimoCampamento, nombreCampamento);

                if (!esUltimoCampamento) {
                    break;
                }
            }
        }
        return mensaje;
    }

    public String trasladarPorBloqueo(
            Queue<Paciente> filaActual,
            Queue<Paciente> filaSiguiente,
            boolean esUltimoCampamento,
            String nombreCampamento) {

        String mensaje = "";
        Queue<Paciente> atrapados = new LinkedList<>(filaActual);
        filaActual.clear();

        while (!atrapados.isEmpty()) {
            Paciente paciente = atrapados.poll();
            paciente.perderIntento();

            if (paciente.getIntentosRestantes() == 0) {
                totalMuertos++;
                mensaje += "Paciente " + paciente.getCC() + " murió por bloqueo." + System.lineSeparator();
            } else if (esUltimoCampamento) {
                filaActual.add(paciente);
                mensaje += "Paciente " + paciente.getCC() + " reintenta en " + nombreCampamento + " por bloqueo. Intentos restantes: " + paciente.getIntentosRestantes() + System.lineSeparator();
            } else {
                filaSiguiente.add(paciente);
                mensaje += "Paciente " + paciente.getCC() + " trasladado por bloqueo a " + siguienteNombre(nombreCampamento) + ". Intentos restantes: " + paciente.getIntentosRestantes() + System.lineSeparator();
            }
        }
        return mensaje;
    }

    private String siguienteNombre(String actual) {
        if (actual.equals("Campamento 1")) return "Campamento 2";
        return "Campamento 3";
    }

    public String mostrarInventario(String titulo, Campamento campamento) {
        return titulo + ":" + System.lineSeparator() + "  Raciones: " + campamento.getRaciones() + System.lineSeparator() + "  Medicamentos: " + campamento.getMedicamentos() + System.lineSeparator();
    }
}
