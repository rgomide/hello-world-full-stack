package br.com.gomide.hello.vo;

public class MusicUploadVO {
  private String name;
  private FileVO file;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FileVO getFile() {
    return file;
  }

  public void setFile(FileVO file) {
    this.file = file;
  }
}
