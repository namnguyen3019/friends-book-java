package nam.nguyen;

import java.sql.Timestamp;

public class FriendRequest {

    private int friend_req_id;
    private String sender;
    private String receiver;
    private String status = "pending";
    private boolean isSeen = false;
    private String note = "";
    private Timestamp created_at;
    private Timestamp updated_at;

    public FriendRequest(int friend_req_id, String sender, String receiver, String status, String note, boolean isSeen,
            Timestamp created_at, Timestamp updated_at) {
        this.friend_req_id = friend_req_id;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.note = note;
        this.isSeen = isSeen;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

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

    public int getFriend_req_id() {
        return friend_req_id;
    }

    public boolean getIsSeen() {
        return isSeen;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

}
