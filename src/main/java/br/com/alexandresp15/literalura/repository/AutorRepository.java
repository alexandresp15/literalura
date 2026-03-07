package br.com.alexandresp15.literalura.repository;

import br.com.alexandresp15.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}