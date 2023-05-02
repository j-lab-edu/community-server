package com.communityserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    @Schema(name = "file number", description = "file 번호(Auto increment)")
    private int fileNumber;

    @Schema(name = "post number", description = "작성한 post 번호")
    private int postNumber;

    @Schema(name = "path", description = "파일 경로")
    private String path;

    @Schema(name = "file name", description = "파일의 이름")
    private String fileName;

    @Schema(name = "extension", description = "파일 확장자")
    private String extension;

}
