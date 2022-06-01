/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zabdieldev.arelance.services;

import com.zabdieldev.arelance.dto.*;
import com.zabdieldev.arelance.exceptions.ResourceNotFoundException;

import com.zabdieldev.arelance.models.Publicacion;
import com.zabdieldev.arelance.repositories.PublicacionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author jonat
 */
@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);

        PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);

        return publicacionRespuesta;
    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor,
            String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        // pageable
        PageRequest pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);

        List<Publicacion> listaDePublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido = listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion))
                .collect(Collectors.toList());

        PublicacionRespuesta publicacionResponse = new PublicacionRespuesta();
        publicacionResponse.setContenido(contenido);
        publicacionResponse.setNumeroDePagina(publicaciones.getNumber());
        publicacionResponse.setMedidaDePagina(publicaciones.getSize());
        publicacionResponse.setTotalElementos(publicaciones.getTotalElements());
        publicacionResponse.setTotalPaginas(publicaciones.getTotalPages());
        publicacionResponse.setUltima(publicaciones.isLast());

        return publicacionResponse;
    }

    // //metodo para convertir a DTO
    // private PublicacionDTO mapearDTO(Publicacion publicacion) {
    // PublicacionDTO publicacionDTO = new PublicacionDTO();
    //
    // publicacionDTO.setId(publicacion.getId());
    // publicacionDTO.setTitulo(publicacion.getTitulo());
    // publicacionDTO.setDescripcion(publicacion.getDescripcion());
    // publicacionDTO.setContenido(publicacion.getContenido());
    //
    // return publicacionDTO;
    // }
    //
    // //metodo para CONVERTIR entidad a DTO
    // private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
    // Publicacion publicacion = new Publicacion();
    //
    // publicacion.setTitulo(publicacionDTO.getTitulo());
    // publicacion.setDescripcion(publicacionDTO.getDescripcion());
    // publicacion.setContenido(publicacionDTO.getContenido());
    //
    // return publicacion;
    // }
    @Override
    public PublicacionDTO obtenerPublicacionPorId(long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);

        return mapearDTO(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacion(long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        publicacionRepository.delete(publicacion);
    }

    private PublicacionDTO mapearDTO(Publicacion publicacion) {
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
        return publicacionDTO;
    }

    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
        return publicacion;
    }

}
