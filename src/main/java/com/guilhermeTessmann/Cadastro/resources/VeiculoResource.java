package com.guilhermeTessmann.Cadastro.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;
import com.guilhermeTessmann.Cadastro.domain.Veiculo;
import com.guilhermeTessmann.Cadastro.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value="/veiculos")
public class VeiculoResource {

    @Autowired
	private VeiculoService service;

	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<Veiculo> find(@PathVariable Integer id) {
		Veiculo obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@CrossOrigin
	@GetMapping()
	public ResponseEntity<List<Veiculo>> findAll() {
		List<Veiculo> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
    @PostMapping()
	public ResponseEntity<Void> insert(@RequestBody Veiculo veiculo) {// @requestBody converte o jSon em objeto java
		veiculo.setCreated(new Date());
		veiculo = service.insert(veiculo); //preciso retornar a nova URI com o novo veiculo inserido (boa prática).
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(veiculo.getId()).toUri(); //pega a URI que foi inserida e acrescenta o id do novo veiculo inserido
		return ResponseEntity.created(uri).build();
	}

	@CrossOrigin
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody Veiculo veiculo, @PathVariable Integer id) { 
		veiculo.setId(id); //aqui eu informo que ele será uma atualização.
		veiculo.setUpdated(new Date());
		veiculo.setCreated(service.find(id).getCreated()); //Quando atualizo a data de criação deve ficar intacta
		veiculo = service.update(veiculo);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Veiculo> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	@CrossOrigin
	@GetMapping(value = "/marcas")
	public List<Object[]> getMarcas() {
		return service.getMarcas();
	}

	@CrossOrigin
	@GetMapping(value = "/novosVeiculos")
	public List<String> getNovosVeiculosSeteDias() {
		return service.getNovosVeiculosSeteDias();
	}
	@CrossOrigin
	@GetMapping(value = "/naoVendidos")
	public long getQtdVeiculosNaoVendidos() {
		return service.getQtdVeiculosNaoVendidos();
	}

	@CrossOrigin
	@GetMapping(value = "/decada")
	public List<Object[]> getAno() {
		return service.getAno();
	}
	
	@CrossOrigin
	@GetMapping(value = "/find/{q}")
	public ResponseEntity<List<Veiculo>> findByVeiculo(@PathVariable String q) {
		List<Veiculo> list = service.findByVeiculo(q);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdateName(@RequestBody Veiculo veiculo, @PathVariable("id") Integer id) {
  	Veiculo veiculoBanco = service.find(id);
	veiculo.setId(id);
	if(veiculo.getVeiculo() == null){
		veiculo.setVeiculo(veiculoBanco.getVeiculo());
	}
	if(veiculo.getMarca() == null){
		veiculo.setMarca(veiculoBanco.getMarca());
	}
	if(veiculo.getAno() == null){
		veiculo.setAno(veiculoBanco.getAno());
	}
	if(veiculo.getDescricao() == null){
		veiculo.setDescricao(veiculoBanco.getDescricao());
	}

	veiculo.setUpdated(new Date()); //pega a hora da atualização
	veiculo.setCreated(service.find(id).getCreated()); //Quando atualizo a data de criação deve ficar intacta
	
	service.update(veiculo);

    return ResponseEntity.noContent().build();
}

	

}
