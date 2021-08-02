package com.ankit.controller;

import com.ankit.model.Tutorial;
import com.ankit.repository.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TutorialControllerTest {

    TutorialRepository tutorialRepository;
    private TutorialController controller;

    @BeforeEach
    void setUp() {
        tutorialRepository = mock(TutorialRepository.class);
        controller = new TutorialController(tutorialRepository);
    }

//    @Test
//    void testPersoon() {
//        when(tutorialRepository.findByTitleContaining(any())).thenReturn(mock(List.class));
//        assertThat(controller.getTutorialById(1L)).isNotNull();
//    }


    @Test
    void getAllTutorials() {
        List<Tutorial> tutorials = mock(List.class);
        when(tutorialRepository.findAll()).thenReturn(tutorials);
        assertThat(controller.getAllTutorials("")).isNotNull();
    }

    @Test
    void findByPublished() {
    }

    @Test
    void createTutorial() {
    }

    @Test
    void updateTutorial() {
    }

    @Test
    void deleteTutorial() {
    }
}