package chwoerz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static chwoerz.GraalJsService.DB;

@RestController
@RequestMapping("/api")
public class MainController {

  @Autowired
  private GraalJsService graalJsService;

  @Autowired
  private GraalPyService graalPyService;

  @PostMapping("/validate")
  List<ZodResult> validate(@RequestBody Person person) {
    return graalJsService.validatePerson(person);
  }

  @GetMapping("/chart")
  String getChart() {
    return graalPyService.toPie(DB);
  }
}
