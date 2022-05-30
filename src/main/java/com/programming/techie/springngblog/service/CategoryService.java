package com.programming.techie.springngblog.service;

import com.programming.techie.springngblog.dto.CategoryDto;
import com.programming.techie.springngblog.model.Category;
import com.programming.techie.springngblog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Transactional
    public List<CategoryDto> getAllCategory(){
        List <Category> listCategory = categoryRepository.findAll();
        List<CategoryDto> listDto = new ArrayList<>();
        for (Category i : listCategory){
            CategoryDto dto = new CategoryDto();
            dto.setId(i.getId());
            dto.setName(i.getName());
            listDto.add(dto);
        }
        return listDto;
    }
}
