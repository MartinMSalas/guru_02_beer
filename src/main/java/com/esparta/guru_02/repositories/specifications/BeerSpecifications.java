package com.esparta.guru_02.repositories.specifications;

import com.esparta.guru_02.entities.Beer;
import com.esparta.guru_02.model.BeerStyle;
import org.springframework.data.jpa.domain.Specification;

/*
 * Author: M
 * Date: 09-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public class BeerSpecifications {

    public static Specification<Beer> hasNameLike(String beerName) {
        return (root, query, cb) ->
                beerName == null
                        ? null
                        : cb.like(
                        cb.lower(root.get("beerName")),
                        "%" + beerName.toLowerCase() + "%"
                );
    }

    public static Specification<Beer> hasStyle(BeerStyle style) {
        return (root, query, cb) ->
                style == null
                        ? null
                        : cb.equal(root.get("beerStyle"), style);
    }
}
