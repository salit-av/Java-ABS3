package AllParticipants;

public class Notification {
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

}
