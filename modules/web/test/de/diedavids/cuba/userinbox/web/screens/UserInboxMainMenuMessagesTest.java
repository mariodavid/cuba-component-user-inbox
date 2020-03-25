package de.diedavids.cuba.userinbox.web.screens;

import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.web.testsupport.proxy.TestServiceProxy;
import de.diedavids.cuba.userinbox.service.MessageService;
import de.diedavids.cuba.userinbox.web.UserInboxWebTestContainer;
import de.diedavids.cuba.userinbox.web.screens.main.UserInboxSideMenuMainScreen;
import de.diedavids.sneferu.UiTestAPI;
import de.diedavids.sneferu.environment.SneferuTestUiEnvironment;
import de.diedavids.sneferu.screen.ScreenTestAPI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserInboxMainMenuMessagesTest {

  @RegisterExtension
  SneferuTestUiEnvironment environment =
      new SneferuTestUiEnvironment(UserInboxWebTestContainer.Common.INSTANCE)
          .withScreenPackages(
              "com.haulmont.cuba.web.app.main",
              "de.diedavids.cuba.userinbox.web.screens"
          )
          .withUserLogin("admin")
          .withMainScreen(UserInboxSideMenuMainScreen.class);

  @Mock
  private MessageService messageService;

  @BeforeEach
  void setUp() {
    TestServiceProxy.mock(MessageService.class, messageService);
  }

  @Test
  public void when_rootScreenIsShown_then_messagesMenuItemIsAdded() {

    // when:
    SideMenu sideMenu = showSideMenuRootScreen();

    // then:
    assertThat(sideMenu.getMenuItem("messages"))
            .isNotNull();
  }


  @Test
  public void when_rootScreenIsShown_then_messagesMenuItemHasBadgeOfNewMessages() {

    //given:
    when(messageService.countUnreadMessagesForCurrentUser())
            .thenReturn(3L);

    // when:
    SideMenu sideMenu = showSideMenuRootScreen();
    SideMenu.MenuItem messagesMenuItem = sideMenu.getMenuItem("messages");

    // then:
    assertThat(messagesMenuItem.getBadgeText())
            .contains("3");
  }

  @AfterEach
  public void closeAllScreens() {
      environment.getUiTestAPI().closeAllScreens();
      TestServiceProxy.clear();
  }

  @AfterAll
  public static void clearProxy() {
    TestServiceProxy.clear();
  }


  private SideMenu showSideMenuRootScreen() {

    environment.getScreens()
            .create(UserInboxSideMenuMainScreen.class, OpenMode.ROOT)
            .show();


    ScreenTestAPI mainScreen = new ScreenTestAPI(
            UserInboxSideMenuMainScreen.class,
            environment.getScreens()
                    .getOpenedScreens()
                    .getRootScreen()
    );

    return sideMenu(mainScreen);
  }

  private static SideMenu sideMenu(ScreenTestAPI mainScreen) {
    return (SideMenu) mainScreen
            .rawComponent(new SideMenuComponentDescriptor("sideMenu"));
  }
}
