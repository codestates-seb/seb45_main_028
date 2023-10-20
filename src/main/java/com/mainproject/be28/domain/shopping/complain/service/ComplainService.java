package com.mainproject.be28.domain.shopping.complain.service;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPatchDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPostDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponsesDto;
import org.springframework.data.domain.Page;


public interface ComplainService  {

    //complain 객체를 인자로 받아와서 데이터베이스에 저장한 뒤, 저장된 엔티티 객체를 반환
    ComplainResponseDto createComplain(ComplainPostDto complainPostDto, Member member);


    //complainId를 사용하여 엔티티를 조회하고, 조회 결과가 없으면 예외를 발생
     ComplainResponseDto findComplain(Long complainId);

    //complain 객체를 기반으로 엔티티 정보를 업데이트하고, 업데이트된 엔티티를 데이터베이스에 저장하여 반환
   ComplainResponseDto updateComplain(ComplainPatchDto complain);

//complainId에 해당하는 엔티티를 데이터베이스에서 조회한 뒤 삭제
    void deleteComplain(long complainId);
    Page<ComplainResponsesDto> findComplains(int page, int size);

   Page<ComplainResponsesDto> findComplainsByMember(String name, int page, int size);
}

