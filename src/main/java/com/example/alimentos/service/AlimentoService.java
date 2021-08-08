package com.example.alimentos.service;

import com.example.alimentos.model.Alimento;
import com.example.alimentos.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {

    AlimentoRepository repository;

    @Autowired
    public void setRepository(AlimentoRepository repository) {
        this.repository = repository;
    }

    public List<Alimento> findAll(){
        return repository.findAll();
    }

    public void save(Alimento a){
        repository.save(a);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Alimento findById(Long id){
        return repository.getById(id);
    }
}