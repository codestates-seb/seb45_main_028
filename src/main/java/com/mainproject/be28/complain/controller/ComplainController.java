package com.mainproject.be28.complain.controller;

import com.mainproject.be28.complain.dto.ComplainDto;
import com.mainproject.be28.complain.dto.ComplainPostDto;
import com.mainproject.be28.complain.dto.ComplainResponseDto;
import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.complain.mapper.ComplainMapper;
import com.mainproject.be28.complain.service.ComplainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/complain")
public class ComplainController {
    private final ComplainService complainService;
    private final ComplainMapper mapper;

    public ComplainController(ComplainService complainService, ComplainMapper mapper) {
        this.complainService = complainService;
        this.mapper = mapper;
    }

    @PostMapping ("/new") // 문의사항등록
    public ResponseEntity postComplain(@RequestBody @Valid ComplainPostDto complainPostDto) {
        Long ItemId = complainPostDto.getItemId();
        Long memberId = complainPostDto.getMemberId();

        complainService.createComplain(mapper.complainPostDtoToComplain(complainPostDto));
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @GetMapping ("{complain-id}") // 문의사항 상세보기
    public ResponseEntity<ComplainResponseDto> getComplain(@PathVariable("complain-id") @Positive long complainId) {
        Complain response = complainService.findComplain(complainId);
        ComplainResponseDto complainResponse = mapper.complainToComplainResponseDto(response);
        return new ResponseEntity<>(complainResponse, HttpStatus.OK);
    }


    @PatchMapping("/{complain-id}")//문의사항 수정하기
    public ResponseEntity patchComplain(@PathVariable("complain-id") @Positive long complainId,
                                    @Valid @RequestBody ComplainDto.Patch requestBody){

        requestBody.setComplainId(complainId);

        Complain complain = mapper.complainPatchDtoToComplain(requestBody);

        Complain response = complainService.updateComplain(complain);

        return new ResponseEntity<>(mapper.complainToComplainResponseDto(response), HttpStatus.OK);
    }
    @DeleteMapping("/{complain-id}")//문의사항 삭제
    public ResponseEntity deleteComplain(@PathVariable("complain-id") @Positive long complainId){

        complainService.deleteItem(complainId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }


