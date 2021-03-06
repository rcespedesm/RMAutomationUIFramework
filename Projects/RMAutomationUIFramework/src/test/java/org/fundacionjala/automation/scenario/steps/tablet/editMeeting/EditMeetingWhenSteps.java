package org.fundacionjala.automation.scenario.steps.tablet.editMeeting;

import java.util.List;

import org.fundacionjala.automation.framework.pages.tablet.home.HomePage;
import org.fundacionjala.automation.framework.pages.tablet.scheduler.CredentialsPage;
import org.fundacionjala.automation.framework.pages.tablet.scheduler.SchedulerPage;
import org.fundacionjala.automation.framework.pages.tablet.settings.ConnectionPage;
import org.fundacionjala.automation.framework.pages.tablet.settings.NavigationPage;
import org.fundacionjala.automation.framework.utils.common.BrowserManager;
import org.fundacionjala.automation.framework.utils.common.PropertiesReader;

import cucumber.api.java.en.When;

public class EditMeetingWhenSteps {

    @When("^I display the meeting \"([^\"]*)\" in the \"([^\"]*)\" room$")
    public void displayMeeting(String subject, String roomName)
	    throws Throwable {
	BrowserManager.openBrowser();
	ConnectionPage connection = new ConnectionPage();

	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	navigation.clickOnRoomToggleButton().selectConferenceRoom(roomName)
		.clickOnSaveButton().topMenu.clickOnHomeButton()
		.clickOnScheduleButton().displayAllDayOnTimeline()
		.clickOnMeetingButton(subject);

    }

    @When("^I modified the meeting \"([^\"]*)\" in the \"([^\"]*)\" room  adding the attendees$")
    public void addingAttendees(String meetingName, String roomName,
	    List<String> attendees) throws Throwable {
	BrowserManager.openBrowser();
	ConnectionPage connection = new ConnectionPage();
	SchedulerPage schedule = new SchedulerPage();

	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	navigation.clickOnRoomToggleButton().selectConferenceRoom(roomName)
		.clickOnSaveButton().topMenu.clickOnHomeButton()
		.clickOnScheduleButton().displayAllDayOnTimeline()
		.clickOnMeetingButton(meetingName);

	for (String attendeName : attendees) {
	    schedule.setAttende(attendeName);
	}

    }

    @When("^Save the changes$")
    public void save_the_changes() throws Throwable {
	SchedulerPage schedule = new SchedulerPage();
	schedule.clickUpdateButton()
		.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOkButton();
    }

    @When("^I modify the \"([^\"]*)\" with \"([^\"]*)\" in the \"([^\"]*)\" meeting in \"([^\"]*)\" room$")
    public void i_modify_the_with_in_the_meeting_in_room(String field,
	    String value, String subject, String roomName) throws Throwable {
	HomePage home = new HomePage();
	BrowserManager.openBrowser();
	ConnectionPage connection = new ConnectionPage();
	SchedulerPage schedule = new SchedulerPage();
	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	home = navigation.clickOnRoomToggleButton()
		.selectConferenceRoom(roomName).clickOnSaveButton().topMenu
		.clickOnHomeButton();

	home.clickOnScheduleButton();
	schedule.displayAllDayOnTimeline().clickOnMeetingButton(subject);

	switch (field) {
	case "subject":
	    schedule.setSubject(value);
	    break;
	case "startTime":
	    schedule.setStartTime(value);
	    break;
	case "endTime":
	    schedule.setEndTime(value);
	    break;
	case "attendees":
	    schedule.setAttende(value);
	    break;
	case "body":
	    schedule.setBody(value);
	    break;
	}
    }

    @When("^Confirm the changes with the user \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void confirm_the_changes_with_the_user_and_password(String field,
	    String value) throws Throwable {
	SchedulerPage schedule = new SchedulerPage();
	CredentialsPage credentials = new CredentialsPage();
	credentials = schedule.clickUpdateButton();
	credentials.clickUpdateAsCheckBox()
		.setUserName(PropertiesReader.getExchangeInviteUser())
		.setPassword(PropertiesReader.getExchangeInvitePwd())
		.tryToClickOnOkButton();
    }

