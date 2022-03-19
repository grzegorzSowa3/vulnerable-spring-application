package pl.recompiled.vulnerablespringapplicationdemo.task;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskEndpoint {

    private final TaskRepository repository;
    private final DataSource dataSource;

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createTask(@RequestBody CreateTaskDto dto) {
        Task task = repository.save(Task.newInstance(dto.getTitle()));
        return ResponseEntity.status(CREATED).body(Map.of("id", task.getId()));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        repository.deleteById(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<String> searchTasks(@RequestParam String phrase) {
        String query = "select title from task where title like '%" + phrase + "%'";
        List<String> result = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            ResultSet rs = c.createStatement().executeQuery(query);
            while (rs.next()) {
                result.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

@Data
class CreateTaskDto {
    private String title;
}
