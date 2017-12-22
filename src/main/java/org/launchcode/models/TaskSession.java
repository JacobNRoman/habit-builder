package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class TaskSession {

    @GeneratedValue
    @Id
    private int id;
    private LocalTime start;
    private LocalTime stop;
    private Duration length;
    private LocalDate date;

    @ManyToOne
    private Task task;

    public TaskSession(){}

    public void startClock(Task task){
        LocalDateTime now = LocalDateTime.now();
        this.setStart(now.toLocalTime());
        this.setDate(now.toLocalDate());
        this.setTask(task);
    }

    public void stopClock(){
        LocalDateTime now = LocalDateTime.now();
        this.setStop(now.toLocalTime());
        this.setLength(Duration.between(this.getStart(), this.getStop()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getStop() {
        return stop;
    }

    public void setStop(LocalTime stop) {
        this.stop = stop;
    }

    public Duration getLength() {
        return length;
    }

    public void setLength(Duration length) {
        this.length = length;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
