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
import br.com.gomide.hello.model.Music;
import br.com.gomide.hello.repository.MusicRepository;
import br.com.gomide.hello.vo.MusicVO;

@RestController
@RequestMapping("/api/v1/musics")
public class MusicController {

  @Autowired
  private MusicRepository musicRepository;

  @GetMapping
  public List<MusicVO> findAll() {
    List<Music> musics = musicRepository.findAll();
    return DozerMapper.parseListObjects(musics, MusicVO.class);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    // TODO: delete file from local storage
    musicRepository.deleteById(id);
  }

  @PostMapping
  public MusicVO create(@RequestBody MusicVO music) {
    Music musicEntity = DozerMapper.parseObject(music, Music.class);


    // TODO: save file to local storage
    return DozerMapper.parseObject(musicRepository.save(musicEntity), MusicVO.class);
  }

}
