package ua.in.sydoruk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.in.sydoruk.model.User;
import ua.in.sydoruk.service.UserService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    private static int currentPage = 0;
    private static int lastPage = 0;
    private static String nameForSearch = "";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public ModelAndView methodForPage() {
        ModelAndView modelAndView = new ModelAndView("list-of-users");
        List<User> users = userService.getUsers(currentPage, nameForSearch);
        lastPage = userService.getThePageNumber(nameForSearch);
        modelAndView.addObject("users", users);
        modelAndView.addObject("first", "Перша");
        modelAndView.addObject("last", "Остання");
        modelAndView.addObject("prev", currentPage != 0 ? "Попередня" : "");
        modelAndView.addObject("current", currentPage);
        int startPage = currentPage - 5 > 0 ? currentPage - 5 : 1;

        int endPage = startPage + 9;
        if (endPage > lastPage) {
            endPage = lastPage;
        }
        modelAndView.addObject("startPage", startPage);
        modelAndView.addObject("endPage", endPage);
        modelAndView.addObject("number", lastPage);
        modelAndView.addObject("next", currentPage < lastPage - 1 ? "Наступна" : "");
        modelAndView.addObject("nameReturn", nameForSearch);
        modelAndView.addObject("reset", (nameForSearch.length() != 0) ? " " : "");
        return modelAndView;
    }


    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public ModelAndView addUserPage() {
        ModelAndView modelAndView = new ModelAndView("add-user-form");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }


    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ModelAndView addingUser(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "age") String age,
                                   @RequestParam(value = "isAdmin") String admin) {
        String message = validation(name, age);
        ModelAndView modelAndView;

        if (message.length() != 0) {
            modelAndView = new ModelAndView("add-user-form");
        } else {
            remove();

            User user = new User();
            user.setName(name);
            user.setAge(Integer.parseInt(age));
            user.setisAdmin(Boolean.parseBoolean(admin));
            userService.addUser(user);

            modelAndView = methodForPage();
            message = "Користувач успішно доданий.";
        }

        modelAndView.addObject("message", message);
        return modelAndView;
    }

    private String validation(String name, String age) {
        String message = "";
        Pattern p = Pattern.compile("\\p{Punct}");

        Matcher m = p.matcher(name);
        if (name.trim() == "") {
            message = "Ви нічого не ввели. Введіть ім'я користувача!<br/>";
        } else if (m.find()) {
            message = "І'мя не може містити знаки пунктуації<br/>";
        } else if (name.length() > 25) {
            message = "Довжина імені не повинна перевищувати 25 символів<br/>";
        }


        try {
            int ageInt = Integer.parseInt(age);

            if (ageInt < 1) {
                message += "Невірні дані. Вік повинен бути цілим числом більшим від нуля!";
            }

            if (ageInt > 150) {
                message += "Максимально можливе значення віку 150 років!";
            }
        } catch (NumberFormatException e) {
            message += "Введіть коректне значення для віку";
        }
        return message;
    }


    @RequestMapping(value = "/user/list")
    public ModelAndView listOfUsers(@RequestParam(value = "page") int page) {
        currentPage = page;
        return methodForPage();
    }

    @RequestMapping(value = "/user/next")
    public ModelAndView nextPage() {
        if (currentPage < userService.getThePageNumber(nameForSearch) - 1)
            currentPage++;
        return methodForPage();
    }

    @RequestMapping(value = "/user/prev")
    public ModelAndView prevPage() {
        if (currentPage != 0) currentPage--;
        return methodForPage();
    }

    @RequestMapping(value = "/user/first")
    public ModelAndView firstPage() {
        if (currentPage != 0) currentPage = 0;
        return methodForPage();
    }

    @RequestMapping(value = "/user/last")
    public ModelAndView lastPage() {
        currentPage = lastPage - 1;
        return methodForPage();
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUserPage(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("edit-user-form");
        User user = userService.getUser(id);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editingUser(@RequestParam(value = "name") String name,
                                    @RequestParam(value = "age") String age,
                                    @RequestParam(value = "isAdmin") String admin,
                                    @PathVariable Integer id) {
        String message = validation(name, age);
        ModelAndView modelAndView;
        User user = userService.getUser(id);

        if (message.length() != 0) {
            modelAndView = new ModelAndView("edit-user-form");
            modelAndView.addObject("user", user);
        } else {
            user.setName(name);
            user.setAge(Integer.parseInt(age));
            user.setisAdmin(Boolean.parseBoolean(admin));
            userService.updateUser(user);

            modelAndView = methodForPage();
            message = "Дані користувача успішно оновлені.";
        }

        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        String message = "Користувач успішно видалений.";
        ModelAndView modelAndView = methodForPage();
        if (currentPage == lastPage) modelAndView = prevPage();
        modelAndView.addObject("message", message);
        return modelAndView;
    }


    @RequestMapping(value = "/user/search")
    public ModelAndView search(@RequestParam(value = "name") String name) {
        if (name != "") nameForSearch = name;

        currentPage = 0;
        return methodForPage();
    }

    @RequestMapping(value = "/user/remove")
    public ModelAndView remove() {
        currentPage = 0;
        nameForSearch = "";

        return methodForPage();
    }


}
