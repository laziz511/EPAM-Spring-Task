package com.epam.esm.service.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        int page = 1;
        int size = 10;
        List<Tag> expectedTags = Arrays.asList(new Tag(), new Tag());
        when(tagRepository.findAll(page, size)).thenReturn(expectedTags);

        // Act
        List<Tag> result = tagService.findAll(page, size);

        // Assert
        assertEquals(expectedTags, result);
        verify(tagRepository, times(1)).findAll(page, size);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Tag expectedTag = new Tag();
        when(tagRepository.findById(id)).thenReturn(Optional.of(expectedTag));

        // Act
        Tag result = tagService.findById(id);

        // Assert
        assertEquals(expectedTag, result);
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> tagService.findById(id));
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        // Arrange
        Tag tagToCreate = new Tag();
        when(tagRepository.save(any(Tag.class))).thenReturn(tagToCreate);

        // Act
        Tag result = tagService.create(tagToCreate);

        // Assert
        assertNotNull(result);
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        Tag tagToDelete = new Tag();
        when(tagRepository.findById(id)).thenReturn(Optional.of(tagToDelete));

        // Act
        tagService.delete(id);

        // Assert
        verify(tagRepository, times(1)).findById(id);
        verify(tagRepository, times(1)).delete(tagToDelete);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        Long id = 1L;
        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> tagService.delete(id));
        verify(tagRepository, times(1)).findById(id);
        verify(tagRepository, never()).delete(any(Tag.class));
    }

    @Test
    void testFindMostUsedTagOfUserWithHighestOrderCost() {
        // Arrange
        Long userId = 1L;
        List<Tag> expectedTags = Arrays.asList(new Tag(), new Tag());
        when(tagRepository.findMostUsedTagOfUserWithHighestOrderCost(userId)).thenReturn(expectedTags);

        // Act
        List<Tag> result = tagService.findMostUsedTagOfUserWithHighestOrderCost(userId);

        // Assert
        assertEquals(expectedTags, result);
        verify(tagRepository, times(1)).findMostUsedTagOfUserWithHighestOrderCost(userId);
    }
}