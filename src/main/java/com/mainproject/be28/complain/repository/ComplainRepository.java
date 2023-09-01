package com.mainproject.be28.complain.repository;

import com.mainproject.be28.complain.entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long> {

}
