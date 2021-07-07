## 1. Introdução
Neste projeto será discutido **como utilizar Thymeleaf com Spring**  juntamento com um caso de uso na camada de visão (*view*) de uma aplicação Spring Boot [1], Spring Web MVC [2], Spring Data JPA [3] e Thymeleaf [4].

### 1.1 Dependências do projeto.
O projeto será do tipo **Maven Project**.
A linguagem será **Java**.
A versão do Spring Boot será a versão estável atual na data de criação do projeto (**2.2.5**).
Os metadados do projeto são:
- Group: **br.edu.utfpr.pb.pw25s**
- Artifact: **aula3**
- Options:
    - Packaging: **Jar**
    - Java: **11**.

Em dependências devem ser selecionados os *frameworks*:
- Spring Web
- Spring Data JPA
- Spring Devtools
- PostgreSQL Driver (ou o driver do banco de sua preferência H2, MySQL, etc...)
- Lombok

### 1.2 Instruções para executar o projeto.
Como o projeto foi desenvolvido com Maven e Spring Boot o processo para executar o processo é bem simples. Inicialmente é necessário importar o projeto na IDE. O próximo passo será atualizar as dependências. No passo seguinte deve ser configurada a conexão com o banco de dados, por padrão foi criada uma conexão com o `PostgreSql`. O nome do banco de dados é `pw25s-aula3`, então basta criar o banco no Sistema Gerenciador de Banco de Dados (SGBD) e configurar o usuário e senha do banco de dados no arquivo `aplication.properties` que fica dentro de `src/main/resources`.

## 2. Referencial Teórico

### 2.1 A arquitetura MVC

O projeto Spring MVC é o framework da família Spring que auxilia no  desenvolvimento de aplicações web. O padrão de projeto *Model, View e Controller* (MVC) é muito utilizado no desenvolvimento de aplicações web, e entender bem a responsabilidade de cada parte do MVC é importante para o desenvolvimento uma aplicação bem escrita e de fácil manutenibilidade.
A camada **modelo** (*Model*) está relacionada à toda a parte do sistema que será responsável pela lógica de negócio e seus componentes auxiliares, como persistência de dados, cache, integração com  outros sistemas, entre outros.  
A camada de **visualização** (*View*), é a parte visual da aplicação, que é apresentada ao usuário final. Ao visualizar uma página HTML, por exemplo, o usuário está acessando o resultado desta camada.
Por fim, a camada **controladora** (*Controller*) é responsável por orquestrar a interação entre as camadas de modelo e visualização. Quando o usuário preenche um formulário em uma página web, por exemplo, ele está interagindo com a camada de visualização, entretanto quando ele clica no botão enviar, é realizada uma chamada para o servidor e o controlador é acionado. Este transforma os parâmetros inseridos pelo usuário para um formato que seja compatível com a interface disponibilizada pela camada de modelo. Então, os dados são processados e o resultado do processamento é recebido novamente pelo controlador, que o modifica caso necessário e em seguida o envia à camada de visualização novamente, para que o resultado possa ser visualizado pelo usuário final.

### 2.2 Spring MVC

A estrutura do Spring Web MVC é projetada em torno de um **DispatcherServlet** que envia solicitações para um mapeador de requisições (*Handler Mapping*). O mapeador padrão é baseado nas anotações @Controller e @RequestMapping, oferecendo uma ampla variedade de métodos de manipulação flexíveis. Com a introdução do Spring 3.0, o mecanismo @Controller também permite criar aplicacões RESTful, por meio da anotação @PathVariable e outros recursos [6].

![Requisição HTTP](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/images/mvc.png)

Como citado no parágrafo anterior o DispatcherServlet é o componente responsável por orquestrar o funcionamento do Spring MVC. O qual é uma implementação do padrão *Front Controller* que é muito usado na escrita de *frameworks* voltados para o desenvolvimento de aplicações web. O objetivo deste padrão de projeto é fornecer um ponto de entrada central  
para todas as requisições direcionadas à aplicação. É uma responsabilidade do DispatcherServlet interpretar e decidir qual o componente responsável pelo processamento de cada requisição e  seu eventual retorno para o usuário, conforme pode ser observado nas figuras a seguir [6].

![DispacherServlet](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/images/mvc-contexts.gif)

