package com.example.alimentos.service;

import com.example.alimentos.model.Alimento;
import com.example.alimentos.repository.AlimentoRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AlimentoService {

    AlimentoRepository repository;

    @Autowired
    public void setRepository(AlimentoRepository repository) {
        this.repository = repository;
    }

    public List<Alimento> findAll(){
        return repository.findAllByDeleteIsNull();
    }

    public void save(Alimento a){
        repository.save(a);
    }

    public void delete(Long id){
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Alimento alimento = repository.getById(id);
        alimento.setDelete(date);
        repository.save(alimento);
    }

    public Alimento findById(Long id){
        return repository.getById(id);
    }



}