package com.zags.gorodovoy.Services;


import com.zags.gorodovoy.dtoModels.TaskFilter;
import com.zags.gorodovoy.models.Task;
import com.zags.gorodovoy.repository.TaskRepository;
import com.zags.gorodovoy.specification.TaskSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task getById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    public List<Task> getTasksByFilters(TaskFilter taskFilter) {
        Specification<Task> spec = Specification.where(null);

        if (taskFilter.getUserId() != null) spec = spec.and(TaskSpec.userIdEquals(taskFilter.getUserId()));
        if (taskFilter.getStatusId() != null) spec = spec.and(TaskSpec.statusIdEquals(taskFilter.getStatusId()));
        if (taskFilter.getServiceId() != null) spec = spec.and(TaskSpec.serviceIdEquals(taskFilter.getServiceId()));
        if (taskFilter.getEmployeeId() != null) spec = spec.and(TaskSpec.employeeIdEquals(taskFilter.getEmployeeId()));

        List<Task> tasks = taskRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "dateCreate"));


        return tasks;
    }

    public long calculateAverageExecutionTime() {
        List<Task> tasks = taskRepository.findAllTasks();
        long totalDuration = 0;
        int count = 0;

        for (Task task : tasks) {
            Date dateStart = task.getDateStart();
            Date dateFinish = task.getDateFinish();

            if (dateStart != null && dateFinish != null) {
                long duration = dateFinish.getTime() - dateStart.getTime();
                totalDuration += duration;
                count++;
            }
        }

        return count > 0 ?  totalDuration / (3_600_000) / count : 0;
    }

    public Integer getCountReadyTasks(){
        return taskRepository.countReadyTasks();
    }

    public int[] getCountForMonthService(Long serviceId) {
        int[] tasksCount = new int[12];
        List<Task> tasks = taskRepository.findAllTasks();

        for (Task task : tasks) {
            Long servId = task.getServiceId();
            Date dateCreate = task.getDateCreate();

            if (servId == serviceId && dateCreate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateCreate);
                int month = calendar.get(Calendar.MONTH); // Получаем номер месяца (0-11)
                tasksCount[month]++; // Увеличиваем счетчик
            }
        }

        return tasksCount;
    }
}