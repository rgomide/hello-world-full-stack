package br.com.gomide.hello.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import br.com.gomide.hello.mapper.DozerMapper;
import br.com.gomide.hello.model.Music;
import br.com.gomide.hello.repository.MusicRepository;
import br.com.gomide.hello.vo.FileVO;
import br.com.gomide.hello.vo.MusicUploadVO;
import br.com.gomide.hello.vo.MusicVO;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/musics")
public class MusicController {

  private static final String UPLOAD_DIR = "public/uploads"; // Static path

  @Autowired
  private MusicRepository musicRepository;

  @PostConstruct
  public void init() throws IOException {
    // Create uploads directory if it doesn't exist
    Path uploadsDir = Paths.get(UPLOAD_DIR);
    if (!Files.exists(uploadsDir)) {
      Files.createDirectories(uploadsDir);
    }
  }

  @GetMapping
  public List<MusicVO> findAll() {
    List<Music> musics = musicRepository.findAll();
    return DozerMapper.parseListObjects(musics, MusicVO.class);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws IOException {
    Music music = musicRepository.findById(id).orElse(null);
    if (music != null) {
      Path filePath = Paths.get(UPLOAD_DIR, music.getFileName());
      Files.deleteIfExists(filePath);
    }
    musicRepository.deleteById(id);
  }

  @GetMapping("/download/{id}")
  public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
    Music music = musicRepository.findById(id).orElse(null);
    if (music == null) {
      return ResponseEntity.notFound().build();
    }

    Path filePath = Paths.get(UPLOAD_DIR, music.getFileName());
    Resource resource = new UrlResource(filePath.toUri());

    // Determine content type
    String contentType = Files.probeContentType(filePath);
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header("Content-Disposition", "attachment; filename=\"" + music.getFileName() + "\"")
        .body(resource);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public MusicVO create(
      @RequestBody MusicUploadVO music) {
    System.out.println(music);
    try {
      FileVO file = music.getFile();
      // Decode base64 string to byte array
      String base64Data = file.uri().substring(file.uri().indexOf(",") + 1);

      byte[] fileBytes = Base64.getDecoder().decode(base64Data);

      // Generate a new filename with UUID
      String newFilename = UUID.randomUUID().toString();

      // Add file extension if provided in the original filename
      if (file.name() != null && file.name().contains(".")) {
        String fileExtension = file.name().substring(file.name().lastIndexOf("."));
        newFilename += fileExtension;
      } else {
        // Default to .mp3 if no extension
        newFilename += ".mp3";
      }

      // Save file
      Path filePath = Paths.get(UPLOAD_DIR, newFilename);
      Files.write(filePath, fileBytes);

      // Create and save music entity
      Music musicEntity = new Music();

      musicEntity.setName(music.getName());
      musicEntity.setFileName(newFilename);
      musicEntity.setCreatedAt(new Date());
      musicEntity.setFileName(newFilename);

      MusicVO savedMusic = DozerMapper.parseObject(musicRepository.save(musicEntity), MusicVO.class);

      return savedMusic;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error processing upload: " + e.getMessage());
    }
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<String> handleMissingParts(MissingServletRequestPartException e) {
    return ResponseEntity.badRequest().body("Missing required part: " + e.getRequestPartName());
  }

}
