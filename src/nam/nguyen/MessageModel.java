package nam.nguyen;

import java.sql.Timestamp;

public class MessageModel {

    final private String sender_id;
    final private String receiver_id;
    final private String content;
    private boolean read;
    final private Timestamp created_at;

    public MessageModel(String sender_id, String receiver_id, String content, boolean read, Timestamp created_at) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.content = content;
        this.read = read;
        this.created_at = created_at;
    }

    public String getContent() {
        return content;
    }

    public String getSender_id() {
        return sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public boolean getRead() {
        return read;
    }
}
