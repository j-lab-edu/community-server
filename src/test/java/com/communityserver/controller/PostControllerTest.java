package com.communityserver.controller;

import com.communityserver.dto.CommentsDTO;
import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.exception.PermissionDeniedException;
import com.communityserver.mapper.FileMapper;
import com.communityserver.mapper.PostMapper;
import com.communityserver.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PostControllerTest {
    @InjectMocks
    private PostServiceImpl postService;
    private final PostMapper postMapper = mock(PostMapper.class);
    private final FileMapper fileMapper = mock(FileMapper.class);

    private final int testCategoryNumber = 1;
    private final int testUserFailNumber = 9999;
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
        commentsDTO.setUserId("testUserId");
        commentsDTO.setCreateTime(new Date());
        return commentsDTO;
    }

    @Test
    @DisplayName("게시글 추가 성공 테스트")
    public void addPostTest(){
        PostDTO postDTO = generateTestPost();
        assertEquals(postService.addPost(postDTO,postDTO.getUserNumber()), postDTO);
    }
    @Test
    @DisplayName("게시글 추가 실패 테스트")
    public void addPostFailTest(){
        PostDTO postDTO = generateTestPost();
        assertThrows(PermissionDeniedException.class, ()-> {
            postService.addPost(postDTO, testUserFailNumber);
        });
    }

    @Test
    @DisplayName("게시글 정보 확인 테스트")
    public void selectPostTest(){
        PostDTO postDTO = generateTestPost();
        given(postMapper.selectPost(postDTO.getPostNumber())).willReturn(postDTO);
        assertEquals(postService.selectPost(postDTO.getPostNumber()), postDTO);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updatePostTest(){
        PostDTO postDTO = generateTestPost();
        postDTO.setPostName("updatePostNameTest");
        postDTO.setContents("updatePostContentsTest");
        postService.updatePost(postDTO, postDTO.getPostNumber());
        given(postMapper.selectPost(postDTO.getPostNumber())).willReturn(postDTO);
        assertEquals(postService.selectPost(postDTO.getPostNumber()), postDTO);
    }

    @Test
    @DisplayName("게시글 댓글 추가 테스트")
    public void addCommentsTest(){
        PostDTO postDTO = generateTestPost();
        CommentsDTO commentsDTO = generateTestComments();
        given(postMapper.addComments(commentsDTO)).willReturn(postDTO);
        PostDTO resultPostDTO = postService.addComments(postDTO.getPostNumber(), commentsDTO);
        assertEquals(resultPostDTO.getPostNumber(), commentsDTO.getPostNumber());
    }
    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deletePostTest(){
        PostDTO postDTO = generateTestPost();
        postService.deletePost(postDTO.getPostNumber(), postDTO.getUserNumber());
        assertEquals(postService.selectPost(postDTO.getPostNumber()), null);
    }
}
