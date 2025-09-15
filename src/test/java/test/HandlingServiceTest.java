package test;

import devops.model.Car;
import devops.model.Handling;
import devops.repository.HandlingRepository;
import devops.service.HandlingService;
import devops.service.TelegramNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandlingServiceTest {

    @Mock
    private HandlingRepository repository;
    
    @Mock
    private TelegramNotificationService telegramService;

    @InjectMocks
    private HandlingService handlingService;

    @Test
    void getListHandlingByCarId_shouldReturnList() {
        Long carId = 1L;
        List<Handling> expectedHandlings = Arrays.asList(new Handling(), new Handling());
        when(repository.getHandlingListByCarId(carId)).thenReturn(expectedHandlings);

        List<Handling> result = handlingService.getListHandlingByCarId(carId);

        assertEquals(expectedHandlings, result);
        verify(repository).getHandlingListByCarId(carId);
    }

    @Test
    void getListHandling_shouldReturnAll() {
        List<Handling> expectedHandlings = Arrays.asList(new Handling(), new Handling());
        when(repository.findAll()).thenReturn(expectedHandlings);

        List<Handling> result = handlingService.getListHandling();

        assertEquals(expectedHandlings, result);
        verify(repository).findAll();
    }

    @Test
    void addHandling_shouldSaveAndReturnHandling() {
        Handling handlingToSave = new Handling();
        when(repository.save(handlingToSave)).thenReturn(handlingToSave);

        Handling result = handlingService.addHandling(handlingToSave);

        assertEquals(handlingToSave, result);
        verify(repository).save(handlingToSave);
    }

    @Test
    void removeHandling_shouldDeleteHandling() {
        Long id = 1L;

        handlingService.removeHandling(id);

        verify(repository).deleteById(id);
    }

    @Test
    void getHandlingById_shouldReturnHandling() {
        Long id = 1L;
        Handling expectedHandling = new Handling();
        when(repository.getById(id)).thenReturn(expectedHandling);

        Handling result = handlingService.getHandlingById(id);

        assertEquals(expectedHandling, result);
        verify(repository).getById(id);
    }

    @Test
    void updateHandling_shouldUpdateAndReturnHandling() {
        Long id = 1L;
        Handling handlingToUpdate = new Handling();
        handlingToUpdate.setType("Oil Change");
        handlingToUpdate.setCost(100);
        
        Car car = new Car();
        car.setBrand("BMW");
        handlingToUpdate.setCar(car);
        
        Handling existingHandling = new Handling();
        existingHandling.setType("Brake Check");
        existingHandling.setCost(50);
        
        when(repository.getById(id)).thenReturn(existingHandling);
        when(repository.save(existingHandling)).thenReturn(existingHandling);

        Handling result = handlingService.updateHandling(handlingToUpdate, id);

        assertEquals(existingHandling, result);
        verify(repository).getById(id);
        verify(repository).save(existingHandling);
    }
}