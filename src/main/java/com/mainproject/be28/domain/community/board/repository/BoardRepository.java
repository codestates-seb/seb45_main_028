package com.mainproject.be28.domain.community.board.repository;
import java.util.List;
import com.mainproject.be28.domain.community.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContaining(String keyword);
    List<Board> findByMember_MemberId(Long memberId); // 회원아이디로 게시글검색 추가

    List<Board> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword); // 키워드로찾기 추가

    List<Board> findAllByOrderByLikeCountDesc(); // 좋아요
    List<Board> findAllByOrderByViewCountDesc(); // 조회수
//공지사항
    List<Board> findByBoardCategoryOrderByCreatedAtDesc(String boardCategory);
}