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

    private String period;

    private String important;

    private String field;

    private String purpose;

    private String preferredCondition;

    private String amount;
    public MemberForm(){

    }

    public MemberForm(Long id,String login_id, String password, String status, String period, String important, String field, String purpose, String preferredCondition, String amount){
        this.id = id;
        this.login_id = login_id;
        this.password = password;
        this.status = status;
        this.period = period;
        this.important = important;
        this.field = field;
        this.purpose = purpose;
        this.preferredCondition = preferredCondition;
        this.amount = amount;

    }
}
