package learn.spring.mircroservices.catalog.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.spring.mircroservices.catalog.model.CatalogItem;
import learn.spring.mircroservices.catalog.model.Movie;
import learn.spring.mircroservices.catalog.service.CatalogInfo;
import learn.spring.mircroservices.catalog.service.MovieInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	private CatalogInfo catalogInfo;

	@Autowired
	private MovieInfo movieInfo;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		// Movies names needs to be pulled for user id
		Movie[] movies = movieInfo.getMovies(userId);
		return Arrays.asList(movies).stream().map(movieObj -> catalogInfo.getRating(movieObj))
				.collect(Collectors.toList());
	}

}
