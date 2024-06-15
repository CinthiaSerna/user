package com.example.user.controller;

import com.example.user.helper.Response;
import com.example.user.helper.ResponseBuild;
import com.example.user.persistence.entity.User;
import com.example.user.service.DTO.UserDTO;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseBuild build;


    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLast_name());
        return user;
    }



    @PostMapping
    public Response save(@Valid @RequestBody UserDTO userdto, BindingResult result){
        if(result.hasErrors()){
            return build.success(format(result));
        }
        User user = convertToEntity(userdto);
        userService.save(user);
        return build.success(user);
    }

    @GetMapping
    public Response findAll(){
        return build.success(userService.findAll());
    }


    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id){
        User user = (User) userService.findById(id);
        if(user==null){
            return build.success("El usuario a eliminar no existe");
        }
        userService.delete(user);
        return build.success(user);
    }


    private List<Map<String,String>> format(BindingResult result){
        return result.getFieldErrors()
                .stream().map(error -> {
                    Map<String,String> err = new HashMap<>();
                    err.put(error.getField(),error.getDefaultMessage());
                    return err;
                }).collect(Collectors.toList());
    }
}
