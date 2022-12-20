package com.communityserver.mapper;

import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;

public interface FileMapper {
    void addFile(FileDTO fileDTO);
    void deleteFile(int postNumber);
}
