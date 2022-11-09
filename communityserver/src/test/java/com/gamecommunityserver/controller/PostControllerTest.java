package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.CommentsDTO;
import com.gamecommunityserver.dto.FileDTO;
import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.mapper.FileMapper;
import com.gamecommunityserver.mapper.PostMapper;
import com.gamecommunityserver.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.BDDMockito.given;

public class PostControllerTest {
    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostMapper postMapper;
    @Mock
    private FileMapper fileMapper;

    private final int testCategoryNumber = 1;
    private final int testUserNumber = 1;
    private final int testAdminPost = 0;

    public PostDTO generateTestPost(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = new PostDTO();
        postDTO.setCategoryNumber(testCategoryNumber);
        postDTO.setUserNumber(testUserNumber);
        postDTO.setPostName("testPostName");
        postDTO.setAdminPost(testAdminPost);
        postDTO.setContents("testContents");
        postDTO.setCreateTime(new Date());
        postDTO.setSuggestionCount(0);
        postDTO.setViews(0);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setPostNumber(postDTO.getPostNumber());
        fileDTO.setPath("D:/");
        fileDTO.setFileName("testFileName");
        fileDTO.setExtension("test");
        List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
        fileDTOList.add(fileDTO);
        postDTO.setFileDTOList(fileDTOList);
        return postDTO;
    }
    public CommentsDTO generateTestComments(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setPostNumber(1);
        commentsDTO.setContents("testCommentsContents");
        commentsDTO.setUserId(1);
        commentsDTO.setCreateTime(new Date());
        return commentsDTO;
    }

    @Test
    @DisplayName("게시글 추가 성공 테스트")
    public void addPostTest(){
        PostDTO postDTO = generateTestPost();
        postService.addPost(postDTO,postDTO.getUserNumber());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updatePostTest(){
        PostDTO postDTO = generateTestPost();
        postDTO.setPostName("updatePostNameTest");
        postDTO.setContents("updatePostContentsTest");
        postService.updatePost(postDTO, postDTO.getPostNumber());
    }

    @Test
    @DisplayName("게시글 정보 확인 테스트")
    public void selectPostTest(){
        PostDTO postDTO = generateTestPost();
        given(postMapper.selectPost(postDTO.getPostNumber())).willReturn(postDTO);
//        assertThat(postService.selectPost(postDTO.getPostNumber()).equals(postDTO));
    }

    @Test
    @DisplayName("게시글 댓글 추가 테스트")
    public void addCommentsTest(){
        PostDTO postDTO = generateTestPost();
        CommentsDTO commentsDTO = generateTestComments();
        postService.addComments(postDTO.getPostNumber(), commentsDTO);
    }
    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deletePostTest(){
        PostDTO postDTO = generateTestPost();
//        given().willReturn();
        postMapper.deletePost(postDTO.getPostNumber(), postDTO.getUserNumber());
    }


}
