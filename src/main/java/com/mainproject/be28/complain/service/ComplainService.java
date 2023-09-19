package com.mainproject.be28.complain.service;

import com.mainproject.be28.complain.dto.ComplainPostDto;
import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.complain.mapper.ComplainMapper;
import com.mainproject.be28.complain.repository.ComplainRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ComplainService {
    private final ComplainRepository complainRepository;
    private final CustomBeanUtils<Complain> beanUtils;
    private final MemberService memberService;
    private final ItemService itemService;
    private final ComplainMapper mapper;

    public ComplainService(ComplainRepository complainRepository, CustomBeanUtils<Complain> beanUtils, MemberService memberService, ItemService itemService, ComplainMapper mapper) {
        this.complainRepository = complainRepository;
        this.beanUtils = beanUtils;
        this.memberService = memberService;
        this.itemService = itemService;
        this.mapper = mapper;
    }

    //complain 객체를 인자로 받아와서 데이터베이스에 저장한 뒤, 저장된 엔티티 객체를 반환
    public Complain createComplain(ComplainPostDto complainPostDto) {

        Complain complain = mapper.complainPostDtoToComplain(complainPostDto);
        Member member = memberService.findTokenMember();
        Item item = itemService.findItem(complainPostDto.getItemId());

        complain.setMember(member);
        complain.setItem(item);

        return complainRepository.save(complain);
    }


    //complainId를 사용하여 엔티티를 조회하고, 조회 결과가 없으면 예외를 발생
    public Complain findComplain(Long complainId){
        Optional<Complain> optionalComplain
                = complainRepository.findById(complainId);
        Complain findComplain =optionalComplain.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Complain_NOT_FOUND));

        return findComplain;
}

    //complain 객체를 기반으로 엔티티 정보를 업데이트하고, 업데이트된 엔티티를 데이터베이스에 저장하여 반환
    public Complain updateComplain(Complain complain) {
        Complain findComplain = findComplain(complain.getComplainId());

        Complain updatedComplain =
                beanUtils.copyNonNullProperties(complain, findComplain);//complain 객체에서 변경된 부분만을 findComplain 엔티티에 복사
        return complainRepository.save(updatedComplain);

    }

//complainId에 해당하는 엔티티를 데이터베이스에서 조회한 뒤 삭제
    public void deleteComplain(long complainId) {
        Complain findComplain = findComplain(complainId);
        complainRepository.delete(findComplain);
    }

    public Page<Complain> findComplains(int page, int size) {
        Page<Complain> findAllComplain = complainRepository.findAllByComplainStatus(
                PageRequest.of(page,size),
                Complain.ComplainStatus.COMPLAIN_EXIST);


        VerifiedNoComplain(findAllComplain);

        return findAllComplain;

    }
    private void VerifiedNoComplain(Page<Complain> findAllComplain){
        if(findAllComplain.getTotalElements()==0){
            throw new BusinessLogicException(ExceptionCode.Complain_NOT_FOUND);
        }
    }
    }

