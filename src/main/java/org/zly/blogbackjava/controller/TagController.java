package org.zly.blogbackjava.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.dto.request.CreateTagDto;

@RestController
@RequestMapping("/tag")
@RequireLogin
public class TagController {

    @PostMapping("/createTag")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createTag(@RequestBody @Valid CreateTagDto createTagDto) {

    }

    @GetMapping("/getAllTagList")
    public void getAllTagList(){}

    @GetMapping("/getTagListByPage")
    public void getTagListByPage(){}

    @GetMapping("/getTagInfo/{id}")
    public void getTagInfo(){}

    @PutMapping("/updateTag")
    public void updateTag(){}

    @DeleteMapping("/deleteTag/{id}")
    public void deleteTag(){}
}
