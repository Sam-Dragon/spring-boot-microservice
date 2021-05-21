package learn.spring.mircroservices.catalog.webclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogItem {

	private String title;
	private String description;
	private Integer rating;
}
