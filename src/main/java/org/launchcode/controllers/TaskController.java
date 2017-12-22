package org.launchcode.controllers;

import org.launchcode.models.Task;
import org.launchcode.models.TaskSession;
import org.launchcode.models.data.TaskDao;
import org.launchcode.models.data.TaskSessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;

@Controller
public class TaskController {

    @Autowired
    TaskDao taskDao;

    @Autowired
    TaskSessionDao sessionDao;

    @RequestMapping(value="")
    public String index(Model model){
        model.addAttribute("title", "Tasks");
        model.addAttribute("tasks", taskDao.findAll());

        return "index";
    }

    @RequestMapping(value="add", method= RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("title", "New Task");
        model.addAttribute(new Task());

        return "add";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Task task, Errors errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("title", "New Task");

            return "add";
        }
        taskDao.save(task);
        return "redirect:";
    }

    @RequestMapping(value="task/{id}", method=RequestMethod.GET)
    public String task(Model model, @PathVariable int id){

        Task task = taskDao.findOne(id);

        model.addAttribute("title", task.getName());
        model.addAttribute("task", task);

        return "task";
    }

    @RequestMapping(value="task/{id}", method=RequestMethod.POST)
    public String startTask(Model model, @PathVariable int id){

        Task task = taskDao.findOne(id);
        TaskSession session = new TaskSession();
        session.startClock(task);
        sessionDao.save(session);

        model.addAttribute("task", task);
        model.addAttribute("title", task.getName());
        model.addAttribute("taskSession", session);

        return "task";
    }

    @RequestMapping(value="task/{taskId}/{sessionId}", method=RequestMethod.POST)
    public String endTask(Model model, @PathVariable int taskId, @PathVariable int sessionId){
        Task task = taskDao.findOne(taskId);
        TaskSession session = sessionDao.findOne(sessionId);
        session.stopClock();
        sessionDao.save(session);

        model.addAttribute("task", task);
        model.addAttribute("title", task.getName());

        return "task";
    }
}
