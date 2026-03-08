package br.com.alexandresp15.literalura.principal;

import br.com.alexandresp15.literalura.model.*;
import br.com.alexandresp15.literalura.repository.AutorRepository;
import br.com.alexandresp15.literalura.repository.LivroRepository;
import br.com.alexandresp15.literalura.service.ConsumoApi;
import br.com.alexandresp15.literalura.service.ConverteDados;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

import java.util.List;
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
                    
                    -------- LiterAlura --------
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    6 - Top 10 livros mais baixados
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

                case 6:
                    top10LivrosAPI();
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarLivro() {

        System.out.println("Digite o nome do livro:");
        String nomeLivro = leitura.nextLine();

        try {

            String nomeCodificado = URLEncoder.encode(nomeLivro, StandardCharsets.UTF_8);

            String json = consumo.obterDados(ENDERECO + nomeCodificado);

            DadosResposta resposta = conversor.obterDados(json, DadosResposta.class);

            if (resposta.resultados().isEmpty()) {
                System.out.println("Nenhum livro encontrado.");
                return;
            }

            System.out.println("\nLivros encontrados:\n");

            for (int i = 0; i < resposta.resultados().size() && i < 5; i++) {
                System.out.println(i + " - " + resposta.resultados().get(i).titulo());
            }

            System.out.println("\nEscolha o número do livro:");
            int escolha = leitura.nextInt();
            leitura.nextLine();

            DadosLivro dadosLivro = resposta.resultados().get(escolha);
            DadosAutor dadosAutor = dadosLivro.autores().get(0);

            String nomeAutor = dadosAutor.nome().trim();

            Autor autor = autorRepository
                    .findByNome(nomeAutor)
                    .orElseGet(() -> {
                        Autor novoAutor = new Autor(dadosAutor);
                        novoAutor.setNome(nomeAutor);
                        return autorRepository.save(novoAutor);
                    });

            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);

            livroRepository.save(livro);

            System.out.println("""
                
                Livro salvo com sucesso!
                -----------------------
                Título: %s
                Autor: %s
                Idioma: %s
                Downloads: %d
                """.formatted(
                    livro.getTitulo(),
                    autor.getNome(),
                    livro.getIdioma(),
                    livro.getNumeroDownloads()
            ));

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro.");
        }
    }

    private void listarLivros() {

        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        livros.forEach(livro -> System.out.println("""
                
                ----- LIVRO -----
                Título: %s
                Autor: %s
                Idioma: %s
                Downloads: %d
                """.formatted(
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getIdioma(),
                livro.getNumeroDownloads()
        )));
    }

    private void listarAutores() {

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado.");
            return;
        }

        autores.forEach(autor -> System.out.println("""
                
                ----- AUTOR -----
                Nome: %s
                Nascimento: %d
                Falecimento: %d
                """.formatted(
                autor.getNome(),
                autor.getAnoNascimento(),
                autor.getAnoFalecimento()
        )));
    }

    private void listarAutoresVivos() {

        System.out.println("Digite o ano:");
        int ano = leitura.nextInt();
        leitura.nextLine();

        List<Autor> autores = autorRepository
                .findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo nesse ano.");
            return;
        }

        autores.forEach(autor ->
                System.out.println("Autor vivo em " + ano + ": " + autor.getNome()));
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

        List<Livro> livros = livroRepository.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma.");
            return;
        }

        livros.forEach(livro ->
                System.out.println("Livro: " + livro.getTitulo() +
                        " | Autor: " + livro.getAutor().getNome()));
    }

    public void top10LivrosAPI() {

        ConsumoApi consumoApi = new ConsumoApi();
        String endereco = "https://gutendex.com/books/";

        var json = consumoApi.obterDados(endereco);
        DadosResposta resposta = conversor.obterDados(json, DadosResposta.class);

        System.out.println("\nTop 10 livros mais baixados:\n");

        resposta.resultados().stream()
                .sorted(Comparator.comparing(DadosLivro::numeroDownloads).reversed())
                .limit(10)
                .forEach(l -> System.out.println(
                        l.titulo() + " - Downloads: " + l.numeroDownloads()
                ));
    }
}