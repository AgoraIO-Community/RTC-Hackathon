package mobi.accessible.show.model;

public class Seat {

    private String userId;
    private boolean isClosed;

    public Seat(String userId) {
        this.userId = userId;
    }

    public Seat(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

}
