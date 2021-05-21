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

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import learn.spring.mircroservices.catalog.model.CatalogItem;
import learn.spring.mircroservices.catalog.model.Movie;
import learn.spring.mircroservices.catalog.model.Rating;

@RestController
@RequestMapping("/catalog/proxy")
public class MovieCatalogController_ProblemWithProxyClass {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog_ProxyIssue(@PathVariable("userId") String userId) {
		// Movies names needs to be pulled for user id
		Movie[] movies = getMovies_ProxyIssue(userId);
		return Arrays.asList(movies).stream().map(movieObj -> getRating_ProxyIssue(movieObj))
				.collect(Collectors.toList());
	}

	@HystrixCommand(fallbackMethod = "getFallbackCatalog_ProxyIssue")
	private CatalogItem getRating_ProxyIssue(Movie movie) {
		Rating rating = restTemplate.getForObject("http://SPRING-PROFILE-MOVIE-RATINGS/ratings/" + movie.getMovieId(),
				Rating.class);
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}

	public CatalogItem getFallbackCatalog_ProxyIssue(Movie movie) {
		return new CatalogItem("No Movie", null, 0);
	}

	@HystrixCommand(fallbackMethod = "getFallbackMovies_ProxyIssue")
	private Movie[] getMovies_ProxyIssue(String userId) {
		return restTemplate.getForObject("http://spring-profile-movie-info/movies/user/" + userId, Movie[].class);
	}

	public Movie[] getFallbackMovies_ProxyIssue(String userId) {
		return new Movie[] { new Movie("0", "No Movie", null) };
	}
}
