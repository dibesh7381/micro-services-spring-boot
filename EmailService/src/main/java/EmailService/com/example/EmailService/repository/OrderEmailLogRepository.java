package EmailService.com.example.EmailService.repository;

import EmailService.com.example.EmailService.entity.OrderEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEmailLogRepository extends JpaRepository<OrderEmailLog, Long> {
}