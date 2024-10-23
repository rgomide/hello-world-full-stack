package br.com.gomide.hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gomide.hello.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

  public List<Person> findAllByOrderByNameDesc();

}
