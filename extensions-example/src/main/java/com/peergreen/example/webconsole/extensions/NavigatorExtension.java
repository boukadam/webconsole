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

import com.peergreen.example.webconsole.extensions.utils.GitHubClassURL;
import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.Inject;
import com.peergreen.webconsole.UIContext;
import com.peergreen.webconsole.navigator.Navigable;
import com.peergreen.webconsole.vaadin.tabs.Tab;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.example.webconsole.scope.ScopeExample.tab")
@Tab("Navigator")
@Navigable("/navigator")
public class NavigatorExtension extends VerticalLayout {

    @Inject
    private UIContext uiContext;

    public NavigatorExtension() {
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        Link showCodeSource = new Link("Show code source", new ExternalResource(GitHubClassURL.getURL(NavigatorExtension.class)));
        showCodeSource.setTargetName("_blank");
        addComponent(showCodeSource);
        setComponentAlignment(showCodeSource, Alignment.TOP_RIGHT);
    }

    @PostConstruct
    public void init() {
        Label guide = new Label("The path of a navigable extension is the path of its parent concatenated with the string " +
                "inside the class annotation @Navigable(\"alias\")");
        guide.addStyleName("h2");
        addComponent(guide);

        HorizontalLayout row = new HorizontalLayout();
        row.setSpacing(true);
        row.setMargin(true);
        row.setCaption("Type the alias of an extension you want to navigate to");
        final ComboBox comboBox = new ComboBox();
        comboBox.setWidth("400px");
        comboBox.setNullSelectionAllowed(false);
        comboBox.addItem("/example/simple");
        comboBox.addItem("/example/notifier");
        comboBox.addItem("/example/window");
        comboBox.addItem("/example/confirm");
        row.addComponent(comboBox);
        Button navigate = new Button("Navigate");
        navigate.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (comboBox.getValue() != null && !"".equals(comboBox.getValue())) {
                    uiContext.getViewNavigator().navigateTo(comboBox.getValue().toString());
                }
            }
        });
        row.addComponent(navigate);
        addComponent(row);
        setExpandRatio(row, 1.5f);
    }
}
