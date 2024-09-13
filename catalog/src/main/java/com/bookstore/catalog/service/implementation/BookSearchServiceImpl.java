package com.bookstore.catalog.service.implementation;

import com.bookstore.catalog.dto.BookDto;
import com.bookstore.catalog.entity.Book;
import com.bookstore.catalog.mappers.BookMapper;
import com.bookstore.catalog.service.BookSearchService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchServiceImpl implements BookSearchService {
    private final BookMapper bookMapper;
    private final EntityManager entityManager;

    @Override
    public List<BookDto> search(String title, String author, String genre, Double price) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            predicates.add(criteriaBuilder.like(book.get("title"), "%" + title + "%"));
        }

        if (author != null && !author.isEmpty()) {
            predicates.add(criteriaBuilder.like(book.get("author"), "%" + author + "%"));
        }

        if (genre != null && !genre.isEmpty()) {
            predicates.add(criteriaBuilder.like(book.get("genre"), "%" + genre + "%"));
        }

        if (price != null) {
            predicates.add(criteriaBuilder.equal(book.get("price"), price));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<Book> result = entityManager.createQuery(query).getResultList();

        return bookMapper.mapToDto(result);
    }
}

