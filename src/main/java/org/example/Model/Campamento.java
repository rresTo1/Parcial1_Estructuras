package org.example.Model;

import java.util.*;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import java.util.Random;


public class Campamento {




    Queue<Paciente> filaPaciente = new LinkedList<>();
    Deque<String> pilaRaciones = new ArrayDeque<>();
    Bag<String> medicamentos = new HashBag<>();
    Random rnd;


    public Campamento(Queue<Paciente> filaPaciente, Random rnd) throws Exception{
        this.filaPaciente = filaPaciente;
        this.pilaRaciones = generarRaciones(rnd);
        this.medicamentos = generarProvisionesMedicas(rnd);
        this.rnd = rnd;
    }


    public static Bag<String> generarProvisionesMedicas(Random rnd) {

        Bag<String> medicamentos = new HashBag<>();

        String[] tipos = {"A", "B", "C"};

        int totalDosis = rnd.nextInt(40) + 20;

        for (int i = 0; i < totalDosis; i++) {

            String medicamento =
                    tipos[rnd.nextInt(tipos.length)];

            medicamentos.add(medicamento);
        }

        return medicamentos;
    }

    public static Deque<String> generarRaciones(Random rnd) {
        Deque<String> Raciones = new ArrayDeque<>();
        String[] racion = {"0", "1"};

        int totalDosis = rnd.nextInt(40) + 20;

        for (int i = 0; i < totalDosis; i++) {
            String letra = racion[rnd.nextInt(racion.length)];
            Raciones.push(letra);
        }

        return Raciones;
    }

    public boolean tieneMedicamentosSuficientes(Paciente paciente) {

        for (String medicamento : paciente.getRecetaMedica().uniqueSet()) {

            int necesita = paciente.getRecetaMedica().getCount(medicamento);

            int disponible = medicamentos.getCount(medicamento);

            if (disponible < necesita) {
                return false;
            }
        }

        return true;
    }

    public void consumirMedicamentos(Paciente paciente) {
        for (String medicamento : paciente.getRecetaMedica().uniqueSet()) {
            int cantidad = paciente.getRecetaMedica().getCount(medicamento);
            medicamentos.remove(medicamento, cantidad);
        }
    }

    public String evaluarSupervivencia(Paciente paciente) {

        String racion = pilaRaciones.peek();

        boolean racionCorrecta = racion.equals(paciente.getPreferenciaRacion());

        boolean medicamentosSuficientes = tieneMedicamentosSuficientes(paciente);

        if (racionCorrecta && medicamentosSuficientes) {

            pilaRaciones.pop();
            consumirMedicamentos(paciente);

            return "SANADO";
        }

        if (!medicamentosSuficientes) {

            paciente.perderIntento();

            if (paciente.getIntentosRestantes() == 0) {
                return "MUERTO";
            }

            return "TRASLADO";
        }

        return "ESPERA";
    }


    public boolean detectarBloqueo() {

        if (pilaRaciones.isEmpty() || filaPaciente.isEmpty()) {
            return false;
        }

        String racionTope = pilaRaciones.peek();

        for (Paciente paciente : filaPaciente) {

            if (paciente.getPreferenciaRacion().equals(racionTope) && tieneMedicamentosSuficientes(paciente)) {

                return false;
            }
        }

        return true;
    }




    public Queue<Paciente> getFilaPaciente() {
        return filaPaciente;
    }

    public Deque<String> getRaciones() {
        return pilaRaciones;
    }

    public Bag<String> getMedicamentos() {
        return medicamentos;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Campamento that)) return false;
        return Objects.equals(filaPaciente, that.filaPaciente) && Objects.equals(pilaRaciones, that.pilaRaciones) && Objects.equals(medicamentos, that.medicamentos) && Objects.equals(rnd, that.rnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filaPaciente, pilaRaciones, medicamentos, rnd);
    }

    @Override
    public String toString() {
        return "Campamento{" +
                "filaPaciente=" + filaPaciente +
                ", pilaRaciones=" + pilaRaciones +
                ", medicamentos=" + medicamentos +
                ", rnd=" + rnd +
                '}';
    }


}
