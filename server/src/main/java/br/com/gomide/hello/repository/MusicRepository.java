package br.com.gomide.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gomide.hello.model.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {

}
