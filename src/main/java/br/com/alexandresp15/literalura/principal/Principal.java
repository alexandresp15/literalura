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

        System.out.println("Digite o nome do livro que deseja buscar:");

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
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor: " + autor.getNome());
    }
}