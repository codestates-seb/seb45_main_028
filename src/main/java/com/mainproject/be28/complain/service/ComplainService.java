package com.mainproject.be28.complain.service;

import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.complain.mapper.ComplainMapper;
import com.mainproject.be28.complain.repository.ComplainRepository;
import com.mainproject.be28.item.entity.Item;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ComplainService {
    private final ComplainRepository complainRepository;


    public ComplainService(ComplainRepository complainRepository) {
        this.complainRepository = complainRepository;

    }

    public static void createComplain(Complain complain, Long ItemId) {

        Long memberId = complain.getMember().getMemberId();
        Long itemId = complain.getItem().getItemId();

        Item targetItem = verifyExistsItem(itemId); //item이 존재하는지
        setComplainWriting(targetItem);

        complainRepository.save(complain);
    }


}

