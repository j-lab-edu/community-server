package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.mapper.PostSearchMapper;
import com.gamecommunityserver.service.impl.PostSearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PostSearchControllerTest {

    @InjectMocks
    private PostSearchServiceImpl postSearchService;
    @Mock
    private PostSearchMapper postSearchMapper;
    private final int testCategoryNumber = 1;
    private final int testUserNumber = 1;

    public PostDTO generateTestPostSearch(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = new PostDTO();
        postDTO.setCategoryNumber(testCategoryNumber);
        postDTO.setUserNumber(testUserNumber);
        postDTO.setPostName("testPostName");
        postDTO.setContents("testContents");
        return postDTO;
    }

    @Test
    @DisplayName("게시글 검색 테스트")
    public void searchPostTest(){
        PostDTO postDTO = generateTestPostSearch();
        postSearchService.getSearchPost(postDTO);
    }
}
