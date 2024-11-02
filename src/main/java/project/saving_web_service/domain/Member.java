package project.saving_web_service.domain;

import java.util.ArrayList;
import java.util.List;

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

    private String period;

    private String important;

    private String purpose;

    private String preferredCondition;

    private String amount;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteInstall> favoriteInstalls = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteDeposit> favoriteDeposits = new ArrayList<>();

}
