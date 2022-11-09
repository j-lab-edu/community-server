package com.gamecommunityserver.service.impl;

import com.gamecommunityserver.dto.CommentsDTO;
import com.gamecommunityserver.dto.FileDTO;
import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.exception.PermissionDeniedException;
import com.gamecommunityserver.mapper.FileMapper;
import com.gamecommunityserver.mapper.PostMapper;
import com.gamecommunityserver.mapper.UserInfoMapper;
import com.gamecommunityserver.service.PostService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final int AdminPost = 1;
    private final int DeniedPermission = 0;
    private final PostMapper postMapper;
    private final UserInfoMapper userMapper;
    private final FileMapper fileMapper;

    public PostServiceImpl(PostMapper postMapper, FileMapper fileMapper, UserInfoMapper userMapper){
        this.postMapper = postMapper;
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }
    @CacheEvict(value = "post", allEntries = true)
    @Override
    public PostDTO addPost(PostDTO postDTO, int userNumber){
        if(postDTO.getAdminPost() == AdminPost && userMapper.adminUserCheck(userNumber) == DeniedPermission){
            throw new PermissionDeniedException("권한 부족");
        }
        postDTO.setUserNumber(userNumber);
        postMapper.addPost(postDTO);
        int postNumber = postDTO.getPostNumber();
        List<FileDTO> fileDTOList = postDTO.getFileDTOList();
        for(int i=0;i<fileDTOList.size(); i++) {
            FileDTO fileDTO = fileDTOList.get(i);
            fileDTO.setPostNumber(postNumber);
            fileMapper.addFile(fileDTO);
        }
        return postDTO;
    }
    @Override
    public int checkHasPermission(int postNumber, int userNumber){
        return postMapper.checkHasPermission(userNumber, postNumber);
    }

    @CacheEvict(value = "post", key = "#postNumber")
    @Override
    public void updatePost(PostDTO postDTO, int postNumber){
        String postName = postDTO.getPostName();
        String contents = postDTO.getContents();
        postMapper.updatePost(postName, contents, postNumber);
    }
    @Cacheable(value = "post", key = "#postNumber")
    @Override
    public PostDTO selectPost(int postNumber){
        PostDTO postMetaData = postMapper.selectPost(postNumber);
        return postMetaData;
    }

    public void addViews(int postNumber){
        postMapper.addViews(postNumber);
    }
    @Override
    public PostDTO addComments(int postNumber, CommentsDTO commentsDTO){
        commentsDTO.setPostNumber(postNumber);
        return postMapper.addComments(commentsDTO);
    }
    @CacheEvict(value = "post", key = "#postNumber")
    @Override
    public void deletePost(int postNumber, int userNumber){
        postMapper.deletePost(postNumber, userNumber);
    }
}
