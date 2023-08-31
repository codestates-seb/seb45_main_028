package com.mainproject.be28.complain.service;

import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.complain.repository.ComplainRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ComplainService {
    private final ComplainRepository complainRepository;
    private final CustomBeanUtils<Complain> beanUtils;

    public ComplainService(ComplainRepository complainRepository, CustomBeanUtils<Complain> beanUtils) {
        this.complainRepository = complainRepository;
        this.beanUtils = beanUtils;
    }

    //complain 객체를 인자로 받아와서 데이터베이스에 저장한 뒤, 저장된 엔티티 객체를 반환
    public Complain createComplain(Complain complain) {
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
        Complain findComplain = verifyExistComplain(complain.getComplainId());

        Complain updatedComplain =
                beanUtils.copyNonNullProperties(complain, findComplain);//complain 객체에서 변경된 부분만을 findComplain 엔티티에 복사
        return complainRepository.save(updatedComplain);

    }
    //complainId에 해당하는 Complain 엔티티가 데이터베이스에 존재하는지 확인
    public Complain verifyExistComplain(long complainId) {
        Optional<Complain> complain = complainRepository.findById(complainId);
        return complain.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Complain_NOT_FOUND));
    }
//complainId에 해당하는 엔티티를 데이터베이스에서 조회한 뒤 삭제
    public void deleteItem(long complainId) {
        Complain findComplain = findComplain(complainId);
        complainRepository.delete(findComplain);
    }
}
