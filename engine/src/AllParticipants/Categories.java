package AllParticipants;

import java.util.ArrayList;
import java.util.List;

public class Categories {
    private List<String> categories;


    public void setCategories(List<String> listCategories){
        categories = listCategories;
    }

    public List<String> getCategories() {
        if (categories == null) {
            categories = new ArrayList<String>();
        }
        return this.categories;
    }

    public void addCategory(String category){
        categories.add(category);
    }

}

