package br.com.gomide.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gomide.hello.model.Person;
import br.com.gomide.hello.repository.PersonRepository;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  public List<Person> findAll() {
    return personRepository.findAllByOrderByNameDesc();
  }

  public void delete(Long id) {
    personRepository.deleteById(id);
  }

  public Person create(Person person) {
    return personRepository.save(person);
  }
}
