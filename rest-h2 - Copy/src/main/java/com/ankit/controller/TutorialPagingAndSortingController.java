package com.ankit.controller;

import com.ankit.model.Tutorial;
import com.ankit.repository.TutorialPageableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

///api/tutorials
//sort by [id, descending] (default) & pagination [page=0, size=3] (default)
///api/tutorials?sort=title,asc
//sort by [title, ascending] & pagination [page=0, size=3] (default)
///api/tutorials?sort=published,desc&sort=title,asc
//order by column [published, descending], then order by column [title, ascending] & pagination [page=0, size=3] (default)
///api/tutorials?page=1&size=5&sort=published,desc&sort=title,asc
//order by column [published, descending], then order by column [title, ascending] & pagination [page=1, size=5]

@RestController
@RequestMapping("/apiPagingSorting/tutorials")
public class TutorialPagingAndSortingController {

    @Autowired
    TutorialPageableRepository tutorialRepository;

    public static final String TUTORIALS = "tutorials";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String TOTAL_ITEMS = "totalItems";
    public static final String TOTAL_PAGES = "totalPages";

    @GetMapping()
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

    //    http://localhost:8001/apiPagingSorting/tutorials/tutorialsPagingAndSorting?page=0&size=5&sort=published,desc&sort=title,asc
    @GetMapping("/tutorialsPagingAndSorting")
    public ResponseEntity<Map<String, Object>> getAllTutorialsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] sort1 = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(sort1[1]), sort1[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
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