package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.DeliveryGuyRating;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryGuyRatingRepo extends JpaRepository<DeliveryGuyRating , Long> {
}
