/**
 * Peergreen S.A.S. All rights reserved.
 * Proprietary and confidential.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peergreen.example.webconsole.extensions;

import javax.annotation.PostConstruct;

import java.util.Random;

import com.peergreen.example.webconsole.extensions.utils.GitHubClassURL;
import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.Inject;
import com.peergreen.webconsole.navigator.Navigable;
import com.peergreen.webconsole.notifier.INotifierService;
import com.peergreen.webconsole.vaadin.tabs.Tab;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.example.webconsole.scope.ScopeExample.tab")
@Tab("Notifier Extension")
@Navigable("/notifier")
public class NotifierExtension extends VerticalLayout {

    private static final String MESSAGES[] = new String[] {"Congratulations ! You have created a notification",
                                                           "Waw ! It's another notification",
                                                           "Amazing ! You are very comfortable with the notifier",
                                                           "Ok guy, stop joking. Have a look to other examples",
                                                           "I know you missed the notifier xD",
                                                           "Just a simple notification",
                                                           "Peergreen likes your button click action",
                                                           "Visit the web site http://www.peergreen.com/"};

    @Inject
    private INotifierService notifierService;

    public NotifierExtension() {
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        Link showCodeSource = new Link("Show code source", new ExternalResource(GitHubClassURL.getURL(NotifierExtension.class)));
        showCodeSource.setTargetName("_blank");
        addComponent(showCodeSource);
        setComponentAlignment(showCodeSource, Alignment.TOP_RIGHT);
    }

    @PostConstruct
    public void init() {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidth("100%");
        Button simpleButton = new Button("Click to create a notification !");
        final Random random = new Random();
        simpleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                notifierService.addNotification(MESSAGES[random.nextInt(MESSAGES.length)]);
            }
        });
        simpleButton.setWidth("400px");
        row.addComponent(simpleButton);
        row.setComponentAlignment(simpleButton, Alignment.BOTTOM_LEFT);

        final TextField customNotification = new TextField();
        customNotification.setCaption("Write something and type enter");
        final ShortcutListener addNotification = new ShortcutListener("Execute", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object o, Object o2) {
                notifierService.addNotification(customNotification.getValue());
                customNotification.setValue("");
            }
        };
        customNotification.addFocusListener(new FieldEvents.FocusListener() {
            @Override
            public void focus(FieldEvents.FocusEvent focusEvent) {
                customNotification.addShortcutListener(addNotification);
            }
        });
        customNotification.addBlurListener(new FieldEvents.BlurListener() {
            @Override
            public void blur(FieldEvents.BlurEvent blurEvent) {
                customNotification.removeShortcutListener(addNotification);
            }
        });
        customNotification.setWidth("400px");
        row.addComponent(customNotification);
        addComponent(row);
        setExpandRatio(row, 1.5f);
    }
}
