package learn.spring.mircroservices.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import learn.spring.mircroservices.catalog.model.CatalogItem;
import learn.spring.mircroservices.catalog.model.Movie;
import learn.spring.mircroservices.catalog.model.Rating;

@Service
public class CatalogInfo {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalog", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") }, threadPoolKey = "movieInfoPool", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "50"),
					@HystrixProperty(name = "maxQueueSize", value = "20") })
	public CatalogItem getRating(Movie movie) {
		Rating rating = restTemplate.getForObject("http://SPRING-PROFILE-MOVIE-RATINGS/ratings/" + movie.getMovieId(),
				Rating.class);
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}

	public CatalogItem getFallbackCatalog(Movie movie) {
		return new CatalogItem("1234", "No Movie", 0);
	}
}
