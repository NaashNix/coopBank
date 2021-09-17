package model;

public class UserDetailsModel {
    private String name;
    private String telephone;
    private String bankPosition;
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "UserDetailsModel{" +
                "name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", bankPosition='" + bankPosition + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /*
        --> This model is for saving and getting data from 'UserDetails' database.
     */


    public UserDetailsModel(String name, String telephone, String bankPosition,
                            String userName, String password) {
        this.name = name;
        this.telephone = telephone;
        this.bankPosition = bankPosition;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBankPosition() {
        return bankPosition;
    }

    public void setBankPosition(String bankPosition) {
        this.bankPosition = bankPosition;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
