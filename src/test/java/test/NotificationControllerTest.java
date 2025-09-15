package test;

import devops.controller.NotificationController;
import devops.model.Notification;
import devops.service.NotificationService;
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
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void getNotificationList_shouldReturnList_whenExists() throws Exception {
        List<Notification> expectedNotifications = Arrays.asList(new Notification(), new Notification());
        when(notificationService.getListNotifications()).thenReturn(expectedNotifications);

        ResponseEntity<?> result = notificationController.getNotificationList();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedNotifications, result.getBody());
        verify(notificationService).getListNotifications();
    }

    @Test
    void getNotification_shouldReturnNotification_whenExists() throws Exception {
        Long id = 1L;
        Notification expectedNotification = new Notification();
        when(notificationService.getNotification(id)).thenReturn(expectedNotification);

        ResponseEntity<?> result = notificationController.getNotification(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedNotification, result.getBody());
        verify(notificationService).getNotification(id);
    }

    @Test
    void createNotification_shouldReturnNotification_whenValid() {
        Notification notificationToCreate = new Notification();
        Notification createdNotification = new Notification();
        when(notificationService.createNotification(notificationToCreate)).thenReturn(createdNotification);

        ResponseEntity<?> result = notificationController.createNotification(notificationToCreate);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(createdNotification, result.getBody());
        verify(notificationService).createNotification(notificationToCreate);
    }

    @Test
    void activateNotification_shouldReturnNotification_whenValid() {
        Long id = 1L;
        Notification activatedNotification = new Notification();
        when(notificationService.activate(id)).thenReturn(activatedNotification);

        ResponseEntity<?> result = notificationController.activateNotification(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(activatedNotification, result.getBody());
        verify(notificationService).activate(id);
    }

    @Test
    void deactivateNotification_shouldReturnNotification_whenValid() {
        Long id = 1L;
        Notification deactivatedNotification = new Notification();
        when(notificationService.deactivate(id)).thenReturn(deactivatedNotification);

        ResponseEntity<?> result = notificationController.deactivateNotification(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(deactivatedNotification, result.getBody());
        verify(notificationService).deactivate(id);
    }

    @Test
    void updateNotification_shouldReturnUpdatedNotification_whenValid() {
        Long id = 1L;
        Notification notificationToUpdate = new Notification();
        Notification updatedNotification = new Notification();
        when(notificationService.updateNotification(notificationToUpdate, id)).thenReturn(updatedNotification);

        ResponseEntity<?> result = notificationController.updateNotification(id, notificationToUpdate);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedNotification, result.getBody());
        verify(notificationService).updateNotification(notificationToUpdate, id);
    }

    @Test
    void deleteNotification_shouldReturnNoContent_whenValid() {
        Long id = 1L;

        ResponseEntity<?> result = notificationController.deleteNotification(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals("{}", result.getBody());
        verify(notificationService).removeNotification(id);
    }
}

