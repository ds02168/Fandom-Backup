package com.example.NewJeans.controller;

import com.example.NewJeans.dto.response.CommentSaveResponseDTO;
import com.example.NewJeans.dto.request.CommentRequestDTO;
import com.example.NewJeans.dto.response.ListBoardResponseDTO;
import com.example.NewJeans.Entity.Comment;
import com.example.NewJeans.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/create/{board-id}")
    public int createComment(
            Model model,
            //@AuthenticationPrincipal Long memId,
            @PathVariable("board-id") Long boardId,
            @Validated @RequestBody CommentRequestDTO requestDTO,
            BindingResult result

    ) {
        if (result.hasErrors()) {
            log.warn("DTO 검증 에러 발생: {}", result.getFieldError());
            //model.addAttribute("error", "createComment 에러");
            return 0; //페이지 만들어서 보내
        }

        try {
            commentService.create(boardId,requestDTO); //memNickName 추가
            //model.addAttribute("commentRequestDTO", commentRequestDTO);
            return 1;

//            return ResponseEntity
//                    .ok()
//                    .body(boardDetailResponseDTO);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            //model.addAttribute("error", "createBoard 에러");
            return 0; //페이지 만들어서 보내
//            return ResponseEntity
//                    .internalServerError()
//                    .body(BoardListResponseDTO.builder().error(e.getMessage()));
        }
    }


    //댓글 보기  (댓글 창 클릭하면 댓글 보이게)
    @GetMapping("{idol-id}/{board-id}")
    public List<Comment> retrieveCommentList(
            Model model,
            @PathVariable("board-id") Long boardId
    )
    {
//        List<CommentResponseDTO> commentResponseDTO = commentService.retrieve(boardId);
//        model.addAttribute("CommentResponseDTO",commentResponseDTO);
        return commentService.retrieve(boardId);
    }

    //댓글 수정 댓글 쓴 작성자만 수정 가능
    @RequestMapping(
            value = "/{board-id}/comments/{comment-id}"
            , method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> updateComment(
            //@AuthenticationPrincipal Long memId,
            @PathVariable("board-id") Long boardId,
            @PathVariable("comment-id") Long cmtId,
            @Validated @RequestBody CommentRequestDTO requestDTO, BindingResult result, HttpServletRequest request
    )
    {
        commentService.update(cmtId,requestDTO);
        return null;

    }

    @RequestMapping(value = "/commentSave", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> commentSave(
            @Validated @RequestParam("boardId") Long boardId,
            @Validated @RequestParam("userEmail") String userEmail,
            @RequestParam("commentText") String commentText
    ){
        log.info("commentSave boardId is {}", boardId);
        log.info("commentSave userEmail is {}", userEmail);

        try {
            CommentSaveResponseDTO commentSaveResponseDTO = commentService.commentSave(boardId, userEmail, commentText);
            return ResponseEntity
                    .ok()
                    .body(commentSaveResponseDTO);
        }catch (Exception e){
            log.warn(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    //댓글 삭제   //댓글 쓴 작성자, 게시글 작성자 삭제 가능
    @DeleteMapping("/commentDelete")
    public @ResponseBody ResponseEntity<?> deleteComment(
            @Validated @RequestParam("commentId") Long commentId
    )
    {
        log.info("commentDelete commentId is {}", commentId);

        try {
            commentService.delete(commentId); //memId
            return ResponseEntity
                    .ok()
                    .body("delete Success");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(ListBoardResponseDTO.builder().error(e.getMessage()));
        }
    }
}