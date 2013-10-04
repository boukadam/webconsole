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
import com.peergreen.webconsole.vaadin.ConfirmDialog;
import com.peergreen.webconsole.vaadin.tabs.Tab;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.example.webconsole.scope.ScopeExample.tab")
@Tab("Confirm dialog")
@Navigable("/confirm")
public class ConfirmDialogExtension extends VerticalLayout {

    public ConfirmDialogExtension() {
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        Link showCodeSource = new Link("Show code source", new ExternalResource(GitHubClassURL.getURL(ConfirmDialogExtension.class)));
        showCodeSource.setTargetName("_blank");
        addComponent(showCodeSource);
        setComponentAlignment(showCodeSource, Alignment.TOP_RIGHT);

        Label title = new Label("Drag and drop components from a panel to another");
        title.addStyleName("h1");
        addComponent(title);
        setComponentAlignment(title, Alignment.MIDDLE_CENTER);

        HorizontalLayout row = new HorizontalLayout();
        row.setSizeFull();
        row.setSpacing(true);
        row.setMargin(true);

        VerticalLayout leftPanel = new VerticalLayout();
        leftPanel.setSpacing(true);
        leftPanel.addStyleName("dashed-area");
        leftPanel.addComponent(getDraggableComponent(new Label("Label")));
        leftPanel.addComponent(getDraggableComponent(new Button("Button")));
        DragAndDropWrapper leftPanelWrapper = new DragAndDropWrapper(leftPanel);
        row.addComponent(leftPanelWrapper);
        row.setComponentAlignment(leftPanelWrapper, Alignment.TOP_LEFT);

        VerticalLayout rightPanel = new VerticalLayout();
        rightPanel.setSpacing(true);
        rightPanel.addStyleName("dashed-area");
        DragAndDropWrapper rightPanelWrapper = new DragAndDropWrapper(rightPanel);
        row.addComponent(rightPanelWrapper);
        row.setComponentAlignment(rightPanelWrapper, Alignment.TOP_RIGHT);

        leftPanelWrapper.setDropHandler(new ConfirmDialogExtensionDropHandler(rightPanel, leftPanel));
        rightPanelWrapper.setDropHandler(new ConfirmDialogExtensionDropHandler(leftPanel, rightPanel));

        addComponent(row);
        setExpandRatio(row, 1.5f);
    }

    private DragAndDropWrapper getDraggableComponent(Component component) {
        DragAndDropWrapper dragAndDropWrapper = new DragAndDropWrapper(component);
        dragAndDropWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.COMPONENT);
        return dragAndDropWrapper;
    }

    private class ConfirmDialogExtensionDropHandler implements DropHandler {

        private VerticalLayout source;
        private VerticalLayout target;

        private ConfirmDialogExtensionDropHandler(VerticalLayout source, VerticalLayout target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public void drop(final DragAndDropEvent dragAndDropEvent) {
            ConfirmDialog.show(dragAndDropEvent.getTargetDetails().getTarget().getUI(),
                    "Confirm dialog example",
                    new Label("Would you really like to move this component ?"),
                    "Yes !",
                    "I'm not sure",
                    new ConfirmDialog.Listener() {
                        @Override
                        public void onClose(boolean isConfirmed) {
                            if (isConfirmed) {
                                Component component = dragAndDropEvent.getTransferable().getSourceComponent();
                                source.removeComponent(component);
                                target.addComponent(component);
                            }
                        }
                    });
        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }
    }
}
