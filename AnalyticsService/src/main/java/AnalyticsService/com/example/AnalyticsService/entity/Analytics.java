package AnalyticsService.com.example.AnalyticsService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "analytics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Analytics {

    @Id
    private Long id;

    private Long totalUsers;
}
