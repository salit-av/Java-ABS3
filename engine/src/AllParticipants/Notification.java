package AllParticipants;

public class Notification implements Cloneable{
    private String title;
    private String content;
    private String end;

    public Notification(String title, String content, String end) {
        this.title = title;
        this.content = content;
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public Notification clone()  {
/*        try {
            return (Notification) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }*/
        return null;
    }
}
