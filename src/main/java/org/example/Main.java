package org.example;

import org.example.Model.Campamento;
import org.example.Model.Paciente;
import org.example.Service.SimulacionService;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        try {
            Random rnd = new Random();
            SimulacionService servicio = new SimulacionService();

            Queue<Paciente> filaCampamento1 = new LinkedList<>();
            Queue<Paciente> filaCampamento2 = new LinkedList<>();
            Queue<Paciente> filaCampamento3 = new LinkedList<>();

            Campamento campamento1 = new Campamento(filaCampamento1, rnd);
            Campamento campamento2 = new Campamento(filaCampamento2, rnd);
            Campamento campamento3 = new Campamento(filaCampamento3, rnd);

            int cantidadPacientes = rnd.nextInt(11) + 20;

            for (int i = 0; i < cantidadPacientes; i++) {
                String CC = "P" + (i + 1);
                String preferenciaRacion = String.valueOf(rnd.nextInt(2));
                Paciente paciente = new Paciente(CC, preferenciaRacion, (byte) 3, rnd);
                filaCampamento1.add(paciente);
            }


            System.out.println("SAHR - FASE 2");
            System.out.println("Pacientes iniciales: " + cantidadPacientes);
            System.out.println();

            System.out.println(servicio.mostrarInventario("Campamento 1 (inicial)", campamento1));
            System.out.println(servicio.mostrarInventario("Campamento 2 (inicial)", campamento2));
            System.out.println(servicio.mostrarInventario("Campamento 3 (inicial)", campamento3));

            System.out.print(servicio.procesarCampamento(campamento1, filaCampamento1, filaCampamento2, "Campamento 1"));
            System.out.print(servicio.procesarCampamento(campamento2, filaCampamento2, filaCampamento3, "Campamento 2"));
            System.out.print(servicio.procesarCampamento(campamento3, filaCampamento3, filaCampamento3, "Campamento 3"));

            System.out.println();
            System.out.println("ESTADO FINAL");
            System.out.println("Pacientes sanados: " + servicio.getTotalSanados());
            System.out.println("Pacientes muertos: " + servicio.getTotalMuertos());
            System.out.println("Total procesado: " + (servicio.getTotalSanados() + servicio.getTotalMuertos()) + " / " + cantidadPacientes);
            System.out.println();

            System.out.println(servicio.mostrarInventario("Campamento 1 (final)", campamento1));
            System.out.println(servicio.mostrarInventario("Campamento 2 (final)", campamento2));
            System.out.println(servicio.mostrarInventario("Campamento 3 (final)", campamento3));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
