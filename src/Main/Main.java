import model.AdminCreation;
import model.UI.ApplicationUI;



void main() {

    AdminCreation.createAdmin();

    ApplicationUI applicationUI = new ApplicationUI();
    applicationUI.startApplicationUI();

}
