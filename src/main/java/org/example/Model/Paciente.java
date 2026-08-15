package org.example.Model;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

import java.util.Objects;
import java.util.Random;

public class Paciente {



    String CC;
    String preferenciaRacion;
    Bag<String> recetaMedica = new HashBag<>();
    byte intentosRestantes;
    Random rnd;

    public Paciente(String CC, String preferenciaRacion, byte intentosRestantes, Random rnd) throws Exception {
        this.CC = CC;

        this.preferenciaRacion = preferenciaRacion;

        this.recetaMedica = generarRecetaMedica(rnd);

        this.intentosRestantes = intentosRestantes;

        if (!Objects.equals(preferenciaRacion, "0") && !Objects.equals(preferenciaRacion, "1")){

            throw new Exception("Este no es un valor permitido, debe ser un 0 o un 1");

        }

    }

    public static Bag<String> generarRecetaMedica(Random rnd) {
        Bag<String> receta = new HashBag<>();
        String[] medicamentos = {"A", "B", "C"};

        int totalDosis = rnd.nextInt(11) + 5;

        for (int i = 0; i < totalDosis; i++) {
            String letra = medicamentos[rnd.nextInt(medicamentos.length)];
            receta.add(letra);
        }

        return receta;
    }


    public byte getIntentosRestantes() {
        return intentosRestantes;
    }

    public void perderIntento() {
        intentosRestantes--;
    }

    public String getPreferenciaRacion() {
        return preferenciaRacion;
    }

    public Bag<String> getRecetaMedica() {
        return recetaMedica;
    }

    public String getCC() {
        return CC;
    }



    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Paciente paciente)) return false;
        return intentosRestantes == paciente.intentosRestantes && Objects.equals(CC, paciente.CC) && Objects.equals(preferenciaRacion, paciente.preferenciaRacion) && Objects.equals(recetaMedica, paciente.recetaMedica) && Objects.equals(rnd, paciente.rnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CC, preferenciaRacion, recetaMedica, intentosRestantes, rnd);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "CC='" + CC + '\'' +
                ", preferenciaRacion='" + preferenciaRacion + '\'' +
                ", recetaMedica=" + recetaMedica +
                ", intentosRestantes=" + intentosRestantes +
                ", rnd=" + rnd +
                '}';
    }
}
