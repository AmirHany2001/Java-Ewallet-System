import model.AdminCreation;
import UI.ApplicationUI;



void main() {

    AdminCreation.createAdmin();

    ApplicationUI applicationUI = new ApplicationUI();
    applicationUI.startApplicationUI();

}