    @When("^I want to modify the meeting  \"([^\"]*)\" in the \"([^\"]*)\" room over the same time of out of order$")
    public void i_want_to_modify_the_meeting_in_the_room_over_the_same_time_of_out_of_order(
	    String subject, String roomName) throws Throwable {

	String startTime = "07:00:00.000";
	String endTime = "23:00:00.000";
	BrowserManager.openBrowser();
	HomePage home = new HomePage();
	ConnectionPage connection = new ConnectionPage();
	CredentialsPage credential = new CredentialsPage();

	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	home = navigation.clickOnRoomToggleButton()
		.selectConferenceRoom(roomName).clickOnSaveButton().topMenu
		.clickOnHomeButton();

	home.clickOnScheduleButton();

	SchedulerPage scheduler = new SchedulerPage();
	credential = scheduler
		.setOrganizer(PropertiesReader.getExchangeOrganizerUser())
		.setSubject(subject).setStartTime(startTime)
		.setEndTime(endTime).clickOnCreateButton();
	credential.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOnOkButton();

    }

    @When("^I modify the schedule in the \"([^\"]*)\" meeting on \"([^\"]*)\" drag and drop the whole meeting to the \"([^\"]*)\" \"([^\"]*)\" hour$")
    public void dragging_and_dropping_the_whole_meeting_to_the_hour(
	    String meetingName, String roomName, String direction, int hours)
	    throws Throwable {
	BrowserManager.openBrowser();
	ConnectionPage connection = new ConnectionPage();
	SchedulerPage schedule = new SchedulerPage();
	CredentialsPage credential = new CredentialsPage();

	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	navigation.clickOnRoomToggleButton().selectConferenceRoom(roomName)
		.clickOnSaveButton().topMenu.clickOnHomeButton()
		.clickOnScheduleButton().displayAllDayOnTimeline()
		.clickOnMeetingButton(meetingName);
	if (direction.equals("left")) {
	    hours = hours * -1;
	}
	schedule.moveMeetingOnTimeLine(meetingName, hours).clickUpdateButton();
	credential.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOnOkButton();
    }

    @When("^I modify the \"([^\"]*)\" meeting in \"([^\"]*)\" room from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void modify_the_schedule_meeting(String subject, String roomName,
	    String startTime, String endTime) throws Throwable {
	BrowserManager.openBrowser();
	HomePage home = new HomePage();
	ConnectionPage connection = new ConnectionPage();
	CredentialsPage credential = new CredentialsPage();
	SchedulerPage scheduler = new SchedulerPage();

	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	home = navigation.clickOnRoomToggleButton()
		.selectConferenceRoom(roomName).clickOnSaveButton().topMenu
		.clickOnHomeButton();

	scheduler = home.clickOnScheduleButton();
	scheduler.displayAllDayOnTimeline().clickOnMeetingButton(subject)
		.setStartTime(startTime).setEndTime(endTime)
		.clickUpdateButton();
	credential.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOnOkButton();

    }

    @When("^I modify the \"([^\"]*)\" meeting in \"([^\"]*)\" room with new subject \"([^\"]*)\"$")
    public void i_modify_the_meeting_in_room_with_new_subject(String subject,
	    String roomName, String newSubject) throws Throwable {

	BrowserManager.openBrowser();
	HomePage home = new HomePage();
	ConnectionPage connection = new ConnectionPage();
	CredentialsPage credential = new CredentialsPage();
	SchedulerPage scheduler = new SchedulerPage();
	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	home = navigation.clickOnRoomToggleButton()
		.selectConferenceRoom(roomName).clickOnSaveButton().topMenu
		.clickOnHomeButton();

	scheduler = home.clickOnScheduleButton();
	scheduler.displayAllDayOnTimeline().clickOnMeetingButton(subject)
		.setSubject(newSubject).clickUpdateButton();
	credential.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOnOkButton();
    }

