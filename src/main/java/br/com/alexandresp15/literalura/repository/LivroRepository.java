package br.com.alexandresp15.literalura.repository;

import br.com.alexandresp15.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByIdioma(String idioma);
}