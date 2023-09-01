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

    public Complain createComplain(Complain complain) {
        return complainRepository.save(complain);
    }

    public Complain findComplain(Long complainId){
        Optional<Complain> optionalComplain
                = complainRepository.findById(complainId);
        Complain findComplain =optionalComplain.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Complain_NOT_FOUND));

        return findComplain;
}


    public Complain updateComplain(Complain complain) {
        Complain findComplain = verifyExistComplain(complain.getComplainId());

        Complain updatedComplain =
                beanUtils.copyNonNullProperties(complain, findComplain);
        return complainRepository.save(updatedComplain);

    }
    public Complain verifyExistComplain(long complainId) {
        Optional<Complain> complain = complainRepository.findById(complainId);
        return complain.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Complain_NOT_FOUND));
    }

    public void deleteItem(long complainId) {
        Complain findComplain = findComplain(complainId);
        complainRepository.delete(findComplain);
    }
}
