/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zabdieldev.arelance.dto;

import java.util.List;

/**
 *
 * @author jonat
 */
public class PublicacionRespuesta {

    private List<PublicacionDTO> contenido;
    private int numeroDePagina;
    private int medidaDePagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;

    public PublicacionRespuesta() {
    }

    public List<PublicacionDTO> getContenido() {
        return contenido;
    }

    public void setContenido(List<PublicacionDTO> contenido) {
        this.contenido = contenido;
    }

    public int getNumeroDePagina() {
        return numeroDePagina;
    }

    public void setNumeroDePagina(int numeroDePagina) {
        this.numeroDePagina = numeroDePagina;
    }

    public int getMedidaDePagina() {
        return medidaDePagina;
    }

    public void setMedidaDePagina(int medidaDePagina) {
        this.medidaDePagina = medidaDePagina;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }

}
