package devops.controller;

import devops.model.Car;
import devops.service.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*")
@Slf4j
public class CarController {

    private CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") Long id) {
        try{
            return ResponseEntity.ok(carService.getCarById(id));
        }
        catch (Exception exception) {
            log.error("Error getting car with id {}: {}", id, exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/")
    public ResponseEntity<java.util.List<Car>> getCars() {
        try {
            return ResponseEntity.ok(carService.getCars());
        }
        catch (Exception exception) {
            log.error("Error getting cars: {}", exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> AddCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.addCar(car));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> UpdateCar(@RequestBody Car car, @PathVariable("id") Long id) {
        return ResponseEntity.ok(carService.updateCar(car, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> RemoveCar(@PathVariable("id") Long id) {
        carService.removeCarById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{}");
    }
}
