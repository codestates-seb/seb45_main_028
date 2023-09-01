package com.mainproject.be28.complain.mapper;

import com.mainproject.be28.complain.dto.ComplainDto;
import com.mainproject.be28.complain.dto.ComplainPostDto;
import com.mainproject.be28.complain.dto.ComplainResponseDto;
import com.mainproject.be28.complain.entity.Complain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ComplainMapper {
    //ComplainPostDto 객체를 Complain 엔티티로 변환
    @Mapping(source = "itemId", target = "item.itemId") //itemId 필드 값을 가져와서, Complain 객체의 item 필드의 itemId로 매핑
    @Mapping(source = "memberId", target = "member.memberId")
    Complain complainPostDtoToComplain(ComplainPostDto complainPostDto);

   // Complain 엔티티를 ComplainResponseDto 객체로 변환할 때 필요한 매핑 정보를 제공
    @Mapping(source = "item.itemId", target ="itemId") // Complain 엔티티의 item 필드의 itemId 값을 ComplainResponseDto 객체의 itemId 필드에 복사
    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "complainId", target = "complainId")
    ComplainResponseDto complainToComplainResponseDto(Complain complain);

    Complain complainPatchDtoToComplain(ComplainDto.Patch complainPatchDto);//ComplainDto.Patch 객체를 기반으로 Complain 엔티티 객체로 변환하는 매핑
    List<ComplainResponseDto> complainsToComplainResponseDtos(List<Complain> questions);
}
