package com.guilhermeTessmann.Cadastro.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.guilhermeTessmann.Cadastro.domain.Veiculo;
import com.guilhermeTessmann.Cadastro.repositories.VeiculoRepository;
import com.guilhermeTessmann.Cadastro.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;


    public Veiculo find(Integer id){ //metodo para encontrar algum veiculo antes de excluir ou atualizar.
        Optional<Veiculo> obj = veiculoRepository.findById(id);
        
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Veículo não encontrado! id= " + id));
    }

    public List<Veiculo> findAll(){
        List<Veiculo> obj = veiculoRepository.findAll();
		return obj;
    }

    public Veiculo insert(Veiculo veiculo){
        veiculo.setId(null);
        return veiculoRepository.save(veiculo);
    }

    public Veiculo update(Veiculo veiculo){
        veiculo.setId(veiculo.getId()); //quando o id é passado como parâmetro, o spring entende que é um update e não insert
        find(veiculo.getId()); //verifica se existe o veiculo;
        return veiculoRepository.save(veiculo);
    }
    
    public void delete(Integer id){
        find(id); //verifica se existe o veiculo;
        veiculoRepository.deleteById(id); //não preciso tratar o erro, pois o mesmo ja está sendo tratado no find.
    }

    public Veiculo updateSomeInformation(Veiculo veiculo){
        veiculo.setId(veiculo.getId()); //quando o id é passado como parâmetro, o spring entende que é um update e não insert
        find(veiculo.getId()); //verifica se existe o veiculo;
        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> findByVeiculo(String veiculo){ 
        return veiculoRepository.findByVeiculo(veiculo);
     }

    
     public List<Object[]> getMarcas(){ 
        return veiculoRepository.getMarcas();
     }
     
     public List<String> getNovosVeiculosSeteDias(){
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH, -7);
        return veiculoRepository.getNovosVeiculosSeteDias(date.getTime());
     }

     public long getQtdVeiculosNaoVendidos(){
        
        return veiculoRepository.getQtdVeiculosNaoVendidos();
     }
     public List<Object[]> getAno(){
  
        return veiculoRepository.getAno();
     }

     

}
