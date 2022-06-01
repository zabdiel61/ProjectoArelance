/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zabdieldev.arelance.repositories;

import com.zabdieldev.arelance.models.Empleado;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jonat
 */
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
    
    public Optional<Empleado> findByEmail(String email);
    
    public Optional<Empleado> findByUsernameOrEmail(String username, String email);
    
    public Optional<Empleado> findByUsername(String username);
    
    public boolean existsByUsername(String username);
    
    public boolean existByEmail(String email);
}
