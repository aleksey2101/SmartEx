package com.smartexsev.smartexserv.conntrollers;

import com.google.gson.Gson;
import com.smartexsev.smartexserv.classes.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Controller
@EnableWebMvc
@Scope
public class MainController {

    ArrayList<User> users = new ArrayList<>();
    int k = 0;
    Thread save = new Thread(new Runnable() {
        @Override
        public void run() {
            boolean open = false;
            while (true) {
                try {
                    if (open) {
                        FileWriter writer = new FileWriter("users.json");
                        Gson gson = new Gson();
                        writer.write(gson.toJson(users));
                        writer.close();
                        open = true;
                    } else {
                        FileReader reader = new FileReader("users.json");
                        Scanner in = new Scanner(reader);
                        Gson gson = new Gson();
                        users = gson.fromJson(in.nextLine(), ArrayList.class);
                        reader.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    });

    @GetMapping("/startSave")
    public @ResponseBody String startSave() {
        if (!save.isAlive()) {
            save.start();
        }
        return "ok";
    }

    @GetMapping("/listUser")
    public ModelAndView listUser() {
        HashMap<String, String> model = new HashMap<>();
        String s = "";
        for (int i = 0; i < users.size() ;i++){
            s +=    "[Name: "+users.get(i).getName()+
                    " ID: "+users.get(i).getId()+
                    " Password: "+users.get(i).getPassword()+"]";
        }
        model.put("id", s);
        k++;
        model.put("k", Integer.toString(k));
        return new ModelAndView("userList", model);
    }

    @GetMapping("/addUser/{name}/{id}/{password}")
    public @ResponseBody String addUser(@PathVariable String name,
                                        @PathVariable int id,
                                        @PathVariable String password) {
        users.add(new User(name, users.size(), password));
        Gson gson = new Gson();
        return gson.toJson(users.get(users.size()-1));
    }

    @GetMapping("/getUserId/{name}")
    public @ResponseBody String uniquenessName(@PathVariable String name) {
        int key = -1;
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            list.add(users.get(i).getName());
        }
        key = list.indexOf(name);
        return Integer.toString(key);
    }

    @GetMapping("message/{name}/{message}")
    public @ResponseBody String message (@PathVariable String name,
                                         @PathVariable String message) {
        return "ok";
    }

    @GetMapping("/getUser/{id}")
    public @ResponseBody String getUser(@PathVariable int id) {
        Gson gson = new Gson();
        return gson.toJson(users.get(id));
    }

    @GetMapping("/removeUser/{id}")
    public @ResponseBody String removeUser(@PathVariable int id) {
        try {
            users.remove(id);
            return "HTTP_OK";
        } catch (Exception e) {
            return "INVALID_ID";
        }
    }

    @GetMapping("/getUserSize")
    public @ResponseBody String getUserCol() {
        return Integer.toString(users.size());
    }

    @GetMapping("/id/{id}+{id2}")
    public @ResponseBody String incString(@PathVariable int id,
                                  @PathVariable int id2) {
        String sum = Integer.toString(id + id2);
        return sum;
    }

}
