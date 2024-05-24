package com.erickcode.desafio4.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
