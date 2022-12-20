package com.communityserver.controller;

import com.communityserver.dto.PostDTO;
import com.communityserver.service.impl.PostSearchServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    public PostSearchController(PostSearchServiceImpl postSearchService) {
        this.postSearchService = postSearchService;
    }

    private static final Logger logger = LogManager.getLogger(PostSearchController.class);
    @GetMapping
    public List<PostDTO> search(@RequestBody PostDTO postDTO) {
        List<PostDTO> postDTOList = postSearchService.resultSearchPost(postDTO);
        return postDTOList;
    }
}
