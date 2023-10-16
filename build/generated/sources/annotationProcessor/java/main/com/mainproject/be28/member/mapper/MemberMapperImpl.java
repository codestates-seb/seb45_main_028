package com.mainproject.be28.member.mapper;

import com.mainproject.be28.member.dto.MemberPatchDto;
import com.mainproject.be28.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-01T16:03:39+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPatchToMember(MemberPatchDto memberPatchDto) {
        if ( memberPatchDto == null ) {
            return null;
        }

        Member member = new Member();

        if ( memberPatchDto.getMemberId() != null ) {
            member.setMemberId( Long.parseLong( memberPatchDto.getMemberId() ) );
        }
        member.setPassword( memberPatchDto.getPassword() );
        member.setPhone( memberPatchDto.getPhone() );
        member.setAddress( memberPatchDto.getAddress() );

        return member;
    }
}
