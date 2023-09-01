package com.mainproject.be28.board.repository;
import java.util.List;
import com.mainproject.be28.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContaining(String keyword);
}
