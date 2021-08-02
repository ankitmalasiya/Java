package com.ankit.controller;

import com.ankit.exception.ResourceNotFoundException;
import com.ankit.model.Tutorial;
import com.ankit.repository.TutorialRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tutorials")
public class TutorialController {

    public static final String TUTORIALS = "tutorials";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String TOTAL_ITEMS = "totalItems";
    public static final String TOTAL_PAGES = "totalPages";

    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (title == null)
            tutorials.addAll((Collection<? extends Tutorial>) tutorialRepository.findAll());
        else {
            tutorials.addAll(tutorialRepository.findByTitleContaining(title));
        }
        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {

        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial savedTutorial = tutorialRepository.save(tutorial);
            return new ResponseEntity<>(savedTutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial updateTutorial = tutorialData.get();
            updateTutorial.setTitle(tutorial.getTitle());
            updateTutorial.setDescription(tutorial.getDescription());
            updateTutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(updateTutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tutorialRepository.save(tutorial), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

            if (tutorialData.isPresent()) {
                tutorialRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorialsPagingAndSorting1")
    public ResponseEntity<Map<String, Object>> getAllTutorials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            List<Tutorial> tutorials;
            Pageable paging = PageRequest.of(page, size);
            Page<Tutorial> pageTutorial;

            pageTutorial = tutorialRepository.findAll(paging);
            tutorials = pageTutorial.getContent();

            return getMapResponseEntity(tutorials, pageTutorial);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    http://localhost:8001/api/tutorials/tutorialsPagingAndSorting1?page=0&size=5&sort=published,desc&sort=title,asc
    @GetMapping("/tutorialsPagingAndSorting2")
    public ResponseEntity<Map<String, Object>> getAllTutorialsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] sort1 = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(sort1[1]), sort1[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Tutorial> tutorials;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Tutorial> pageTutorial;
            pageTutorial = tutorialRepository.findAll(pagingSort);
            tutorials = pageTutorial.getContent();

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return getMapResponseEntity(tutorials, pageTutorial);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    private ResponseEntity<Map<String, Object>> getMapResponseEntity(List<Tutorial> tutorials, Page<Tutorial> pageTuts) {
        Map<String, Object> response = new HashMap<>();
        response.put(TUTORIALS, tutorials);
        response.put(CURRENT_PAGE, pageTuts.getNumber());
        response.put(TOTAL_ITEMS, pageTuts.getTotalElements());
        response.put(TOTAL_PAGES, pageTuts.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}