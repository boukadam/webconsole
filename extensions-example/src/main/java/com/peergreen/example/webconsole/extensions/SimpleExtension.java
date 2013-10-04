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

import org.osgi.framework.BundleContext;

import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.Inject;
import com.peergreen.webconsole.navigator.Navigable;
import com.peergreen.webconsole.vaadin.tabs.Tab;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.example.webconsole.scope.ScopeExample.tab")
@Tab("Simple Extension")
@Navigable("/simple")
public class SimpleExtension extends VerticalLayout {

    @Inject
    private BundleContext bundleContext;

    public SimpleExtension() {
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
        Label hello = new Label("Hello world !");
        hello.addStyleName("h1");
        addComponent(hello);
        Label guide = new Label(String.format("This is a simple extension added by the bundle <b>%s</b> " +
                "to the scope <b>%s</b>.<br />If you want to contribute to this scope, write an extension class " +
                "annotated by @ExtensionPoint(\"com.peergreen.example.webconsole.scope.ScopeExample.tab\")",
                bundleContext.getBundle().getSymbolicName(),
                "Example"), ContentMode.HTML);
        addComponent(guide);
        setExpandRatio(guide, 1.5f);
    }
}
