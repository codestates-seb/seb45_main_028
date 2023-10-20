package com.mainproject.be28.domain.member.service.Layer1;


import com.mainproject.be28.domain.member.dto.MemberPatchDto;
import com.mainproject.be28.domain.member.dto.MemberPostDto;
import com.mainproject.be28.domain.member.dto.MemberResponseDto;
import com.mainproject.be28.domain.member.dto.PasswordPatchDto;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.mapper.MemberMapper;
import com.mainproject.be28.domain.member.service.Layer2.GetMineService;
import com.mainproject.be28.domain.member.service.Layer2.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final ProfileService profileService;
    private final MemberMapper mapper;
    private final GetMineService getMineService;
    public MemberServiceImpl(ProfileService profileService, MemberMapper mapper, GetMineService getMineService) {
        this.profileService = profileService;
        this.mapper = mapper;
        this.getMineService = getMineService;
    }


    //회원생성
    public MemberResponseDto createMember(MemberPostDto memberPostDto) {
        Member member = mapper.memberPostToMember(memberPostDto);
        Member created = profileService.createMember(member);
        return mapper.memberToMemberResponse(created);
    }
    public void createMember(Member member) {
        profileService.createMember(member);
    }


    public MemberResponseDto updateProfile(MemberPatchDto requestBody) {  // 주소, 핸드폰번호 변경.
        Member member = mapper.memberPatchToMember(requestBody);
        Member response = profileService.updateProfile(member,requestBody.getEmail(), requestBody.getPassword());
        return mapper.memberToMemberResponse(response);
    }

    public void changePassword(PasswordPatchDto passwordPatchDto) {
        profileService.changePassword(passwordPatchDto);
    }

    //회원 탈퇴
    public void deleteMember(String email, String password){
        profileService.deleteMember(email,password);
    }

    @Override
    public MemberResponseDto getProfile() {
       return mapper.memberToMemberResponse(profileService.getProfile());
    }

    @Override
    public <T>Page<T> getMine(int page, int size, Class<T> condition) {
        return getMineService.getMine(page, size, condition);
    }
//
//    @Override
//    public Page<ReviewResponseDto> getMyReviews(int page, int size) {
//        ReviewResponseDto responseDto = new ReviewResponseDto();
//
//        return (Page<ReviewResponseDto>) getMineService.getMine(page, size, responseDto.getClass());
//    }
//
//    @Override
//    public Page<CommentResponseDto> getMyComments(int page, int size) {
//        return (Page<CommentResponseDto>) getMineService.getMine(page, size, CommentResponseDto.class);
//    }
//
//    @Override
//    public Page<ComplainResponsesDto> getMyComplains(int page, int size) {
//        return (Page<ComplainResponsesDto>) getMineService.getMine(page, size, ComplainResponsesDto.class);
//    }

}