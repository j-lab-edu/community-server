package com.communityserver.service.impl;

import com.communityserver.dto.PostDTO;
import com.communityserver.mapper.PostSearchMapper;
import com.communityserver.service.PostSearchService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    public PostSearchServiceImpl(PostSearchMapper postSearchMapper){
        this.postSearchMapper = postSearchMapper;
    }

    @Cacheable(value = "post", key = "#postDTO")
    @Override
    public List<PostDTO> resultSearchPost(PostDTO postDTO){
        List<PostDTO> postDTOList = postSearchMapper.resultSearchPost(postDTO);
        return postDTOList;
    }
}