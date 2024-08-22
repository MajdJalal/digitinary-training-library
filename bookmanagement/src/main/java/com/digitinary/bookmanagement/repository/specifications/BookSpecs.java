package com.digitinary.bookmanagement.repository.specifications;

import com.digitinary.bookmanagement.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    /**
     * creates a spec for matching the id identically
     * @param passedBookId
     * @return
     */
    public static Specification<Book> hasId(Long passedBookId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), passedBookId);
    }

    /**
     * creates a spec for matching the name (containsName)
     * @param passedBookName
     * @return
     */
    public static Specification<Book> containsName(String passedBookName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + passedBookName.toLowerCase() + "%");
    }

    /**
     * creates a spec for matching the quantity identically
     * @param passedBookQuantity
     * @return
     */
    public static Specification<Book> hasQuantity(Integer passedBookQuantity){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("quantity"), passedBookQuantity);
    }
}
