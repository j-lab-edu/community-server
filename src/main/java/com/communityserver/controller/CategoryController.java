package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.CategoryDTO;
import com.communityserver.exception.DuplicateCategoryException;
import com.communityserver.exception.NotMatchCategoryIdException;
import com.communityserver.service.impl.CategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final Logger logger = LogManager.getLogger(CategoryController.class);

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @LoginCheck(types = LoginCheck.UserType.ADMIN)
    @PutMapping("/add")
    public void categoryAdd(Integer loginUserNumber, @Valid @RequestBody CategoryDTO categoryDTO){
        if(categoryService.categoryDuplicateCheck(categoryDTO.getCategoryName()) != 0) {
            logger.warn("있는 카테고리입니다.");
            throw new DuplicateCategoryException("있는 카테고리입니다.");
        }
        if(categoryService.addCategoryName(categoryDTO))
            logger.info("success");
    }
    @LoginCheck(types = LoginCheck.UserType.ADMIN)
    @DeleteMapping("/{categoryNumber}")
    public void categoryDelete(Integer loginUserNumber, @PathVariable("categoryNumber") int categoryNumber) {
        if (categoryService.categoryNumberCheck(categoryNumber)) {
            logger.warn("정확한 카테고리를 입력해주세여");
            throw new NotMatchCategoryIdException("정확한 카테고리를 입력해주세요");
        }
        categoryService.deleteCategoryNumber(categoryNumber);
        System.out.println("success");
    }
}
