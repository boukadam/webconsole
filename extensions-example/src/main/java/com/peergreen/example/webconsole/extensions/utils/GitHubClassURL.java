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

package com.peergreen.example.webconsole.extensions.utils;

/**
 * @author Mohammed Boukada
 */
public class GitHubClassURL {

    private GitHubClassURL() {
    }

    public static String getURL(Class<?> clazz) {
        String prefix = "https://github.com/peergreen/webconsole/blob/master/";
        String artifactId = "extensions-example";
        String srcPath = "/src/main/java/";
        String classPath = clazz.getName().replace('.', '/');
        String java = ".java";
        return prefix + artifactId + srcPath + classPath + java;
    }
}
