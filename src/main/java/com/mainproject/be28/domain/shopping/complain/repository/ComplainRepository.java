package com.mainproject.be28.domain.shopping.complain.repository;

import com.mainproject.be28.domain.shopping.complain.entity.Complain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long> {
    Page<Complain> findAllByComplainStatus(Pageable pageable, Complain.ComplainStatus complainStatus);

    List<Complain> findAllByMember_MemberId(long memberId);
}








