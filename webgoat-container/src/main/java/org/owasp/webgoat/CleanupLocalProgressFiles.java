package org.owasp.webgoat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.FileSystemUtils;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author nbaars
 * @since 4/15/17.
 */
@Slf4j
@Configuration
@ConditionalOnExpression("'${webgoat.clean}' == 'true'")
public class CleanupLocalProgressFiles {

    @Value("${webgoat.server.directory}")
    private String webgoatHome;

    @PostConstruct
    public void clean() {
        File dir = new File(webgoatHome);
        //do it safe, check whether the subdir mongodb is available as subdirectory
        File[] mongoDir = dir.listFiles(f -> f.isDirectory() && f.getName().contains("mongodb"));
        if (mongoDir != null && mongoDir.length == 1) {
            FileSystemUtils.deleteRecursively(dir);
        }
    }
}