### 2.3 Thymeleaf
O [Thymeleaf](http://www.thymeleaf.org/)  é um motor de template (*template engine*) que atua no lado servidor (*server-side*) para Java para processamento e criação de HTML, XML, JavaScript, CSS, e text [4].
Essa biblioteca é extensível e sua capacidade de modelagem garante que os modelos possam ser prototipados sem um *back-end* o que torna o desenvolvimento muito rápido quando comparado com outros motores de template, como o Java Server Pages (JSP) [4].
Em uma aplicação web, a camada de visão retorna para o navegador do cliente apenas HTML (css e javascript, também)


## 3. Integrando Thymeleaf com Spring

Inicialmente é necessário configurar o projeto com as dependências necessárias no arquivo **pom.xml** do Maven.
``` xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
Como os projetos Spring Boot possuem as configurações básicas com o Thymeleaf já pré-configuradas, nenhuma outra ação é necessária para o funcionamento. Agora já é possível criar as páginas que serão apresentadas na camada de visão da aplicação.


## 4. Criando o primeiro Controller e View

Neste projeto está sendo utilizado o padrão *Model View Controller* (MVC). Na camada *model* será utilizado o *framework* Spring JPA, na camada *view* o Thymeleaf e na camada *controller* o *framework* Spring MVC.

### 4.1 Controller
No primeiro passo será criado uma nova classe chamada 'IndexController', nessa classe serão implementados os métodos responsáveis por fazer a chamada da página inicial da aplicação. Na classe criada será adicionada a anotação `@Controller`, que indica que essa classe é um componente do Spring Web MVC e que é um *controller*. O próximo passo é definir o método que retornará o nome da *view* que será exibida ao usuário, nesse caso o método `index()` que retorna o texto `index`, que será o nome do arquivo que será renderizado para o usuário.

Agora é necessário defirnir qual *Uniform Resource Locator* (URL) o usuário irá digitar para acessar a página inicial, para isso foi utilizada a anotação `@GetMapping`, como a anotação aparece sózinha sem nenhum parâmetro significa que esse método responderá requisições HTTP Get na raiz da aplicação, ou seja, no caso deste projeto será exibida a página inicial quando o usuário digitar `http://localhost:8025`.

Nessa mesma classe também foi criado um segundo método chamado `teste()`, nesse método também é possível visualizal a anotação `@GetMapping`, porém agora temos o parâmetro `value` que recebe dois valores **teste** e **teste2**, isso siginifica que caso o usuário digite na barra de enredeços no navegador `http://localhost:8025/teste` ou `http://localhost:8025/teste2` ele irá acessar a mesma página do méto `index()`, pois o retorno também foi o nome do arquivo da camada *view* `index`.
``` Java
package br.edu.utfpr.pb.aula3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping
	public String index() {
		return "index";
	}
	
	@GetMapping(value = {"teste", "teste2"})
	public String teste() {
		return "index";
	}
	
}
```

### 4.2 View
Nesse passo será criado o arquivo da camada *view*. A configuração padrão do Spring Boot com o  Thymeleaf, define que os arquivos da camada *view* devem ficar em `src/main/resources/templates`, e o sufixo dos arquivos devem ser **.html**.

O arquivo `index.html` foi criado na raiz da pasta `templates`. O conteúdo do arquivo é exibido abaixo. Alguns trechos desse HTML devem ser destacados, na *tag* HTML `<html>` o atributo `xmlns:th` define que será possível utilizar as propriedades definidas pelo Thymeleaf no código da página HTML. Além disso, na *tag* `<head>` estão as declarações dos arquivos *Cascading Style Sheets* (CSS) utilizados para estilizar a página.

Ainda no arquivo `index.html` é possível visualizar alguns atalhos para as próximas *views* da aplicação que está sendo desenvolvida. A  *tag* `<a />`, que define um *hyperlink* nesse caso utiliza o atributo `th:href` do Thymeleaf. Utilizando esse atributo é possível utilizar a expressão `@{}`, que coloca o contexto da aplicação nos *hyperlinks* do projeto, ou seja, independente da URL da aplicação em que o *hyperlink* for inserido o endereço sempre vai partir do contexto da aplicação.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Index</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
	integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
	crossorigin="anonymous">
<link rel="stylesheet" 
	href="https://unpkg.com/shards-ui@latest/dist/css/shards.min.css">
</head>
<body>
	<div class="container my-5">
		<h3>Menu</h3>
		<ul class="nav nav-pills nav-fill">
			<li class="nav-item">
				<a class="btn btn-primary" th:href="@{/categoria}"> 
					<i class="fas fa-table"> Lista de Categorias</i>
				</a>
			</li>
			<li class="nav-item">
				<a class="btn btn-primary" th:href="@{/marca}">
					<i class="fas fa-table"> Lista de Marcas</i>
				</a>
			</li>
			<li class="nav-item">
				<a class="btn btn-primary" th:href="@{/produto}">
					<i class="fas fa-table"> Lista de Produtos</i>
				</a>
			</li>
		</ul>
	</div>
</body>
</html>
```
Com a página `index.html` criada a mesma já pode ser acessada após executar a aplicação. Executando a aplicação basta abrir o navegador e acessar o endereço `http://localhost:8026`.

## 5. Criando uma interação entre o Controller, Model e View

Nos próximos exemplos serão realizadas operações de *Create, Read, Update and Delete* (CRUD) no SGBD. O projeto contém três *models* mapeados com anotações da *Java Persistence Api* (JPA) que são Categoria, Marca e Produto. No primeiro exemplo será apresentada uma lista de todos os **categorias** cadastrados no banco de dados, para isso serão criados dois arquivos, o `CategoriaController` no pacote `controller` da aplicação e o arquivo `list.html` `src/main/resources/templates/categoria`. Já no segundo exemplo será criado um formulário para criar e manter **marcass** e, para isso serão realizadas modificações no arquivo `CategoriaController` e será criado o arquivo `form.html` dentro da pasta `src/main/resources/templates/categoria`.

### 5.1 Criando a lista de categorias
Inicialmente será criado o arquivo `CategoriaController`* e, assim como no *controller* do *index* será mapeado com a anotação `@Controller`.

#### 5.1.1 CategoriaController
Esse *controller* também possuí a anotação `@RequestMapping("categoria")` que indica que todos os seus métodos irão receber requisições HTTP a partir de `/categoria`. A dependência `CategoriaService` é injetada com a anotação `@Autowired`, por é por meio desse *service* que serão realizadas as operações com o banco de dados.

No método `list()` será realizado o processo de consultar os dados na base e fazer a chamada para página da camada *view*. A anotação `@GetMapping` indica que esse método irá receber requisições HTTP Get de `/categoria`. O método recebe um `Model` que é uma interface que será responsável por adicionar os atributos necessários para serem exibidos no HTML. O código `model.addAttribute("categorias", categoriaService.findAll());` adiciona uma lista de categorias no model chamada `categorias`, essa lista será utilizada no arquivo `list.html` para apresentação dos dados. Por fim, o retorno do método é a página que será exibidos os dados, ou seja, o arquivo `list.html` dentro da pasta `categoria`.

###### * Para melhorar a visualização do código fonte o pacote e as importações necessárias não serão apresentada em conjunto com o código fonte, mas podem ser visualizadas no arquivo original.

``` Java
@Controller
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("categorias", categoriaService.findAll());
		return "categoria/list";
	}

}
```
#### 5.1.2 View `/categoria/list`

O arquivo `list.html` inicia com o conteúdo semelhante ao `index.html` com o namespace do Thymeleaf e a adição das referências aos arquivos CSS. Depois é apresentado um *hyperlink* para retornar ao `index`. É utilizada então uma *tag* `<table />` no cabeçalho (`<thead />`) é exibido o texto estático que será apresentado na primeira linha da tabela.  Em seguida vem o corpo da tabela com a *tag* (`<tbody />`), nele serão exibidos os dados dos **categorias**. Na *tag* `<tr  th:each="categoria: ${categorias}">` visualiza-se o atributo do Thymeleaf `th:each` que é utilizado para percorrer uma lista, nesse caso a lista `categorias` que foi adicionado no model na classe `CategoriaController`, na linha de código `model.addAttribute("categorias", categoriaService.findAll());`, e `categoria:` significa que cada **gênero** da lista será um objeto `categoria` que poderá ser utilizado pelos atributos do Thymeleaf.

Para exibir os dados na tela são utilizadas as colunas da tabela `<td />` que nesse caso são `<td th:text="${categoria.id}">código</td>` e `<td th:text="${categoria.nome}">nome</td>`. Note o uso do atributo `th:text` que apresentará no texto de cada coluna (`<td> AQUI </td>`) os valores dos atributos `id` e `nome`  do objeto `categoria`, respectivamente.

Para testar, basta executar a aplicação e clicar no *hyperlink* para a tela de **categorias** na página inicial, ou digitar no navegador `http://localhost:8025/categoria`.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Lista de Gêneros</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
        <link rel="stylesheet" href="https://unpkg.com/shards-ui@latest/dist/css/shards.min.css">
    </head>
    <body>
        <div class="container my-5">
    		<h1>Lista de Gêneros</h1>
    		<a class="btn btn-primary" th:href="@{/}"><i class="fas fa-home"></i>
        	</a>
        	
    		<table class="table table-striped table-responsive-md">
    			<thead>
    				<tr>
    					<th>Código</th>
    					<th>Nome</th>
    					<th>Editar</th>
    					<th>Remover</th>
    				</tr>
    			</thead>
    			<tbody>
    				<tr th:each="categoria: ${categorias}">
    					<td th:text="${categoria.id}">código</td>
    					<td th:text="${categoria.nome}">nome</td>
						<!-- EDITAR -->
						<td> ---
						</td>
						<!-- REMOVER -->
						<td> ---
						</td>
    				</tr>
    			</tbody>
    		</table> <!-- FIM TABLE -->
        </div>
    </body>
</html>
```

### 5.2 Criando o form de categorias e o método de remoção de categorias

#### 5.2.1 CategoriaController
Nessa etapa será utilizado o mesmo arquivo `CategoriaController` criado na seção 4.1. O método `list()` foi mantido e foram adicionados três novos métodos: um método `save()` e dois métodos `form()` com assinaturas diferentes, um para ser utilizado na criação de novos registros e outro na edição.

O método `form` para novos registros recebe uma requisição HTTP Get em `/categoria/new`, `/categoria/novo` ou `/categoria/form`. Adiciona um novo gênero no `Model` e retorna para página `form.html` dentro de `src/main/resources/templates/categoria`.

O método `form` para edição de registros recebe uma requisição HTTP Get em `/categoria/{id}`. Nesse caso `{id}` seré o código do gênero que será editado pelo usuário e será recuperado por meio do parâmetro do método `@PathVariable Long id`. O `Model` também é parâmetro do método, pois é por meio dele que será adicionado o gênero que será editado na *view* `form.html` (o arquivo para novo e editar é o mesmo, a diferença está no fato de que no primeito método form é passado um novo gênero para o *view* e, no segundo um gênero existente no banco de dados).

O método `save()` que recebe uma requisição HTTP Post em `/categoria` e têm como parâmetros um objeto da classe `Categoria` que será passado pelo forumário presente no aquivo `form.html`. Um objeto da classe `BindingResult ` que é utilizado na validação do objeto. Um objeto da classe `Model` que é utilizado para retornar o `categoria` para o formulário em caso de erro.  Por fim um objeto da classe `RedirectAttributes ` que é utilizado para adicionar atributos que serão exibidos na página `index.html` após salvar um gênero.

###### * Para melhorar a visualização do código fonte o pacote e as importações necessárias não serão apresentada em conjunto com o código fonte, mas podem ser visualizadas no arquivo original.
``` Java
@Controller
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("categorias", categoriaService.findAll());
		return "categoria/list";
	}

	@GetMapping(value = {"new", "novo", "form"})
	public String form(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "categoria/form";
	}

	@PostMapping
	public String save(@Valid Categoria categoria, BindingResult result,
					   Model model, RedirectAttributes attributes) {
		if ( result.hasErrors() ) {
			model.addAttribute("categoria", categoria);
			return "categoria/form";
		}	
		categoriaService.save(categoria);
		attributes.addFlashAttribute("sucesso", "Registro salvo com sucesso!");
		return "redirect:/categoria";
	}

	@GetMapping("{id}") // /categoria/25
 	public String form(@PathVariable Long id, Model model) {
		model.addAttribute("categoria", categoriaService.findOne(id));
		return "categoria/form";
	}

}
```
#### 5.2.2 View `/categoria/form` e atualizações no `/categoria/list`

O arquivo `form.html` também inicia com o conteúdo semelhante ao `index.html` com o namespace do Thymeleaf e a adição das referências aos arquivos CSS. Nos próximos projetos serão criadas páginas padrão para evitar a repetição de código.

Esse arquivo apresenta uma *tag* de formulário `<form th:action="@{/categoria}" method="post" th:object="${categoria}">`. No atributo do Thymeleaf `th:action` é definido que será realizada uma requisição HTTP para URL `/categoria` a partir do contexto da aplicação. No atributo `method` é definido que a requisição HTTP será do tipo POST. E o atributo `th:object` indica que esse formulario irá receber um objeto `categoria`, que foi o objeto adicionado no *controller* em três ocasiões, no método `form` para novos registros e também na edição e no método `save()` quando ocorre um erro (`model.addAttribute("categoria", categoria);`).

Então, são adicionados duas *tags* `<input/>`, uma para o atributo `id` e outra para o `nome`. No caso do `id` no input é indicado no atributo `th:field` o valor `id` e `readonly`, pois o campo id do banco de dados é auto-incremento (`<input type="text" class="form-control" th:field="*{id}" readonly/>)`. O atributo `th:field` é responsável por adicionar os atributos **id, name** e **value** no HTML que será renderizado no navegador.

O `<input />` utilizado para o `nome` segue o mesmo padrão que o `id`, porém sem o `readonly`, pois esse atributo poderá ser editável. Ainda no `input` do atributo nome existe a exibição dos erros: `<span th:if="${#fields.hasErrors('nome')}" th:errors="*{nome}" class="text-danger"></span>`, o objeto `fields` é retornado ao validar o objeto no servidor utilizando a anotação `@Valid` e a classe `BindingResult`.

Por fim, é utilizado um `submit` para fazer a requisição HTTP Post ao servidor. `<button class="btn btn-primary" type="submit">Salvar</button>`

Para testar, basta acessar `http://localhost:8025/categoria/new`. A tela com a lista de categorias (`list.html`) também precisa ser atualizada para exibir as novas mensagens.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Cadastro de Gêneros</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
        <link rel="stylesheet" href="https://unpkg.com/shards-ui@latest/dist/css/shards.min.css">
    </head>
    <body>
        <div class="container my-5">
        	<h2>Formulário de Cadastro - Gênero</h2>
        	
        	<div class="row">
        		<form th:action="@{/categoria}" method="post" th:object="${categoria}">
        			<div class="row">
        				<div class="form-group">
	        				<label for="id">Código</label>
	        				<input type="text" class="form-control" th:field="*{id}" readonly/>
        				</div>
        			</div>
        			<div class="row">
        				<div class="form-group">
	        				<label for="nome">Nome</label>
	        				<input type="text" class="form-control" th:field="*{nome}" />
        					<span th:if="${#fields.hasErrors('nome')}"
        						th:errors="*{nome}" class="text-danger"></span>
        				</div>
        			</div>
        			
        			<div class="row">
        				<button class="btn btn-primary" type="submit" >Salvar</button>
        			</div>
        		</form>
        	</div> <!-- FIM div row -->
        </div>
    </body>
