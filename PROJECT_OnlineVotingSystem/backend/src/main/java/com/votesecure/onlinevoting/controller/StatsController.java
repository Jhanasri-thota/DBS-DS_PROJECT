package com.votesecure.onlinevoting.controller;
import com.votesecure.onlinevoting.service.ElectionService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/stats") public class StatsController{
 private final ElectionService service; public StatsController(ElectionService service){this.service=service;}
 @GetMapping public Map<String,Object> stats(){return service.summary();}
}
