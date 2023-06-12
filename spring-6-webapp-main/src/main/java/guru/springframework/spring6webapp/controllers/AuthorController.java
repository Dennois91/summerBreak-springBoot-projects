package guru.springframework.spring6webapp.controllers;

import guru.springframework.spring6webapp.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("/authors") // http://localhost:8080/authors
    public String getAuthors(Model model) {

        model.addAttribute("authors", authorService.findAll());

        return "authors";
    }
}

/*
Use same methods used to display a list of books to display a list of Authors

Create Author Service and Implementation for “findAll”

Create Author Controller for path “/authors”

Add to model response list of authors

Create Thymeleaf template to display list of Authors

Test in browser
 */