</html>
```
O conteúdo da nova página `list.html` apresenta agora um `hyperlink` para a tela de novos categorias. E uma div para exibir as mensagens de sucesso e erro `<div th:if="${!#strings.isEmpty(sucesso)}"  class="alert alert-success"> ... </div>` e `<div th:if="${!#strings.isEmpty(erro)}"`. As strings `sucesso e erro` foram adicionadas na classe `CategoriaController` nos métodos `save()` e `delete()` (que será desenvolvido na próxima seção) com o código `attributes.addFlashAttribute("sucesso", "Registro salvo com sucesso!");`. As mensagens são exibidas na *tag* `<span th:text="${sucesso}">Mensagem de sucesso!</span>` dentro da `div` de alerta.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Lista de Gêneros</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
        <link rel="stylesheet" href="https://unpkg.com/shards-ui@latest/dist/css/shards.min.css">
    </head>
    <body>
        <div class="container my-5">
    		<h1>Lista de Gêneros</h1>
    		<div class="row">
    			<div class="col-md-6">
    				<div th:if="${!#strings.isEmpty(sucesso)}" 
    					class="alert alert-success">
    					<i class="fa fa-check-circle"></i>
    					<span th:text="${sucesso}">Mensagem de sucesso!</span>	
   					</div>
   					
   					<div th:if="${!#strings.isEmpty(erro)}" 
    					class="alert alert-danger">
    					<i class="fa fa-trash"></i>
    					<span th:text="${erro}">Mensagem de erro!</span>	
   					</div>
    			</div>
    		</div>
    		<a class="btn btn-primary" th:href="@{/}">
        		<i class="fas fa-home"></i>
        	</a>
        	<a class="btn btn-primary" th:href="@{/categoria/new}">
        		<i class="fas fa-plus"></i> Novo
        	</a>
    		
    		<table class="table table-striped table-responsive-md">
    			<thead>
    				<tr>
    					<th>Código</th>
    					<th>Nome</th>
    					<th>Editar</th>
    					<th>Remover</th>
    				</tr>
    			</thead>
    			<tbody>
    				<tr th:each="categoria: ${categorias}">
    					<td th:text="${categoria.id}">código</td>
    					<td th:text="${categoria.nome}">nome</td>
						<!-- EDITAR -->
						<td>
						<a th:href="@{/categoria/{id}(id=${categoria.id})}" class="btn btn-primary">
							<i class="fas fa-edit ml-2"></i>
						</a>
						</td>
						<!-- REMOVER -->
						<td>
						<form method="post" 
							th:action="@{/categoria/{id}?_method=DELETE(id=${categoria.id})}">
							<input type="hidden" name="_method" value="DELETE">
							<button type="submit" class="btn btn-danger">
								<i class="fas fa-trash ml-2"></i>
							</button>
						</form>
						</td>
    				</tr>
    			</tbody>
    		</table> <!-- FIM TABLE -->
    		
        </div>
    </body>
</html>
```

