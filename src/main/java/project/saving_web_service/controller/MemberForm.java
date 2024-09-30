package project.saving_web_service.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MemberForm {

    private Long id;

    private String login_id;

    private String password;

    private String status;

    private String interest;

    private String commodity_existence;

    private String commodity;

    private String period;

    private String important;
    public MemberForm(){

    }

    public MemberForm(Long id,String login_id, String password, String status, String interest, String commodity_existence, String commodity, String period, String important ){
        this.id = id;
        this.login_id = login_id;
        this.password = password;
        this.status = status;
        this.interest = interest;
        this.commodity_existence = commodity_existence;
        this.commodity = commodity;
        this.period = period;
        this.important = important;

    }
}
