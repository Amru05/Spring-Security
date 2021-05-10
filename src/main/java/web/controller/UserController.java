package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ModelAndView getUsers() {
        List<User> usersList = userService.getUsers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("usersList", usersList);
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @PostMapping(value = "/adduser")
    public ModelAndView addUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        userService.addUser(user);
        return modelAndView;
    }

    @PostMapping(value = "/edituser")
    public ModelAndView editUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        userService.editUser(user);
        return modelAndView;
    }

    @GetMapping(value = "/deluser")
    public ModelAndView delUser(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        userService.delUser(id);
        return modelAndView;
    }

}
