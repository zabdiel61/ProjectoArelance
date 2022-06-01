/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zabdieldev.arelance.repositories;

import com.zabdieldev.arelance.models.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jonat
 */
public interface ComentarioRepository extends JpaRepository<Comentario, Long >{
    	public List<Comentario> findByPublicacionId(long publicacionId); 
}
