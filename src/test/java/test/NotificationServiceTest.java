package test;

import devops.model.Notification;
import devops.repository.NotificationRepository;
import devops.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void createNotification_shouldSaveAndReturnNotification() {
        Notification notificationToCreate = new Notification();
        when(repository.save(notificationToCreate)).thenReturn(notificationToCreate);

        Notification result = notificationService.createNotification(notificationToCreate);

        assertEquals(notificationToCreate, result);
        verify(repository).save(notificationToCreate);
    }

    @Test
    void activate_shouldActivateAndReturnNotification() {
        Long id = 1L;
        Notification notification = new Notification();
        notification.setActive(false);
        when(repository.getById(id)).thenReturn(notification);
        when(repository.save(notification)).thenReturn(notification);

        Notification result = notificationService.activate(id);

        assertTrue(notification.isActive());
        assertEquals(notification, result);
        verify(repository).getById(id);
        verify(repository).save(notification);
    }

    @Test
    void deactivate_shouldDeactivateAndReturnNotification() {
        Long id = 1L;
        Notification notification = new Notification();
        notification.setActive(true);
        when(repository.getById(id)).thenReturn(notification);
        when(repository.save(notification)).thenReturn(notification);

        Notification result = notificationService.deactivate(id);

        assertFalse(notification.isActive());
        assertEquals(notification, result);
        verify(repository).getById(id);
        verify(repository).save(notification);
    }

    @Test
    void updateNotification_shouldUpdateAndReturnNotification() {
        Long id = 1L;
        Notification notificationToUpdate = new Notification();
        notificationToUpdate.setActive(true);
        
        Notification existingNotification = new Notification();
        existingNotification.setActive(false);
        
        when(repository.getById(id)).thenReturn(existingNotification);
        when(repository.save(existingNotification)).thenReturn(existingNotification);

        Notification result = notificationService.updateNotification(notificationToUpdate, id);

        assertEquals(existingNotification, result);
        verify(repository).getById(id);
        verify(repository).save(existingNotification);
    }

    @Test
    void removeNotification_shouldDeleteNotification() {
        Long id = 1L;

        notificationService.removeNotification(id);

        verify(repository).deleteById(id);
    }

    @Test
    void getNotification_shouldReturnNotification() {
        Long id = 1L;
        Notification expectedNotification = new Notification();
        when(repository.getById(id)).thenReturn(expectedNotification);

        Notification result = notificationService.getNotification(id);

        assertEquals(expectedNotification, result);
        verify(repository).getById(id);
    }

    @Test
    void getListNotifications_shouldReturnAllNotifications() {
        List<Notification> expectedNotifications = Arrays.asList(new Notification(), new Notification());
        when(repository.findAll()).thenReturn(expectedNotifications);

        List<Notification> result = notificationService.getListNotifications();

        assertEquals(expectedNotifications, result);
        verify(repository).findAll();
    }
}