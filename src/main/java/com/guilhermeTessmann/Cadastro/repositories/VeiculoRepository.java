package com.guilhermeTessmann.Cadastro.repositories;

import java.util.Date;
import java.util.List;


import com.guilhermeTessmann.Cadastro.domain.Veiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo,Integer>{

    List<Veiculo> findByVeiculo(String veiculo);

    @Query("select marca, count(marca) from Veiculo group by marca")
    List<Object[]> getMarcas();

    @Query("select concat(substring(concat(ano,''),0,4),'0') , count(ano) from Veiculo group by(substring(concat(ano,''),0,4)) order by (substring(concat(ano,''),0,4))")
    List<Object[]> getAno();

    @Query("select count(id) from Veiculo where vendido = false")
    long getQtdVeiculosNaoVendidos();
    
    @Query("select veiculo from Veiculo where created >= ?1")
    List<String> getNovosVeiculosSeteDias(Date data);

}
