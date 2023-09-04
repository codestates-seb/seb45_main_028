package com.mainproject.be28.review.service;

import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.review.dto.ReviewPostDto;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.review.repository.ReviewRepository;
import com.mainproject.be28.utils.CustomBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomBeanUtils<Review> beanUtils;


    public  Review createReview(Review review){
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        Review findReview = findReview(review.getReviewId());

        Review updatedReview =
                beanUtils.copyNonNullProperties(review, findReview);//complain 객체에서 변경된 부분만을 findComplain 엔티티에 복사
        return reviewRepository.save(updatedReview);

    }

    public Review findReview(long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Review_NOT_FOUND));
    }
    public void deleteReview(long reviewId){
        Review findReview = findReview(reviewId);
        reviewRepository.delete(findReview);
    }

}
