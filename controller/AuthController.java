package controller;

import model.DataStore;
import model.User;
import view.LoginView;

public class AuthController {
    private LoginView view;
    public User loggedUser = null;

    public AuthController(LoginView view) {
        this.view = view;
        view.loginBtn.addActionListener(e -> {
            String u = view.userField.getText();
            String p = String.valueOf(view.passField.getPassword());
            for(User user : DataStore.users) {
                if(user.getUsername().equals(u) && user.getPassword().equals(p)) {
                    loggedUser = user;
                    view.msgLabel.setText("Login Success!");
                    view.setVisible(false);
                }
            }
            if(loggedUser == null) view.msgLabel.setText("Login Failed!");
        });
    }
}
