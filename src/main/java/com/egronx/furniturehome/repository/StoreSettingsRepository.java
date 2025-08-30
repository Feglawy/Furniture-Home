package com.egronx.furniturehome.repository;

import com.egronx.furniturehome.entity.StoreSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreSettingsRepository extends JpaRepository<StoreSettings, Long> {
}
