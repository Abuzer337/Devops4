package test;

import devops.controller.HandlingController;
import devops.model.Handling;
import devops.service.HandlingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandlingControllerTest {

    @Mock
    private HandlingService handlingService;

    @InjectMocks
    private HandlingController handlingController;

    @Test
    void getHandlingList_shouldReturnList_whenExists() throws Exception {
        List<Handling> expectedHandlings = Arrays.asList(new Handling(), new Handling());
        when(handlingService.getListHandling()).thenReturn(expectedHandlings);

        ResponseEntity<?> result = handlingController.GetHandlingList();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedHandlings, result.getBody());
        verify(handlingService).getListHandling();
    }

    @Test
    void getHandling_shouldReturnHandling_whenExists() throws Exception {
        Long id = 1L;
        Handling expectedHandling = new Handling();
        when(handlingService.getHandlingById(id)).thenReturn(expectedHandling);

        ResponseEntity<?> result = handlingController.GetHandling(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedHandling, result.getBody());
        verify(handlingService).getHandlingById(id);
    }

    @Test
    void addHandling_shouldReturnHandling_whenValid() {
        Handling handlingToAdd = new Handling();
        Handling savedHandling = new Handling();
        when(handlingService.addHandling(handlingToAdd)).thenReturn(savedHandling);

        ResponseEntity<?> result = handlingController.AddHandling(handlingToAdd);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(savedHandling, result.getBody());
        verify(handlingService).addHandling(handlingToAdd);
    }

    @Test
    void updateHandling_shouldReturnUpdatedHandling_whenValid() {
        Long id = 1L;
        Handling handlingToUpdate = new Handling();
        Handling updatedHandling = new Handling();
        when(handlingService.updateHandling(handlingToUpdate, id)).thenReturn(updatedHandling);

        ResponseEntity<?> result = handlingController.UpdateHandling(handlingToUpdate, id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedHandling, result.getBody());
        verify(handlingService).updateHandling(handlingToUpdate, id);
    }

    @Test
    void removeHandling_shouldReturnNoContent_whenValid() {
        Long id = 1L;

        ResponseEntity<?> result = handlingController.RemoveHandling(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals("{}", result.getBody());
        verify(handlingService).removeHandling(id);
    }
}
