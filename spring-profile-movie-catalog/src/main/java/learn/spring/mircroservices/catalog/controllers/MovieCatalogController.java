package learn.spring.mircroservices.catalog.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import learn.spring.mircroservices.catalog.model.CatalogItem;
import learn.spring.mircroservices.catalog.model.Movie;
import learn.spring.mircroservices.catalog.model.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		// Movies names needs to be pulled for user id
		Movie[] movies = restTemplate.getForObject("http://spring-profile-movie-info/movies/user/" + userId, Movie[].class);

		return Arrays.asList(movies).stream().map(movieObj -> {
			Movie movie = restTemplate.getForObject("http://spring-profile-movie-info/movies/" + movieObj.getMovieId(),
					Movie.class);
			Rating rating = restTemplate.getForObject("http://SPRING-PROFILE-MOVIE-RATINGS/ratings/" + movieObj.getMovieId(),
					Rating.class);

			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).collect(Collectors.toList());
	}
}
