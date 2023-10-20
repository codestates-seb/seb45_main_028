package com.mainproject.be28.domain.shopping.complain.controller;

import com.mainproject.be28.domain.shopping.complain.dto.ComplainPatchDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPostDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.domain.shopping.service.ShoppingService;
import com.mainproject.be28.global.response.MultiResponseDto;
import com.mainproject.be28.global.response.SingleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/complain")
public class ComplainController {

    private final ShoppingService shoppingService;
    private  final HttpStatus ok = HttpStatus.OK;

    public ComplainController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping ("/new") // 문의사항등록
    public ResponseEntity<SingleResponseDto<ComplainResponseDto>> postComplain(@RequestBody @Valid ComplainPostDto complainPostDto) {

        ComplainResponseDto response = shoppingService.createComplain(complainPostDto);
        SingleResponseDto<ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }

    @GetMapping ("{complain-id}") // 문의사항 상세보기
    public ResponseEntity<SingleResponseDto<ComplainResponseDto>> getComplain(@PathVariable("complain-id") @Positive long complainId) {
        ComplainResponseDto response = shoppingService.findComplain(complainId);
        SingleResponseDto<ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }

    @GetMapping("") //문의사항 목록보기
    public ResponseEntity<MultiResponseDto<ComplainResponsesDto>> getComplains(@RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size
    ){
        page = Math.max(page - 1, 0); // 페이지가 0이 되지 않도록
        Page<ComplainResponsesDto> pageComplains = shoppingService.findComplains(page,size);
        MultiResponseDto<ComplainResponsesDto> response = new MultiResponseDto<>(pageComplains, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PatchMapping("/{complain-id}")//문의사항 수정하기
    public ResponseEntity<SingleResponseDto<ComplainResponseDto> > patchComplain(@PathVariable("complain-id") @Positive long complainId,
                                    @Valid @RequestBody ComplainPatchDto complainPatchDto){

        complainPatchDto.setComplainId(complainId);


        ComplainResponseDto response = shoppingService.updateComplain(complainPatchDto);

        SingleResponseDto<ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }

    @DeleteMapping("/{complain-id}")//문의사항 삭제
    public ResponseEntity<HttpStatus> deleteComplain(@PathVariable("complain-id") @Positive long complainId){

        shoppingService.deleteComplain(complainId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }


