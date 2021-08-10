package com.example.alimentos.repository;

import com.example.alimentos.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    List<Alimento> findAllByDeleteIsNull();
}