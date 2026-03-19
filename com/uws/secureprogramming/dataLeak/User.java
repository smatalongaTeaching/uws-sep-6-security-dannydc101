package com.uws.secureprogramming.dataLeak;

public class User {
    private String name;
    private String email;
    private String password;
    

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }   
    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }
    public void setName(String name) {
        this.name = name;
    }       

    public void setEmail(String email) {
        this.email = email;
    }   

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return name.equals(user.name) &&
               email.equals(user.email);
       }  

}