package br.edu.utfpr.pb.pw25s.aula2;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.utfpr.pb.pw25s.aula2.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula2.model.Produto;
import br.edu.utfpr.pb.pw25s.aula2.repository.CategoriaRepository;
import br.edu.utfpr.pb.pw25s.aula2.repository.ProdutoRepository;

@SpringBootApplication
public class Aula2Application implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Aula2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria c1 = new Categoria();
		c1.setNome("Categoria Teste");

		salvarCategoria(c1);

		c1.setNome("Games");
		salvarCategoria(c1);

		Categoria c2 = new Categoria();
		c2.setNome("Eletrônicos");
		salvarCategoria(c2);

		listarCategorias();

		listarCategoriasFindByDescricaoLike();

		// SALVAR PRODUTOS
		Produto p1 = new Produto();
		p1.setNome("Age of Empires");
		p1.setDescricao("Jogo desenvolvido de estratégia desenvolvido...");
		p1.setValor(99.9);
		p1.setCategoria(c1);
		p1.setDataFabricacao(LocalDate.now());
		salvarProduto(p1);

		// SALVAR PRODUTOS
		Produto p2 = new Produto();
		p2.setNome("Fifa 20");
		p2.setDescricao("Jogo de futebol desenvolvido pela EA...");
		p2.setValor(149.9);
		p2.setCategoria(c1);
		p2.setDataFabricacao(LocalDate.now());

		salvarProduto(p2);

		produtoRepository.delete(p2);

		listarProdutosFindByNomeLike();

		listarProdutosFindByNomeContainsOrDescricaoContainsOrderByValorDesc();

		listarProdutosFindByCategoriaIdQueryAndQueryNativa();

		listarProdutosFindByDataFabricacaoBetween();
	}

	private void salvarCategoria(Categoria categoria) {

		this.categoriaRepository.save(categoria);

		System.out.println("Categoria salva com sucesso! \n " + categoria);
	}

	private void listarCategorias() {
		System.out.println("\n *********** LISTA DE CATEGORIAS ***********");
		this.categoriaRepository.findAll().forEach(c -> System.out.println(c));
		System.out.println("\n *********** LISTA DE CATEGORIAS ***********");
	}

	private void listarCategoriasFindByDescricaoLike() {

		System.out.println("\n *********** LISTA DE CATEGORIAS LIKE ***********");
		this.categoriaRepository.findByNomeLike("%G%").forEach(c -> System.out.println(c));
		System.out.println("\n *********** LISTA DE CATEGORIAS LIKE ***********");
	}

	private void salvarProduto(Produto produto) {

		this.produtoRepository.save(produto);

		System.out.println("Produto salvo com sucesso! \n " + produto);
	}

	private void listarProdutosFindByNomeLike() {

		System.out.println("\n *********** LISTA DE Produtos NOME LIKE ***********");

		this.produtoRepository.findByNomeLike("%Age%").forEach(p -> System.out.println(p));

		System.out.println("\n *********** LISTA DE Produtos NOME LIKE ***********");

	}

	private void listarProdutosFindByNomeContainsOrDescricaoContainsOrderByValorDesc() {

		System.out.println("\n *********** LISTA DE Produtos NOME AND DESCRICAO CONTAINS ***********");

		this.produtoRepository.findByNomeContainsOrDescricaoContainsOrderByValorDesc("a", "a")
				.forEach(p -> System.out.println(p));

		System.out.println("\n *********** LISTA DE Produtos NOME AND DESCRICAO CONTAINS ***********");

	}

	private void listarProdutosFindByCategoriaIdQueryAndQueryNativa() {

		System.out.println("\n *********** LISTA DE Produtos findByCategoriaIdQuery ***********");

		this.produtoRepository.findByCategoriaIdQuery(1L).forEach(p -> System.out.println(p));

		System.out.println("\n *********** FIM DA LISTA DE Produtos findByCategoriaIdQuery ***********");



		System.out.println("\n ############### LISTA DE Produtos findByCategoriaIdQuery ###############");

		this.produtoRepository.findByCategoriaIdQueryNativa(1L).forEach(p ->
				System.out.println(p[0] + " - " + p[1] + " - " + p[2])
		);

		System.out.println("\n ############### FIM DA LISTA DE Produtos findByCategoriaIdQuery ###############");
	}

	private void listarProdutosFindByDataFabricacaoBetween() {
		System.out.println("\n *********** LISTA DE Produtos DataFabricacao Between ***********");

		this.produtoRepository.findByDataFabricacaoBetween(
				LocalDate.of(2019, 01, 01), LocalDate.of(2019, 12, 31)
		).forEach(p -> System.out.println(p));

		System.out.println("\n *********** LISTA DE Produtos DataFabricacao Between ***********");
	}
}
