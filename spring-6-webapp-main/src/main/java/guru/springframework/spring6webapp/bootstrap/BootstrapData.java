package guru.springframework.spring6webapp.bootstrap;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repositories.AuthorRepository;
import guru.springframework.spring6webapp.repositories.BookRepository;
import guru.springframework.spring6webapp.repositories.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123456");

        Author ericSaved = authorRepository.save(eric);
        Book dddSaved = bookRepository.save(ddd);

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");

        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("54757585");

        Author rodSaved = authorRepository.save(rod);
        Book noEJBSaved = bookRepository.save(noEJB);

        ericSaved.getBooks().add(dddSaved);
        rodSaved.getBooks().add(noEJBSaved);
        dddSaved.getAuthors().add(ericSaved);
        noEJBSaved.getAuthors().add(rodSaved);

        Publisher bonnier = new Publisher(); // Create a Publisher object
        bonnier.setPublisherName("Bonnier"); // Set the name
        bonnier.setAddress("Bonniervagen 1"); // Set the address line 1
        bonnier.setCity("Stockholm"); // Set the city
        bonnier.setState("Handen"); // Set the state
        bonnier.setZipCode("12345"); // Set the zip

        Publisher savedPublisher = publisherRepository.save(bonnier); // Save the Publisher object

        savedPublisher.getBooks().add(dddSaved);
        savedPublisher.getBooks().add(noEJBSaved);

        savedPublisher = publisherRepository.save(savedPublisher);

        dddSaved.setPublisher(savedPublisher);
        noEJBSaved.setPublisher(savedPublisher);

        authorRepository.save(ericSaved);
        authorRepository.save(rodSaved);
        bookRepository.save(dddSaved);
        bookRepository.save(noEJBSaved);


        System.out.println("Started in Bootstrap");
        System.out.println("Number of Books: " + bookRepository.count()); // Print the number of books
        System.out.println("Number of Authors: " + authorRepository.count()); // Print the number of authors
        System.out.println("Number of Books for Eric: " + ericSaved.getBooks().size()); // Print the number of books for Eric
        System.out.println("Number of Books for Rod: " + rodSaved.getBooks().size()); // Print the number of books for Rod
        System.out.println("Number of Publishers: " + publisherRepository.count()); // Print the number of publishers
        // print number of books by publisher
        publisherRepository.findAll().forEach(publisher -> System.out.println("Publisher: " + publisher.getPublisherName()
                + " Published: " + publisher.getBooks().size() + " books"));
    }
}
