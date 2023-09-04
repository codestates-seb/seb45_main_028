package com.mainproject.be28.complain.mapper;

import com.mainproject.be28.complain.dto.ComplainPatchDto;
import com.mainproject.be28.complain.dto.ComplainPostDto;
import com.mainproject.be28.complain.dto.ComplainResponseDto;
import com.mainproject.be28.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.complain.entity.Complain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
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
    @Mapping(source = "member.name", target = "name")
    @Mapping(source = "item.name", target = "itemname")

    ComplainResponseDto complainToComplainResponseDto(Complain complain);

    Complain complainPatchDtoToComplain(ComplainPatchDto complainPatchDto);//ComplainPatchDto 객체를 기반으로 Complain 엔티티 객체로 변환하는 매핑

    public default List<ComplainResponsesDto> complainsToComplainResponsesDto(List<Complain> complains) {
        List<ComplainResponsesDto> responseDtos = new ArrayList<>();

        for (Complain complain : complains) {
            ComplainResponsesDto responseDto = new ComplainResponsesDto();
            responseDto.setComplainId(complain.getComplainId());
            responseDto.setItemId(complain.getItem().getItemId());
            responseDto.setMemberId(complain.getMember().getMemberId());
            responseDto.setName(complain.getMember().getName());
            responseDto.setItemname(complain.getItem().getName());

            responseDto.setTitle(complain.getTitle());

            responseDtos.add(responseDto);
        }

        return responseDtos;
    }
}
