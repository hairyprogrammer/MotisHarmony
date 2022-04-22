/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import model.Accounts;
import model.AccountsDataManager;
import model.AlarmClock;
import model.MusicPlayerManager;
import model.YoutubeDownloader;
import view.MainViewRunner;

/**
 * FXML Controller class
 *
 * @author Jake Yeo
 */
public class SettingsPageViewController implements Initializable {

    @FXML
    private AnchorPane settingsViewMainAnchorPane;
    @FXML
    private Button logoutButton;
    @FXML
    private RadioButton saveDownloadQueueRadioButton;
    @FXML
    private RadioButton saveSongPositionRadioButton;
    @FXML
    private RadioButton savePlayPreference;
    @FXML
    private RadioButton stayLoggedInRadioButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        saveDownloadQueueRadioButton.setSelected(Accounts.getLoggedInAccount().getSettingsObject().getSaveDownloadQueue());
        saveSongPositionRadioButton.setSelected(Accounts.getLoggedInAccount().getSettingsObject().getSaveSongPosition());
        savePlayPreference.setSelected(Accounts.getLoggedInAccount().getSettingsObject().getSavePlayPreference());
        stayLoggedInRadioButton.setSelected(Accounts.getLoggedInAccount().getSettingsObject().getStayLoggedIn());
    }

    @FXML
    private void logout(ActionEvent event) throws Exception {
        AccountsDataManager.saveAllSettings();
        MusicPlayerManager.getMpmCurrentlyUsing().stopDisposeMediaPlayer();
        AccountsDataManager adm = new AccountsDataManager();
        adm.setPathOfAccToAutoLogIn(null);
        YoutubeDownloader.getYtdCurrentlyUsing().setStopDownloading(true);
        YoutubeDownloader.getYtdCurrentlyUsing().setStopAllDownloadingProcesses(true);
        Accounts.setLoggedInAccount(null);
        //Stop the alarm clock from checking the time
        AlarmClock.getAlarmCurrentlyUsing().stopAlarmCheck();
        //Makes the sliding bar menu animate correctly
        MainViewRunner.setSlideBarRanOnce(false);
        YoutubeDownloader.getYtdCurrentlyUsing().getYoutubeUrlDownloadQueueList().clear();
        //initialize login page before switching
        MainViewRunner.getSceneChanger().addScreen("LoginPage", FXMLLoader.load(getClass().getResource("/fxml/LoginPageView.fxml")));
        MainViewRunner.getSceneChanger().switchToLoginPageView();
    }

    @FXML
    private void updateSaveDownloadQueue(ActionEvent event) throws Exception {
        AccountsDataManager.setSaveDownloadQueue(saveDownloadQueueRadioButton.isSelected());
    }

    @FXML
    private void updateSaveSongPosition(ActionEvent event) throws Exception {
        AccountsDataManager.setSaveSongPosition(saveSongPositionRadioButton.isSelected());
    }

    @FXML
    private void updateSavePlayPreference(ActionEvent event) throws Exception {
        AccountsDataManager.setSavePlayPreference(savePlayPreference.isSelected());
    }

    @FXML
    private void updateStayLoggedIn(ActionEvent event) throws Exception {
        AccountsDataManager.setStayLoggedIn(stayLoggedInRadioButton.isSelected());
    }

}
