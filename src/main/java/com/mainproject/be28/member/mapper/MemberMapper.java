package com.mainproject.be28.member.mapper;

import com.mainproject.be28.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.member.dto.MemberPatchDto;
import com.mainproject.be28.member.dto.MemberPostDto;
import com.mainproject.be28.member.dto.MemberResponseDto;
import com.mainproject.be28.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface MemberMapper {

    default Member memberPostToMember(MemberPostDto memberPostDto){
        Member member = new Member();
        member.setMemberId(memberPostDto.getMemberId());
        member.setPassword(memberPostDto.getPassword());
        member.setEmail(memberPostDto.getEmail());
        member.setName(memberPostDto.getName());
        member.setPhone(memberPostDto.getPhone());
        member.setAddress(memberPostDto.getAddress());

        return member;
    }

    default Member memberPatchToMember(MemberPatchDto memberPatchDto){
            if ( memberPatchDto == null ) {
                return null;
            }
            Member member = new Member();
            member.setPhone( memberPatchDto.getPhone() );
            member.setAddress( memberPatchDto.getAddress() );

            return member;
        }

    default MemberResponseDto memberToMemberResponse(Member member){
        MemberResponseDto response = new MemberResponseDto(
                member.getEmail(),
                member.getName(),
                member.getPhone(),
                member.getAddress(),
                member.getStamp()
        );
        return response;
    }

}
