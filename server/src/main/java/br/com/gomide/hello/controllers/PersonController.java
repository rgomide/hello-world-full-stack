package br.com.gomide.hello.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gomide.hello.mapper.DozerMapper;
import br.com.gomide.hello.model.Person;
import br.com.gomide.hello.service.PersonService;
import br.com.gomide.hello.vo.PersonVO;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {

  @Autowired
  private PersonService personService;

  @GetMapping
  public List<PersonVO> findAll() {
    List<Person> people = personService.findAll();
    return DozerMapper.parseListObjects(people, PersonVO.class);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    personService.delete(id);
  }

  @PostMapping
  public PersonVO create(@RequestBody PersonVO person) {
    Person personEntity = DozerMapper.parseObject(person, Person.class);
    return DozerMapper.parseObject(personService.create(personEntity), PersonVO.class);
  }

}
