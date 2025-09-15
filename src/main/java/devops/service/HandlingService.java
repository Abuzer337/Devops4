package devops.service;

import devops.model.Handling;
import devops.repository.HandlingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@AllArgsConstructor
public class HandlingService {

    private static final Logger log = LoggerFactory.getLogger(HandlingService.class);
    
    private HandlingRepository repository;
    
    @Autowired
    private TelegramNotificationService telegramService;

    public List<Handling> getListHandlingByCarId(Long carId){
        return repository.getHandlingListByCarId(carId);
    }

    public List<Handling> getListHandling() {
        return repository.findAll();
    }

    public Handling getHandlingById(Long id){
        return repository.getById(id);
    }

    public Handling addHandling(Handling handling) {
        log.info("Adding handling: {}", handling);
        Handling savedHandling = repository.save(handling);
        log.info("Saved handling: {}", savedHandling);
        
        // Отправляем уведомление в Telegram
        String carBrand = "Неизвестная марка";
        if (savedHandling.getCar() != null) {
            log.info("Car object: {}", savedHandling.getCar());
            if (savedHandling.getCar().getBrand() != null && !savedHandling.getCar().getBrand().isEmpty()) {
                carBrand = savedHandling.getCar().getBrand();
            } else {
                log.warn("Car brand is null or empty for car: {}", savedHandling.getCar());
            }
        } else {
            log.warn("Car object is null for handling: {}", savedHandling);
        }
        log.info("Sending Telegram notification for handling: carBrand={}, type={}", carBrand, savedHandling.getType());
        telegramService.sendHandlingNotification("Добавлено обслуживание", carBrand, savedHandling.getType());
        return savedHandling;
    }

    public void removeHandling(Long handlingId) {
        repository.deleteById(handlingId);
    }

    public Handling updateHandling(Handling handling,Long destId) {
        var dest = repository.getById(destId);
        dest.setCar(handling.getCar());
        dest.setDate(handling.getDate());
        dest.setCost(handling.getCost());
        dest.setType(handling.getType());
        Handling updatedHandling = repository.save(dest);
        // Отправляем уведомление в Telegram
        String carBrand = handling.getCar() != null ? handling.getCar().getBrand() : "Неизвестная марка";
        telegramService.sendHandlingNotification("Обновлено обслуживание", carBrand, handling.getType());
        return updatedHandling;
    }
}
