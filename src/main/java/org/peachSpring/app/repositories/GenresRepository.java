package org.peachSpring.app.repositories;

import org.peachSpring.app.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Integer> {
}
