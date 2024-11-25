package br.com.gomide.hello.vo;

import java.util.Date;

public class MusicVO {
  private Long id;
  private String name;
  private String fileName;
  private Date createdAt;

  public MusicVO() {
    this.createdAt = new Date();
  }

  public MusicVO(Long id, String name, String fileName) {
    this.id = id;
    this.name = name;
    this.fileName = fileName;
    this.createdAt = new Date();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
