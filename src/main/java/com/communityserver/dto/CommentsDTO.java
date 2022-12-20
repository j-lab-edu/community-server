package com.communityserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentsDTO {
    private int commentsNumber;
    private int postNumber;
    private String contents;
    private String userId;
    private Date createTime;

    public void CommentsDTO(){}

    public void CommentsDTO(int commentsNumber, int postNumber, String contents, String userId, Date createTime){
        this.commentsNumber = commentsNumber;
        this.postNumber = postNumber;
        this.contents = contents;
        this.userId = userId;
        this.createTime = createTime;
    }
}
