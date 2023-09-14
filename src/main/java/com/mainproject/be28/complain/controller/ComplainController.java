package com.mainproject.be28.complain.controller;

import com.mainproject.be28.complain.dto.ComplainPatchDto;
import com.mainproject.be28.complain.dto.ComplainPostDto;
import com.mainproject.be28.complain.dto.ComplainResponseDto;
import com.mainproject.be28.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.complain.mapper.ComplainMapper;
import com.mainproject.be28.complain.service.ComplainService;
import com.mainproject.be28.response.MultiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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

        complainService.createComplain(mapper.complainPostDtoToComplain(complainPostDto));
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @GetMapping ("{complain-id}") // 문의사항 상세보기
    public ResponseEntity<ComplainResponseDto> getComplain(@PathVariable("complain-id") @Positive long complainId) {
        Complain response = complainService.findComplain(complainId);
        ComplainResponseDto complainResponse = mapper.complainToComplainResponseDto(response);
        return new ResponseEntity<>(complainResponse, HttpStatus.OK);
    }

    @GetMapping("") //문의사항 목록보기
    public ResponseEntity getComplains(@RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size
    ){
        page = Math.max(page - 1, 0); // 페이지가 0이 되지 않도록
        Page<Complain> pageComplains = complainService.findComplains(page,size);

        List<Complain> complains = pageComplains.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(
                mapper.complainsToComplainResponsesDto(complains),
                pageComplains),HttpStatus.OK);

    }


    @PatchMapping("/{complain-id}")//문의사항 수정하기
    public ResponseEntity patchComplain(@PathVariable("complain-id") @Positive long complainId,
                                    @Valid @RequestBody ComplainPatchDto complainPatchDto){

        complainPatchDto.setComplainId(complainId);

        Complain complain = mapper.complainPatchDtoToComplain(complainPatchDto);

        Complain response = complainService.updateComplain(complain);

        return new ResponseEntity<>(mapper.complainToComplainResponseDto(response), HttpStatus.OK);
    }

    @DeleteMapping("/{complain-id}")//문의사항 삭제
    public ResponseEntity deleteComplain(@PathVariable("complain-id") @Positive long complainId){

        complainService.deleteComplain(complainId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }


