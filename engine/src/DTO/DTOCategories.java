package DTO;

import java.util.List;

public class DTOCategories {
    private List<String> dtoAllCategories;

    public DTOCategories(List<String> dtoAllCategories) {
        this.dtoAllCategories = dtoAllCategories;
    }

    public List<String> getDtoAllCategories() {
        return dtoAllCategories;
    }

    public boolean isEmpty(){
        return dtoAllCategories.isEmpty();
    }

}
