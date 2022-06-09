package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final PermissionService permissionService;

    @Transactional(readOnly = true)
    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void update(long id, String name, long genreId, long authorId) {
        var author = authorRepository.findById(authorId).orElseThrow(InvalidDataForUpdateException::new);
        var genre = genreRepository.findById(genreId).orElseThrow(InvalidDataForUpdateException::new);
        var book = bookRepository.findById(id).orElseThrow(InvalidDataForUpdateException::new);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setName(name);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void insert(String name, long genreId, long authorId) {
        var genre = genreRepository.findById(genreId).orElseThrow(InvalidDataForUpdateException::new);
        var author = authorRepository.findById(authorId).orElseThrow(InvalidDataForUpdateException::new);
        var book = bookRepository.save(new Book(name, author, genre));
        addPermissionToAuthority(book);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteById(long id) {
        bookRepository.deleteExistingBookById(id);
    }

    @Transactional(readOnly = true)
    @Override
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(EmptyResultException::new);
    }


    private void addPermissionToAuthority(Book book) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        for (var authority : authentication.getAuthorities()) {
            if (hasRoleUser(authority) || hasRoleAdmin(authority)) {
                permissionService.addPermissionForAuthority(book, BasePermission.READ, "ROLE_USER");
                permissionService.addPermissionForAuthority(book, BasePermission.READ, "ROLE_ADMIN");
            }
            else if (hasRoleAdmin(authority)) {
                permissionService.addPermissionForAuthority(book, BasePermission.WRITE, "ROLE_ADMIN");
            }
        }
    }

    private boolean hasRoleUser(GrantedAuthority authority) {
        return authority.getAuthority().equals("ROLE_USER");
    }

    private boolean hasRoleAdmin(GrantedAuthority authority) {
        return authority.getAuthority().equals("ROLE_ADMIN");
    }

}