package kruger.msvc.cursos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kruger.msvc.cursos.entity.Curso;
import kruger.msvc.cursos.services.CursoService;


@RestController
@RequestMapping("/kruger/")
public class CursoController {
	
	@Autowired
	private CursoService service;
	
	@GetMapping
	public ResponseEntity<List<Curso>> listar(){
		return ResponseEntity.ok(service.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detalle(@PathVariable Long id){
		Optional<Curso> o =	service.porId(id);
		if(o.isPresent()) {
		return ResponseEntity.ok(o.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Autowired
	private DiscoveryClient discoveryClient;
	@GetMapping("/uri")
	public String serviceUrl() {
		List<ServiceInstance> list = discoveryClient.getInstances("STORES");
		if (list != null && list.size() > 0 ) {
			return list.get(0).getUri().toString();
		}
		return null;
	}
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Curso curso){
		Curso cursoDb= service.guardar(curso);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);	}	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id){
		Optional<Curso> o= service.porId(id);
		if(o.isPresent()) {
			Curso cursoDb=o.get();
			cursoDb.setNombre(curso.getNombre());
			return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
			
		}
		return ResponseEntity.notFound().build();
		
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		Optional<Curso> o= service.porId(id);
		if(o.isPresent()) {
			service.eliminar(o.get().getId());
			return ResponseEntity.noContent().build();
			
		}
		return ResponseEntity.notFound().build();
		
	}
	
	
	
}
