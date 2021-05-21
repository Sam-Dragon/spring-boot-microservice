package learn.spring.microservice.movie.ratings.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.spring.microservice.movie.ratings.model.Rating;

@RestController
@RequestMapping("/ratings")
public class MovieRatingsController {

	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		if (movieId.equalsIgnoreCase("1234"))
			return new Rating(movieId, 3);
		else if (movieId.equalsIgnoreCase("5678"))
			return new Rating(movieId, 4);
		else
			return new Rating(movieId, 0);

	}
}
