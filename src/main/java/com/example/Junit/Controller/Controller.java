package com.example.Junit.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Junit.Model.Movies;
import com.example.Junit.Repo.ModelRepo;

@RestController
@RequestMapping("/movies")
public class Controller {
	
	@Autowired
	private ModelRepo movierepo;
	
	
	@GetMapping
	public List<Movies> getallmovies(){
		return movierepo.findAll();
	}
	
	@GetMapping(value = "movieid")
	public Movies getbyId(@PathVariable(value = "moviesid") Long movieid) {
		return movierepo.findById(movieid).get();
	}
	
	@PostMapping
	public Movies moviecreation(@RequestBody Movies movie) {
		return movierepo.save(movie);
	}
	
	@PutMapping
	public Movies updatemovie (@RequestBody Movies movies) throws NotFoundException{
		if(movies == null || movies.getMovieid() == null){
			throw new NotFoundException();
		}
		
		Optional<Movies> optionalmovies = movierepo.findById(movies.getMovieid());
		if(!optionalmovies.isPresent()) {
			throw new NotFoundException();
		}
		
		Movies currenterecord = optionalmovies.get();
		currenterecord.setName(movies.getName());
		currenterecord.setRating(movies.getRating());
		currenterecord.setAbout(movies.getAbout());
		
		
		return movierepo.save(currenterecord);
	}
	
	@DeleteMapping(value ="moviesid")
	public void delete(@PathVariable(value = "moviesid")Movies movie) {
		movierepo.delete(movie);
	}
	
}
