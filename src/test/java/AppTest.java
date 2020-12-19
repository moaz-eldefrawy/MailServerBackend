import Services.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void createInitialFolders() {
        try {
            App app = new App();
            File usersIndex = new File(app.userIndexPath);
            File mailIndex = new File(app.mailsFolderPath);
            assertEquals(usersIndex.exists(), true);
            assertEquals(mailIndex.exists(),true);
            FileManager.deleteDir(new File(app.rootPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}