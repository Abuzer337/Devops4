package test;

import devops.controller.CarController;
import devops.model.Car;
import devops.service.CarService;
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
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @Test
    void getCars_shouldReturnList_whenExists() throws Exception {
        List<Car> expectedCars = Arrays.asList(new Car(), new Car());
        when(carService.getCars()).thenReturn(expectedCars);

        ResponseEntity<?> result = carController.GetCars();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedCars, result.getBody());
        verify(carService).getCars();
    }

    @Test
    void getCars_shouldReturnBadRequest_whenException() throws Exception {
        when(carService.getCars()).thenThrow(new Exception("Error"));

        ResponseEntity<?> result = carController.GetCars();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("{}", result.getBody());
    }

    @Test
    void getCar_shouldReturnCar_whenExists() throws Exception {
        Long id = 1L;
        Car expectedCar = new Car();
        when(carService.getCarById(id)).thenReturn(expectedCar);

        ResponseEntity<?> result = carController.GetCar(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedCar, result.getBody());
        verify(carService).getCarById(id);
    }

    @Test
    void getCar_shouldReturnBadRequest_whenException() throws Exception {
        Long id = 1L;
        when(carService.getCarById(id)).thenThrow(new Exception("Error"));

        ResponseEntity<?> result = carController.GetCar(id);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("{}", result.getBody());
    }

    @Test
    void addCar_shouldReturnCar_whenValid() {
        Car carToAdd = new Car();
        Car savedCar = new Car();
        when(carService.addCar(carToAdd)).thenReturn(savedCar);

        ResponseEntity<?> result = carController.AddCar(carToAdd);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(savedCar, result.getBody());
        verify(carService).addCar(carToAdd);
    }

    @Test
    void updateCar_shouldReturnUpdatedCar_whenValid() {
        Long id = 1L;
        Car carToUpdate = new Car();
        Car updatedCar = new Car();
        when(carService.updateCar(carToUpdate, id)).thenReturn(updatedCar);

        ResponseEntity<?> result = carController.UpdateCar(carToUpdate, id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedCar, result.getBody());
        verify(carService).updateCar(carToUpdate, id);
    }

    @Test
    void removeCar_shouldReturnNoContent_whenValid() {
        Long id = 1L;

        ResponseEntity<?> result = carController.RemoveCar(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals("{}", result.getBody());
        verify(carService).removeCarById(id);
    }
}

