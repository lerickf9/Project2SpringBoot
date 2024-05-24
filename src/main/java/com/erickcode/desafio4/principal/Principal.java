package com.erickcode.desafio4.principal;

import com.erickcode.desafio4.model.Datos;
import com.erickcode.desafio4.model.DatosLibros;
import com.erickcode.desafio4.service.ConsumoAPI;
import com.erickcode.desafio4.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);

    private static final String URL_BASE = "https://gutendex.com/books/";

    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu()

    {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        //Top 10 libros mas descargados
        System.out.println("Top 10 libros mas descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numerodeDescarga).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda libro por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l-> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            System.out.println("Libro buscado");
            System.out.println(libroBuscado.get());
        }else{
            System.out.println("El libro no esta en nuestras bases de datos");
        }

        //Trabajando con estadistica
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d->d.numerodeDescarga()> 0.0)
                .collect(Collectors.summarizingDouble(DatosLibros::numerodeDescarga));
        System.out.println("Cantidad media descargas: "+ est.getAverage());
        System.out.println("Cantidad maxima de descarga: "+ est.getMax());
        System.out.println("Cantidad minima de descarga: "+ est.getMin());
        System.out.println("Cantidad de registro evaluados para calcular las estadisticas: "+ est.getCount());


    }
}
