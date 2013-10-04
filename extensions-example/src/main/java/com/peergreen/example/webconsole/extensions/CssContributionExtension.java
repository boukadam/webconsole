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

import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.Inject;
import com.peergreen.webconsole.navigator.Navigable;
import com.peergreen.webconsole.resource.CssInjectorService;
import com.peergreen.webconsole.vaadin.tabs.Tab;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.example.webconsole.scope.ScopeExample.tab")
@Tab(value = "Contribute with CSS", imgPath = "/images/css.png")
@Navigable("/css")
public class CssContributionExtension extends VerticalLayout {

    @Inject
    private CssInjectorService cssInjectorService;

    public CssContributionExtension() {
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        Link showCodeSource = new Link("Show code source", new ExternalResource("http://github.com"));
        showCodeSource.setTargetName("_blank");
        addComponent(showCodeSource);
        setComponentAlignment(showCodeSource, Alignment.TOP_RIGHT);
    }

    @PostConstruct
    public void init() {
        String css = ".custom_button_style {\n" +
                "   color : red !important;\n" +
                "   border : 1px green dashed !important;\n" +
                "}";
        final TextArea textArea = new TextArea("Css : ", css);
        textArea.setWidth("500px");
        addComponent(textArea);
        Button button = new Button("Change my style !");
        button.setWidth("500px");
        button.addStyleName("custom_button_style");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String newCss = textArea.getValue();
                cssInjectorService.inject(newCss);
            }
        });
        addComponent(button);
        setExpandRatio(button, 1.5f);
    }
}
