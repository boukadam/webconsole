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

import com.peergreen.example.webconsole.extensions.utils.GitHubClassURL;
import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.navigator.Navigable;
import com.peergreen.webconsole.vaadin.DefaultWindow;
import com.peergreen.webconsole.vaadin.tabs.Tab;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.example.webconsole.scope.ScopeExample.tab")
@Tab("Default window")
@Navigable("/window")
public class DefaultWindowExtension extends VerticalLayout {

    public DefaultWindowExtension() {
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        Link showCodeSource = new Link("Show code source", new ExternalResource(GitHubClassURL.getURL(DefaultWindowExtension.class)));
        showCodeSource.setTargetName("_blank");
        addComponent(showCodeSource);
        setComponentAlignment(showCodeSource, Alignment.TOP_RIGHT);

        Button simpleButton = new Button("Click to open a Window");
        simpleButton.setWidth("400px");
        simpleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                final UI ui = clickEvent.getConnector().getUI();
                String caption = "Default window";
                Label content = new Label("This is a simple window");
                Button close = new Button("Close");
                close.addStyleName("wide");
                close.addStyleName("default");
                final DefaultWindow window = new DefaultWindow(caption, content, close);
                window.center();
                ui.addWindow(window);
                close.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        window.close();
                    }
                });
            }
        });
        addComponent(simpleButton);

        setExpandRatio(simpleButton, 1.5f);
    }
}
