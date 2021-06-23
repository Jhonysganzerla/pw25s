package br.edu.utfpr.pb.pw25s.aula5.service.impl;

import br.edu.utfpr.pb.pw25s.aula5.model.Marca;
import br.edu.utfpr.pb.pw25s.aula5.repository.MarcaRepository;
import br.edu.utfpr.pb.pw25s.aula5.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MarcaServiceImpl extends CrudServiceImpl<Marca, Long>  implements MarcaService {

	@Autowired
	private MarcaRepository marcaRepository;
	
	@Override
	protected JpaRepository<Marca, Long> getRepository() {
		return marcaRepository;
	}

}
