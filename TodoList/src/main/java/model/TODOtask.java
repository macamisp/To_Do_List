package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TODOtask {
    private int taskId;
    private String taskTitle;
    private String taskDescription;
    private LocalDate doneDate;
}
