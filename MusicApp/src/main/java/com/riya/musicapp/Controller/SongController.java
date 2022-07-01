package com.riya.musicapp.Controller;
import com.riya.musicapp.Exception.ResourceNotFoundException;
import com.riya.musicapp.Model.Song;
import com.riya.musicapp.Repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    // get all songs
    @GetMapping("/songs")
    public List<Song> getAllSongs(){
        return songRepository.findAll();
    }

    // create song rest api
    @PostMapping("/songs")
    public Song createSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

    // get song by id rest api
    @GetMapping("/songs/{id}")
    public ResponseEntity<Object> getSongById(@PathVariable Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not exist with id :" + id));
        return ResponseEntity.ok(song);
    }

    // update employee rest api

    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song songDetails){
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not exist with id :" + id));

        song.setTitle(songDetails.getTitle());
        song.setFilename(songDetails.getFilename());
        song.setArtist(songDetails.getArtist());

        Song updatedSong = songRepository.save(song);
        return ResponseEntity.ok(updatedSong);
    }

    // delete song rest api
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSong(@PathVariable Long id){
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not exist with id :" + id));

        songRepository.delete(song);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}