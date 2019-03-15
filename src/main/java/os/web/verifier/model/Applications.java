package os.web.verifier.model;

import java.util.ArrayList;
import java.util.List;

public interface Applications {
    default List<String> getAllClassNames(){
        return new ArrayList<>();
    }
}

