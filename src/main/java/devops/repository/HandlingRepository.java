package devops.repository;

import devops.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandlingRepository extends JpaRepository<Handling, Long> {

    @Query("select handling from Handling handling where handling.car.id = :carId")
    List<Handling> getHandlingListByCarId(@Param("carId")Long carId);
}
