package project.saving_web_service.controller;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MemberForm {

    private Long id;

    private String login_id;

    private String password;

    private List<String> status;

    private String period;

    private List<String> important;

    private String purpose;

    private String preferredCondition;

    private String amount;

    private String age;

    private String sex;
    public MemberForm(){

    }

    public MemberForm(Long id,String login_id, String password, List<String> status, String period, List<String> important, String purpose, String preferredCondition, String amount, String age, String sex){
        this.id = id;
        this.login_id = login_id;
        this.password = password;
        this.status = status;
        this.period = period;
        this.important = important;
        this.purpose = purpose;
        this.preferredCondition = preferredCondition;
        this.amount = amount;
        this.age = age;
        this.sex = sex;

    }
}
