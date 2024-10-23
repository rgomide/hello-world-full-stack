package br.com.gomide.hello.vo;

import java.util.Date;

public class PersonVO {
  private Long id;
  private String name;
  private Integer age;
  private Date createdAt;

  public PersonVO() {
    this.createdAt = new Date();
  }

  public PersonVO(Long id, String name, Integer age) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.createdAt = new Date();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
