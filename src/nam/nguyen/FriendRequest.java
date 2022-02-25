package nam.nguyen;

public class FriendRequest {

    private String sender;
    private String receiver;
    private String status = "pending";
    private boolean read = false;
    private String note = "";

    public FriendRequest(String sender, String receiver, String note) {
        this.sender = sender;
        this.receiver = receiver;
        this.note = note;
    }

    public FriendRequest(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public boolean getRead() {
        return read;
    }

}
