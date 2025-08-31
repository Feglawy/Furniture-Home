package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
