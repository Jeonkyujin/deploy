package project.saving_web_service.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "member")
@Entity
@Getter@Setter
public class Member {
    @Id@GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String login_id;

    private String password;

    private String status;

    private String interest;

    private String commodity_existence;

    private String commodity;

    private String period;

    private String important;

    private String recommendation;

}