#### 5.2.3 Método delete

Na listagem do último código da página `list.html` foi adicionado um código na última coluna da tabela para realizar a remoção de um registro da tabela de categorias. Quando é adicionado um parâmetro na URL da requisição com o conteúdo `_method=DELETE` ou é adicionado uma *tag* `hidden` com o  conteúdo `<input type="hidden" name="_method" value="DELETE">` é realizada uma requisição do tipo HTTP Delete para URL da propriedade `th:action`.

No `CategoriaController` foi adicionado o método `delete()` que está anotado com `@DeleteMapping("{id}")`, ou seja, esse método recebe requisições HTTP Delete em `/categoria/{id}`, em que `{id}` é o código do gênero que será removido do banco de dados.

```html
<!-- REMOVER -->
<td>
	<form method="post" th:action="@{/categoria/{id}?_method=DELETE(id=${categoria.id})}">
		<input type="hidden" name="_method" value="DELETE">
		<button type="submit" class="btn btn-danger">
			<i class="fas fa-trash ml-2"></i>
		</button>
	</form>
</td>
```
```Java
@Controller
@RequestMapping("categoria")
public class CategoriaController {
	 // ... foram omitidos os métodos listados anteriormente
	@DeleteMapping("{id}") // /categoria/25
	public String delete(@PathVariable Long id,RedirectAttributes attributes) {
		try {
			categoriaService.delete(id);
			attributes.addFlashAttribute("sucesso", "Registro removido com sucesso!");
		} catch (Exception e) {
			attributes.addFlashAttribute("erro", "Falha ao remover o registro!");
		}
		return "redirect:/categoria";
	}
}
```

