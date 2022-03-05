package nam.nguyen;

import java.sql.Timestamp;

public class Notification {

    private String username;
    private String type_of_notice;
    private Integer message_or_friend_req_id;
    private Timestamp created_at;

    public Notification(String username, String type_of_notice, Integer message_or_friend_req_id,
            Timestamp created_at) {
        this.username = username;
        this.type_of_notice = type_of_notice;
        this.message_or_friend_req_id = message_or_friend_req_id;
        this.created_at = created_at;
    }

    public Notification(String username, String type_of_notice, Integer messageId) {
        this.username = username;
        this.type_of_notice = type_of_notice;
        this.message_or_friend_req_id = messageId;
    }

    public String getUsername() {
        return username;
    }

    public String getType_of_notice() {
        return type_of_notice;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Integer getMessage_or_friend_req_id() {
        return message_or_friend_req_id;
    }

}
