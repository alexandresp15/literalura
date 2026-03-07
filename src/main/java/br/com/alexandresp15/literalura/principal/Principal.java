package br.com.alexandresp15.literalura.principal;

import br.com.alexandresp15.literalura.model.*;
import br.com.alexandresp15.literalura.repository.AutorRepository;
import br.com.alexandresp15.literalura.repository.LivroRepository;
import br.com.alexandresp15.literalura.service.ConsumoApi;
import br.com.alexandresp15.literalura.service.ConverteDados;

import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("""
                
                ---- LiterAlura ----
                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em determinado ano
                5 - Listar livros por idioma
                0 - Sair
                """);

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {

                case 1:
                    buscarLivro();
                    break;

                case 2:
                    listarLivros();
                    break;

                case 3:
                    listarAutores();
                    break;

                case 4:
                    listarAutoresVivos();
                    break;

                case 5:
                    listarLivrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Encerrando aplicação...");
                    break;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivro() {

        System.out.println("Digite o nome do livro:");
        String nomeLivro = leitura.nextLine();

        String json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));

        DadosResposta resposta = conversor.obterDados(json, DadosResposta.class);

        if (resposta.resultados().isEmpty()) {
            System.out.println("Livro não encontrado.");
            return;
        }

        DadosLivro dadosLivro = resposta.resultados().get(0);

        DadosAutor dadosAutor = dadosLivro.autores().get(0);

        Autor autor = new Autor(dadosAutor);

        Livro livro = new Livro(dadosLivro);

        livro.setAutor(autor);

        autorRepository.save(autor);
        livroRepository.save(livro);

        System.out.println("Livro salvo com sucesso!");
    }

    private void listarLivros() {

        var livros = livroRepository.findAll();

        livros.forEach(livro ->
                System.out.println(
                        "Livro: " + livro.getTitulo() +
                                " | Autor: " + livro.getAutor().getNome() +
                                " | Idioma: " + livro.getIdioma()
                ));
    }

    private void listarAutores() {

        var autores = autorRepository.findAll();

        autores.forEach(autor ->
                System.out.println(
                        "Autor: " + autor.getNome() +
                                " (" + autor.getAnoNascimento() +
                                " - " + autor.getAnoFalecimento() + ")"
                ));
    }

    private void listarAutoresVivos() {

        System.out.println("Digite o ano:");
        int ano = leitura.nextInt();
        leitura.nextLine();

        var autores = autorRepository
                .findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);

        autores.forEach(a ->
                System.out.println("Autor vivo em " + ano + ": " + a.getNome()));
    }

    private void listarLivrosPorIdioma() {

        System.out.println("""
            Idiomas disponíveis:
            pt - Português
            en - Inglês
            es - Espanhol
            fr - Francês
            """);

        String idioma = leitura.nextLine();

        var livros = livroRepository.findByIdioma(idioma);

        livros.forEach(l ->
                System.out.println("Livro: " + l.getTitulo()));
    }

}