package ru.katapp.springbootcrudcfg.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.katapp.springbootcrudcfg.entity.User;
import ru.katapp.springbootcrudcfg.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserController {
    private final UserService userService;

    private final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("index");
        try {
            List<User> listUser = userService.listAll();
            mav.addObject("listUser", listUser);
            mav.addObject("user", new User());
            logger.info("Таюлица пользователей загружена");
        } catch (Exception e) {
            logger.warning("Ошибка: " + Arrays.toString(e.getStackTrace()));
        }
        return mav;
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                userService.save(user);
            }
            logger.info("Пользователь " + user.getName() + " добавлен в таблицу");
        } catch (Exception e) {
            logger.warning("Ошибка: " + Arrays.toString(e.getStackTrace()));
        }
        return "redirect:/";

    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        try {
            userService.delete(id);
            logger.info("Пользователь удален");
        } catch (Exception e) {
            logger.warning("Ошибка: " + Arrays.toString(e.getStackTrace()));
        }
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam("id") Long id, Model model) {
        try {
            User user = userService.find(id);
            model.addAttribute("user", user);
            logger.info("Страница изменения пользователя " + user.getId() + " | " + user.getName());
        } catch (Exception e) {
            logger.warning("Ошибка: " + Arrays.toString(e.getStackTrace()));
        }
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "redirect:/edit";
            }
            userService.update(user);
            logger.info("Пользователь c id: " + user.getId() + "успешно изменен");
        } catch (Exception e) {
            logger.warning("Ошибка: " + Arrays.toString(e.getStackTrace()));
        }
        return "redirect:/";
    }
}