### 5.3 Criando o formulário de Série

A tela de lista dos objetos **produto**, composta pelo arquivo `list.html` localizado no diretório `src/main/resources/templates/produto`, seque o mesmo padrão que a lista de **categoria**. Na qual é realizada uma requisicão HTTP Get para `/produto` que é atendia pelo método `list()` localizado na classe `ProdutoController`, conforme o código abaixo, que está dentro do *controller* de série.
```Java
	... 
	@GetMapping
	public String list( Model model ) {
		model.addAttribute("produtos", produtoService.findAll());
		return "produto/list";
	}
	...
```
E, na camada *view* a exibicão da lista também segue o mesmo padrão. Com detalhes na formatação das colunas de data, para ficar no formato `dd/mm/yyyy`.

```html
...
<tbody>
	<tr th:each="produto : ${produtos}">
	    <td th:text="${produto.id}">código</td>
	    <td th:text="${produto.nome}">nome</td>
		<td th:text="${#temporals.format(produto.dataEstreia, 'dd/MM/yyyy')}"></td>
		<td th:text="${#temporals.format(produto.dataEncerramento, 'dd/MM/yyyy')}"></td>
    	<td th:text="${produto.categoria.nome}"></td>
    	<td th:text="${produto.marca.nome}"></td>
		<!-- EDITAR -->
		<td>
			<a th:href="@{/produto/{id}(id=${produto.id})}" class="btn btn-primary">
				<i class="fas fa-edit ml-2"></i>
			</a>
		</td>
		<!-- REMOVER -->
		<td>
			<form method="post" th:action="@{/produto/{id}?_method=DELETE(id=${produto.id})}">
				<input type="hidden" name="_method" value="DELETE">
				<button type="submit" class="btn btn-danger">
					<i class="fas fa-trash ml-2"></i>
				</button>
			</form>
		</td>
	</tr>
</tbody>
...
```
Mas o que realmente diferencia o CRUD de **produto** dos de **categoria** e **marca**, são os relacionamentos que **produto** possui com as outras entidades. No *model* de **produto** pode ser observado as anotações no mapeamento que fazem referência à esse relacionamento. Esses dados ao serem preenchidos no cadastro de uma Série, devem vir do banco de dados.

