package wolverine.example.com.btp_farmer.model;

public class QuestionGetSet {
    private String question;
    private String date;
    private String number;
 
    public QuestionGetSet() {
	}
 
    public QuestionGetSet(String question, String date, String number) {
        this.question = question;
        this.date = date;
        this.number = number;
    }
 
    public String getQues() {
        return question;
    }
 
    public void setQues(String name) {
        this.question = name;
    }
 
    public String getDate() {
        return date;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
 
    public String getNumber() {
        return number;
    }
 
    public void setNumber(String number) {
        this.number = number;
    }
 
}