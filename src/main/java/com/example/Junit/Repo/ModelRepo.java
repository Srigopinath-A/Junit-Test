package com.example.Junit.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Junit.Model.Movies;

@Repository
public interface ModelRepo extends JpaRepository<Movies, Long>{

}
