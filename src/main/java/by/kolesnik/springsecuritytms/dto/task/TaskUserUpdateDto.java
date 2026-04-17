package by.kolesnik.springsecuritytms.dto.task;

import by.kolesnik.springsecuritytms.enums.Status;
import lombok.Data;

@Data
public class TaskUserUpdateDto {

    private Status status;
}