```Java
@Entity
public class Produto {
	...
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", referencedColumnName = "id")
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "marca_id", referencedColumnName = "id")
	private Marca marca;
	
	...
```
Ao acessar o formuário de cadastro de **produto** é realizada uma requisição HTTP Get para url `/new`, que é atendida pelo método `form()` localizado na classe `ProdutoController`. No corpo do método é adicionado um atributo `produto`, com uma nova instância de **Produto** e então é chamado o método `carregarCombos()`. Nesse métodos são adicionadas as listas de **categoria** e **produto**, que serão utilizadas na camada *view* para exibir os dados aos usuários.
```Java
...
	@GetMapping({"new", "novo"})
	public String form( Model model) {
		model.addAttribute("produto", new Produto());
		carregarCombos(model);
		return "produto/form";
	}
	
	private void carregarCombos(Model model) {
		model.addAttribute("categorias", categoriaService.findAll() );
		model.addAttribute("marcas", marcaService.findAll() );
	}
...
```
No formulário da camada *view* que é o arquivo `form.html` dentro da pasta produto as duas listas adicionadas no método `carregarCombos()` serão percorridas para exibir em uma *tag* `<select />`as listas de **categoria** e **marca**. A *tag* de **categoria** contem o `th:field="*{categoria}"`, que é o nome do atributo na classe `Produto`. E na *tag* `<option />` da *tag* `<select />` é percorrida a lista de **categorias** que foi adicionada no *controller*. A *tag* `<select />` de **marca** seguiu o mesmo padrão.
```html
...
<div class="row">
	<div class="form-group">
		<label for="categoria">Gênero</label>
		<select class="form-control" th:field="*{categoria}">
			<option value=""> (selecione) </option>
			<option th:each="categoria: ${categorias}"
				th:value="${categoria.id}"
				th:text="${categoria.nome}"></option>
		</select>
		<span th:if="${#fields.hasErrors('categoria')}"
			th:errors="*{categoria}"
			class="text-danger"
		></span>
	</div>
</div> <!-- FIM GENERO -->
<div class="row">
	<div class="form-group">
		<label for="marca">Marca</label>
		<select class="form-control" th:field="*{marca}">
			<option value=""> (selecione) </option>
			<option th:each="marca: ${marcas}"
				th:value="${marca.id}"
				th:text="${marca.nome}"></option>
		</select>
		<span th:if="${#fields.hasErrors('marca')}"
			th:errors="*{marca}"
			class="text-danger"
		></span>
	</div>
</div> <!-- FIM PRODUTORA -->
...
```

## Referências

[1] Spring Boot [https://docs.spring.io/spring-boot/docs/current/reference/html/](https://docs.spring.io/spring-boot/docs/current/reference/html/) *Acessado em: 05/07/2021*

[2] Spring Web MVC [https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc) *Acessado em: 05/07/2021*

[3] Spring Data JPA [https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface) *Acessado em: 05/07/2021*

[4] Thymeleaf [https://www.thymeleaf.org/](https://www.thymeleaf.org/) - *Acessado em: 05/07/2021*

[5] W3Schools HTML [https://www.w3schools.com/html/](https://www.w3schools.com/html/) - *Acessado em: 05/07/2021*

[6] Spring Web MVC https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html *Acessado em: 05/07/2021*

# Atividade

Criar um novo projeto utilizando o mapeamento ORM das classes criadas no projeto anterior
- Cidade {id, nome}
- Autor {id, nome}
- Editora {id, nome}
- Categoria {id, descricao}
- Livro {id, titulo, EDITORA, GENERO, AUTOR, ano, isbn, CIDADE, valor}

E criar as telas de listagem e formulário para as classes sem relacionamento (Cidade, Autor, Editora, Categoria e Livro).