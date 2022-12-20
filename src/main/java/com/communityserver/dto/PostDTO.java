package com.communityserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
public class PostDTO  implements Serializable {
    private int postNumber;
    private int categoryNumber;
    private int userNumber;
    private int fileNumber;
    private String postName;
    private int adminPost;
    private String contents;
    private Date createTime;
    private int suggestionCount;
    private int views;
    private List<FileDTO> fileDTOList;

    public PostDTO(){}

    public PostDTO(int postNumber, int categoryNumber, int userNumber, int fileNumber, String postName,
                   int adminPost, String contents, Date createTime, int suggestionCount, int views, List<FileDTO> fileDTOList){
        this.postNumber = postNumber;
        this.categoryNumber = categoryNumber;
        this.userNumber = userNumber;
        this.fileNumber = fileNumber;
        this.postName = postName;
        this.adminPost = adminPost;
        this.contents = contents;
        this.createTime = createTime;
        this.suggestionCount = suggestionCount;
        this.views = views;
        this.fileDTOList = fileDTOList;
    }
    @Override
    public String toString() {
        return "PostDTO" + getCategoryNumber() + getPostName() + getContents() + getCreateTime() + getSuggestionCount();
    }
}
