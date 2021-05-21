package learn.spring.mircroservices.catalog.model;

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