    @When("^I modify the \"([^\"]*)\" meeting in \"([^\"]*)\" room with new body \"([^\"]*)\"$")
    public void modify_body_in_meeting(String subject, String roomName,
	    String bodyToModify) throws Throwable {

	BrowserManager.openBrowser();
	HomePage home = new HomePage();
	ConnectionPage connection = new ConnectionPage();
	CredentialsPage credential = new CredentialsPage();
	SchedulerPage scheduler = new SchedulerPage();

	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	home = navigation.clickOnRoomToggleButton()
		.selectConferenceRoom(roomName).clickOnSaveButton().topMenu
		.clickOnHomeButton();

	scheduler = home.clickOnScheduleButton();
	scheduler.displayAllDayOnTimeline().clickOnMeetingButton(subject)
		.setBody(bodyToModify).clickUpdateButton();
	credential.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOnOkButton();
    }

    @When("^I want to modify the schedule of the meeting \"([^\"]*)\" from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void i_want_to_modify_the_schedule_of_the_meeting_from_to(
	    String subject, String startTime, String endTime) throws Throwable {
	SchedulerPage scheduler = new SchedulerPage();
	scheduler.topMenu.clickOnHomeButton().clickOnScheduleButton()
		.displayAllDayOnTimeline().clickOnMeetingButton(subject)
		.setStartTime(startTime).setEndTime(endTime)
		.clickUpdateButton()
		.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOnOkButton();
    }

    @When("^I want to modify the start time in the meeting \"([^\"]*)\" with \"([^\"]*)\"$")
    public void i_want_to_modify_the_start_time_in_the_meeting_with(
	    String subject, String startTime) throws Throwable {
	SchedulerPage scheduler = new SchedulerPage();
	scheduler.topMenu.clickOnHomeButton().clickOnScheduleButton()
		.displayAllDayOnTimeline().clickOnMeetingButton(subject)
		.setStartTime(startTime).clickUpdateButton();
    }

    @When("^I select the meeting \"([^\"]*)\"$")
    public void i_select_the_meeting(String subject) throws Throwable {
	SchedulerPage scheduler = new SchedulerPage();
	scheduler.topMenu.clickOnHomeButton().clickOnScheduleButton()
		.displayAllDayOnTimeline().clickOnMeetingButton(subject);
    }

    @When("^I modify the schedule in the \"([^\"]*)\" meeting on \"([^\"]*)\" drag and drop the start time in \"([^\"]*)\" \"([^\"]*)\" hour$")
    public void drag_and_drop_start_time_in_hour(String meetingName,
	    String roomName, String direction, byte hours) throws Throwable {
	BrowserManager.openBrowser();
	ConnectionPage connection = new ConnectionPage();
	SchedulerPage schedule = new SchedulerPage();
	CredentialsPage credential = new CredentialsPage();

	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	navigation.clickOnRoomToggleButton().selectConferenceRoom(roomName)
		.clickOnSaveButton().topMenu.clickOnHomeButton()
		.clickOnScheduleButton().displayAllDayOnTimeline()
		.clickOnMeetingButton(meetingName);
	if (direction.equals("left")) {
	    hours = (byte) (hours * -1);
	}
	schedule.moveStartTimeMeetingOnTimeLine(meetingName, hours).clickUpdateButton();
	credential.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOnOkButton();

    }

    @When("^I modify the schedule in the \"([^\"]*)\" meeting on \"([^\"]*)\" drag and drop the end time in \"([^\"]*)\" \"([^\"]*)\" hour$")
    public void drag_and_drop_end_time_in_hour(String meetingName,
	    String roomName, String direction, byte hours) throws Throwable {
	BrowserManager.openBrowser();
	ConnectionPage connection = new ConnectionPage();
	SchedulerPage schedule = new SchedulerPage();
	CredentialsPage credential = new CredentialsPage();

	NavigationPage navigation = connection
		.setUpServiceURL(PropertiesReader.getServiceURL())
		.clickOnSaveButton().clickOnNavigationButton();

	navigation.clickOnRoomToggleButton().selectConferenceRoom(roomName)
		.clickOnSaveButton().topMenu.clickOnHomeButton()
		.clickOnScheduleButton().displayAllDayOnTimeline()
		.clickOnMeetingButton(meetingName);
	if (direction.equals("left")) {
	    hours = (byte) (hours * -1);
	}
	schedule.moveEndTimeMeetingOnTimeLine(meetingName, hours).clickUpdateButton();
	credential.setPassword(PropertiesReader.getExchangeOrganizerPwd())
		.clickOnOkButton();

    }

   

}
