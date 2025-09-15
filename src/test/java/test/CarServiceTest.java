package test;

import devops.model.Car;
import devops.repository.CarRepository;
import devops.service.CarService;
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
class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    
    @Mock
    private TelegramNotificationService telegramService;

    @InjectMocks
    private CarService carService;
    

    @Test
    void getCars_shouldReturnList_whenExists() throws Exception {
        List<Car> expectedCars = Arrays.asList(new Car(), new Car());
        when(carRepository.findAll()).thenReturn(expectedCars);

        List<Car> result = carService.getCars();

        assertEquals(expectedCars, result);
        verify(carRepository).findAll();
    }

    @Test
    void getCars_shouldThrowException_whenNull() {
        when(carRepository.findAll()).thenReturn(null);

        assertThrows(Exception.class, () -> carService.getCars());
    }

    @Test
    void addCar_shouldSaveAndReturnCar() {
        Car carToSave = new Car();
        when(carRepository.save(carToSave)).thenReturn(carToSave);

        Car result = carService.addCar(carToSave);

        assertEquals(carToSave, result);
        verify(carRepository).save(carToSave);
    }

    @Test
    void removeCarById_shouldDeleteCar() {
        Long id = 1L;
        Car car = new Car();
        car.setBrand("BMW");
        when(carRepository.getById(id)).thenReturn(car);

        carService.removeCarById(id);

        verify(carRepository).getById(id);
        verify(carRepository).deleteById(id);
        verify(telegramService).sendCarNotification("Удален автомобиль", "BMW", car.getYear());
    }

    @Test
    void updateCar_shouldUpdateAndReturnCar() {
        Long id = 1L;
        Car carToUpdate = new Car();
        carToUpdate.setBrand("BMW");
        carToUpdate.setModel("X5");
        
        Car existingCar = new Car();
        existingCar.setBrand("Audi");
        existingCar.setModel("A4");
        
        when(carRepository.getById(id)).thenReturn(existingCar);
        when(carRepository.save(existingCar)).thenReturn(existingCar);

        Car result = carService.updateCar(carToUpdate, id);

        assertEquals(existingCar, result);
        verify(carRepository).getById(id);
        verify(carRepository).save(existingCar);
        verify(telegramService).sendCarNotification("Обновлен автомобиль", "BMW", existingCar.getYear());
    }

    @Test
    void getCarById_shouldReturnCar_whenExists() throws Exception {
        Long id = 1L;
        Car expectedCar = new Car();
        when(carRepository.getById(id)).thenReturn(expectedCar);

        Car result = carService.getCarById(id);

        assertEquals(expectedCar, result);
        verify(carRepository).getById(id);
    }

    @Test
    void getCarById_shouldThrowException_whenNotFound() {
        Long id = 1L;
        when(carRepository.getById(id)).thenReturn(null);

        assertThrows(Exception.class, () -> carService.getCarById(id));
        verify(carRepository).getById(id);
    }
}