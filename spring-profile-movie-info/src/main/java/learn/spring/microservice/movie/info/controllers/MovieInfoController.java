package learn.spring.microservice.movie.info.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.spring.microservice.movie.info.model.Movie;

@RestController
@RequestMapping("/movies")
public class MovieInfoController {

	@GetMapping("/{movieId}")
	public Movie externalMethod(@PathVariable("movieId") String movieId) {
		if (movieId.equalsIgnoreCase("1234"))
			return new Movie(movieId, "One Two Three Four", "One Two Three Four Desc");
		else if (movieId.equalsIgnoreCase("5678"))
			return new Movie(movieId, "Five Six Seven Eight", "Five Six Seven Eight Desc");
		else
			return new Movie(movieId, "Zero", "Zero Desc");
	}

	@GetMapping("/user/{userId}")
	public List<Movie> userMovies(@PathVariable("userId") String userId) {
		if (userId.equalsIgnoreCase("1"))
			return Arrays.asList(new Movie("1234", "One Two Three Four", "One Two Three Four Desc"),
					new Movie("5678", "Five Six Seven Eight", "Five Six Seven Eight Desc"),
					new Movie("0", "Zero", "Zero Desc"));
		else if (userId.equalsIgnoreCase("2"))
			return Arrays.asList(new Movie("5678", "Five Six Seven Eight", "Five Six Seven Eight Desc"));
		else
			return Arrays.asList(new Movie("1234", "One Two Three Four", "One Two Three Four Desc"));
	}
}
