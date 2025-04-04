package com.carsmoviesinventory.app.Repositories;

import com.carsmoviesinventory.app.Entities.FloresEntities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FloresRepository extends JpaRepository<FloresEntities, UUID>{

    Page<FloresEntities> findAllByFloresNameContaining(String FloresName, Pageable pageable);

    @Override
    Page<FloresEntities> findAll(Pageable pageable);
}