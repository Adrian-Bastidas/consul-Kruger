package kruger.msvc.cursos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kruger.msvc.cursos.entity.Curso;
import kruger.msvc.cursos.repositories.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {
	
	@Autowired
	private CursoRepository repository;

	@Override
	@Transactional(readOnly=true)
	public List<Curso> listar() {
		return (List<Curso>) repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Curso> porId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Curso guardar(Curso curso) {
		return repository.save(curso);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
repository.deleteById(id);
	}

}
