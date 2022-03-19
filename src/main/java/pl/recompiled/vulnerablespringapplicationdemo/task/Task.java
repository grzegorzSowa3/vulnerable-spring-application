package pl.recompiled.vulnerablespringapplicationdemo.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task implements Persistable<UUID> {

    @Id
    private UUID id;
    @Transient
    private boolean isNew;
    private String title;

    public static Task newInstance(String title) {
        final Task task = new Task();
        task.id = UUID.randomUUID();
        task.isNew = true;
        task.title = title;
        return task;
    }
}
