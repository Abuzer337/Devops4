package devops.service;

import devops.model.Car;
import devops.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    @Autowired
    private CarRepository carRepository;
    
    @Autowired
    private TelegramNotificationService telegramService;

    public Car getCarById(Long id) throws Exception {
        return carRepository.findById(id)
            .orElseThrow(() -> new Exception("Car not found"));
    }

    public List<Car> getCars() throws Exception {
        var cars = carRepository.findAll();
        if(cars.isEmpty()) {
            throw new Exception("No cars found");
        }
        return cars;
    }

    public Car addCar(Car car) {
        Car savedCar = carRepository.save(car);
        telegramService.sendCarNotification("Добавлен автомобиль", savedCar.getBrand(), savedCar.getYear());
        return savedCar;
    }

    public Car updateCar(Car car, Long destId) {
        var destCar = carRepository.findById(destId)
            .orElseThrow(() -> new RuntimeException("Car not found"));
        destCar.setBrand(car.getBrand());
        destCar.setYear(car.getYear());
        destCar.setVIN(car.getVIN());
        destCar.setModel(car.getModel());
        destCar.setMileage(car.getMileage());
        Car updatedCar = carRepository.save(destCar);
        telegramService.sendCarNotification("Обновлен автомобиль", updatedCar.getBrand(), updatedCar.getYear());
        return updatedCar;
    }

    public void removeCarById(Long id) {
        carRepository.findById(id).ifPresent(car -> {
            telegramService.sendCarNotification("Удален автомобиль", car.getBrand(), car.getYear());
        });
        carRepository.deleteById(id);
    }
}
