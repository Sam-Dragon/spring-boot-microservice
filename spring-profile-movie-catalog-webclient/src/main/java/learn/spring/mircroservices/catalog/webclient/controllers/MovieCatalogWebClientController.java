package learn.spring.mircroservices.catalog.webclient.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import learn.spring.mircroservices.catalog.webclient.model.CatalogItem;
import learn.spring.mircroservices.catalog.webclient.model.Movie;
import learn.spring.mircroservices.catalog.webclient.model.Rating;

@RestController
@RequestMapping("/catalog/webclient")
public class MovieCatalogWebClientController {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		// Movies names needs to be pulled for user id
		return Arrays.asList("1234", "5678", "0").stream().map(movieId -> {
			Movie movie = webClientBuilder.build().get().uri("http://localhost:3001/movies/" + movieId).retrieve()
					.bodyToMono(Movie.class).block();
			Rating rating = webClientBuilder.build().get().uri("http://localhost:3002/ratings/" + movieId).retrieve()
					.bodyToMono(Rating.class).block();

			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).collect(Collectors.toList());
	}
}
