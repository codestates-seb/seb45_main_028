package com.mainproject.be28.complain.mapper;

import com.mainproject.be28.complain.dto.ComplainDto;
import com.mainproject.be28.complain.dto.ComplainPostDto;
import com.mainproject.be28.complain.dto.ComplainResponseDto;
import com.mainproject.be28.complain.entity.Complain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface ComplainMapper {
    @Mapping(source = "itemId", target = "item.itemId")
    @Mapping(source = "memberId", target = "member.memberId")
    Complain complainPostDtoToComplain(ComplainPostDto complainPostDto);

    @Mapping(source = "item.itemId", target ="itemId")
    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "complainId", target = "complainId")
    ComplainResponseDto complainToComplainResponseDto(Complain complain);


    Complain complainPatchDtoToComplain(ComplainDto.Patch complainPatchDto);
}
