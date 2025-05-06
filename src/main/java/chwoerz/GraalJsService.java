package chwoerz;

import jakarta.annotation.PostConstruct;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GraalJsService {

  private ValidateWrapper validateWrapper;

  public static final Map<String, Long> DB = new ConcurrentHashMap<>();

  @PostConstruct
  void init() throws IOException {
    var context = Context.newBuilder("js", "python")
        .allowAllAccess(true)
        .allowExperimentalOptions(true)
        .option("js.commonjs-require", "true")
        .option("js.esm-eval-returns-exports", "true")
        .build();
    var sourceFile = Source.newBuilder("js",
        Path.of("src/main/js/wrapper.mjs").toFile()).build();
    validateWrapper = context.eval(sourceFile).getMember("validatePerson").as(ValidateWrapper.class);
  }

  public List<ZodResult> validatePerson(Person person) {
    List<ZodResult> zodResults = validateWrapper.validatePerson(person);
    if(zodResults.isEmpty()) {
      DB.merge("OK", 1L, Long::sum);
    }

    zodResults.forEach(entry -> DB.merge(entry.code(), 1L, Long::sum));
    return zodResults;
  }

  @FunctionalInterface
  interface ValidateWrapper {
    List<ZodResult> validatePerson(Person person);
  }
}
