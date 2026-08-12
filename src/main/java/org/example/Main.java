package org.example;


import java.util.*;

import java.util.LinkedList;
import java.util.Queue;

import java.util.ArrayDeque;
import java.util.Deque;


public class Main {
    static void main(String[] args) {

        try {


            Deque<String> pilaRaciones = new ArrayDeque<>();

            Queue<String> filaRefugiados = new LinkedList<>();

            pilaRaciones.push("1");
            pilaRaciones.push("0");
            pilaRaciones.push("1");
            pilaRaciones.push("0");

            filaRefugiados.add("1");
            filaRefugiados.add("1");
            filaRefugiados.add("0");
            filaRefugiados.add("0");

            String bloqueo = detectarBloqueo(pilaRaciones,filaRefugiados );
            System.out.println(bloqueo);


        } catch(Exception e){
            System.out.println("Error:" + e);
        }


    }

    public static String detectarBloqueo(Deque<String> pilaRaciones, Queue<String> filaRefugiados) throws Exception{

        try {

            byte contadorComen = 0;
            byte contadorNoComen = 0;
            String Mensaje = null;
            byte valor = 2;


            while (!filaRefugiados.isEmpty() && !pilaRaciones.isEmpty()){

                String refugiado = filaRefugiados.poll();

                String racion = pilaRaciones.pop();

                if (Objects.equals(refugiado, racion)) {

                    contadorComen++;

                } else {

                    contadorNoComen++;

                    filaRefugiados.add(refugiado);

                    pilaRaciones.push(racion);

                    if (filaRefugiados.size()+valor < contadorNoComen ){
                        break;
                    }

                }

            }



            Mensaje = contadorComen + " Refugiados comieron - " + filaRefugiados.size() + " Refugiados no logran comer";

            return Mensaje;

        } catch (Exception e) {
            throw new Exception("Error" + e);
        }

    }

}
