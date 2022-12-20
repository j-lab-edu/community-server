package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.CommentsDTO;
import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.UserDTO;
import com.communityserver.exception.PostAccessDeniedException;
import com.communityserver.service.impl.PostServiceImpl;
import com.communityserver.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/post")
public class PostController {

    private final int AccessPermission = 1;
    private final PostServiceImpl postService;
    private final UserServiceImpl userService;
    private static final Logger logger = LogManager.getLogger(PostController.class);

    public PostController(PostServiceImpl postService,UserServiceImpl userService){
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/add")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    public PostDTO addPost(Integer userNumber, @RequestBody PostDTO postDTO) {
        return postService.addPost(postDTO, userNumber);
    }

    /***
     * 성능테스트를 위한 10만개 랜덤 게시글 추가
     */
//    @PostMapping("/add/random")
//    public PostDTO addRandomPost(Integer userNumber, @RequestBody PostDTO postDTO) {
//        List<FileDTO> fileDTOList = new ArrayList<>();
//        for(int i=0; i<100000; i++){
//            int leftLimit = 97; // letter 'a'
//            int rightLimit = 122; // letter 'z'
//            int targetStringLength = 10;
//            Random random = new Random();
//            String generatedString = random.ints(leftLimit, rightLimit + 1)
//                    .limit(targetStringLength)
//                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                    .toString();
//            String generatedString2 = random.ints(leftLimit, rightLimit + 1)
//                    .limit(targetStringLength)
//                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                    .toString();
//            int userNumber2 = (int)(Math.random() * 2 +1 );
//            postService.addPost(     PostDTO.builder()
//                    .categoryNumber( (int)(Math.random() * 6 + 1))
//                    .userNumber( userNumber2)
//                    .postName(generatedString)
//                    .contents(generatedString2)
//                    .fileDTOList(fileDTOList)
//                    .build(), userNumber2);
//        }
//         return null;
//    }
    @PostMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    public PostDTO updatePost(Integer userNumber, @PathVariable int postNumber, @RequestBody PostDTO postDTO){
        postDTO.setPostNumber(postNumber);
        postDTO.setUserNumber(userNumber);
        if(postService.checkHasPermission(postDTO) != AccessPermission) {
            logger.error("권한 부족");
            throw new PostAccessDeniedException("권한 부족");
        }
        else {
            postService.updatePost(postDTO, postNumber);
            return postService.selectPost(postNumber);
        }
    }
    @GetMapping("/{postNumber}")
    public PostDTO selectPost(@PathVariable int postNumber){
        PostDTO postMetaData = postService.selectPost(postNumber);
        postService.addViews(postNumber);
        return postMetaData;
    }
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @PutMapping("/{postNumber}")
    public PostDTO addPostComments(Integer loginUserNumber, @PathVariable int postNumber, @RequestBody CommentsDTO commentsDTO){
        commentsDTO.setPostNumber(postNumber);
        UserDTO userDTO = userService.selectUser(loginUserNumber);
        commentsDTO.setUserId(userDTO.getId());
        postService.addComments(postNumber, commentsDTO);
        return postService.selectPost(postNumber);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @DeleteMapping("/{postNumber}")
    public void deletePost(Integer userNumber, @PathVariable int postNumber){
        postService.deleteFile(postNumber);
        postService.deletePost(postNumber, userNumber);
    }
}
