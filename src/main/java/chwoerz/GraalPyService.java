package chwoerz;

import jakarta.annotation.PostConstruct;
import org.graalvm.polyglot.Source;
import org.graalvm.python.embedding.GraalPyResources;
import org.graalvm.python.embedding.VirtualFileSystem;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Service
public class GraalPyService {

  private PieChart pieChart;

  @PostConstruct
  void init() throws IOException {
    var context = GraalPyResources
        .contextBuilder(
            VirtualFileSystem.newBuilder()
                .resourceDirectory("GRAALPY-VFS").build()
        ).allowAllAccess(true)
        .build();

    var sourceFile = Source.newBuilder("python",
        Path.of("src/main/python/lib.py").toFile()).build();
    pieChart = context.eval(sourceFile).as(PieChart.class);
  }

  public String toPie(Map<String, Long> resultMap) {
    return pieChart.toPie(resultMap);
  }

  @FunctionalInterface
  interface PieChart {
    String toPie(Map<String, Long> resultMap);
  }
}
