package com.mainproject.be28.domain.shopping.complain.service;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPatchDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPostDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.domain.shopping.complain.entity.Complain;
import com.mainproject.be28.domain.shopping.complain.mapper.ComplainMapper;
import com.mainproject.be28.domain.shopping.complain.repository.ComplainRepository;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.service.ItemService;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.global.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComplainServiceImpl implements ComplainService{
    private final ComplainRepository complainRepository;
    private final ItemService itemService;
    private final ComplainMapper mapper;
    public ComplainServiceImpl (ComplainRepository complainRepository,ItemService itemService, ComplainMapper mapper) {
        this.complainRepository = complainRepository;
        this.itemService = itemService;
        this.mapper = mapper;
    }


    @Override
    public ComplainResponseDto createComplain(ComplainPostDto complainPostDto,Member member) {

        Complain complain = mapper.complainPostDtoToComplain(complainPostDto);
        Item item = itemService.verifyExistItem(complainPostDto.getItemId());

        complain.setMember(member);
        complain.setItem(item);

        return mapper.complainToComplainResponseDto(complainRepository.save(complain));
    }

    @Override
    public ComplainResponseDto findComplain(Long complainId) {
        Optional<Complain> optionalComplain
                = complainRepository.findById(complainId);

        return mapper.complainToComplainResponseDto(optionalComplain.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Complain_NOT_FOUND)));
    }

    private Complain verifyExistComplain(Long complainId) {
        Optional<Complain> optionalComplain
                = complainRepository.findById(complainId);

        return optionalComplain.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Complain_NOT_FOUND));
    }

    @Override
    public ComplainResponseDto updateComplain(ComplainPatchDto complainPatchDto) {
        Complain findComplain = verifyExistComplain(complainPatchDto.getComplainId());
        Complain complain = mapper.complainPatchDtoToComplain(complainPatchDto);
        CustomBeanUtils<Complain> beanUtils = new CustomBeanUtils<>();
        Complain updatedComplain =
                beanUtils.copyNonNullProperties(complain, findComplain);//complain 객체에서 변경된 부분만을 findComplain 엔티티에 복사
        return mapper.complainToComplainResponseDto(complainRepository.save(updatedComplain));
    }

    @Override
    public void deleteComplain(long complainId) {
        Complain findComplain = verifyExistComplain(complainId);
        complainRepository.delete(findComplain);
    }

    @Override
    public Page<ComplainResponsesDto> findComplains(int page, int size) {
        List<Complain> findAllComplain = complainRepository.findAllByComplainStatus(
                PageRequest.of(page,size),
                Complain.ComplainStatus.COMPLAIN_EXIST);

        List<ComplainResponsesDto> complains=mapper.complainsToComplainResponsesDto(findAllComplain);
        VerifiedNoComplain(complains);
        return new PageImpl<>(complains, PageRequest.of(page,size), complains.size());
    }

    @Override
    public Page<ComplainResponsesDto> findComplainsByMember(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Complain> myComplains = complainRepository.findAllByMember_Name(name);
        List<ComplainResponsesDto> myComplainsDto = mapper.complainsToComplainResponsesDto(myComplains);

        return new PageImpl<>(myComplainsDto, pageRequest, myComplainsDto.size());
    }
    private void VerifiedNoComplain(List<ComplainResponsesDto> complains){
        if(complains.size()==0){
            throw new BusinessLogicException(ExceptionCode.Complain_NOT_FOUND);
        }
    }
}
